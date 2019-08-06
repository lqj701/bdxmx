package com.ik.service.miniprogram.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.zookeeper.*;

import com.ik.crm.commons.util.StringUtils;

public class ZKLock {
    private ZooKeeper zk;

    private String basePath;

    private String lockPath;

    private static final byte[] LOCK_DATA = "".getBytes();

    // zk 为客户端连接实例, basePath 为锁节点路径，我们将在 basePath 下创建顺序子节点
    public ZKLock(ZooKeeper zk, String basePath) {
        // 按照 zk 的路径规则，以'/'开始，不得以'/'结束
        if (basePath.endsWith("/") || !basePath.startsWith("/")) {
            throw new IllegalArgumentException("base path must start with '/', and must not end with '/'");
        }
        this.zk = zk;
        this.basePath = basePath;
    }

    // 检测 basePath 节点是否存在, 若不存在则创建
    private void ensureBasePath() throws KeeperException, InterruptedException {
        if (zk.exists(basePath, false) == null) {
            // basePath 不存在，进行创建
            List<String> pathParts = new ArrayList<>(Arrays.asList(basePath.split("/"))); // 将路径处理为节点列表
            pathParts.remove(0); // 因为 basePath 以'/'开始, pathParts[0] 一定是空串，将其移除

            // 自底向上，寻找路径中最后一个存在的节点
            int last = 0;
            for (int i = pathParts.size() - 1; i >= 0; i--) {
                String path = "/" + StringUtils.join(pathParts.subList(0, i), '/');
                if (zk.exists(path, false) != null) {
                    last = i;
                    break;
                }
            }

            // 从最后一个存在的节点开始，依次创建节点
            for (int i = last; i < pathParts.size(); i++) {
                String path = "/" + StringUtils.join(pathParts.subList(0, i + 1), '/');
                try {
                    zk.create(path, LOCK_DATA, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                } catch (KeeperException.NodeExistsException ignore) {
                } // may created by other thread
            }

        }
    }

    // 阻塞直至加锁成功
    public void lock() throws KeeperException, InterruptedException {
        ensureBasePath();

        // 在 basePath 下创建临时顺序子节点
        String lockPath = zk.create(basePath + "/lock_", LOCK_DATA, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);

        System.out.println(Thread.currentThread().getName() + " create: " + lockPath);

        // 循环检查加锁是否成功
        while (true) {
            // 取出 basePath 中所有节点并找到最小子节点
            // 因为顺序子节点总是递增的，新创建的节点一定比当前 lockPath 更大，所以 create 和 getChildren 两个操作不保持原子性不会出现异常
            List<String> children = zk.getChildren(basePath, false);
            Collections.sort(children);
            String minNode = children.get(0);

            // 当前线程创建了最小子节点，加锁成功
            if (StringUtils.isNotBlank(lockPath) && StringUtils.isNotBlank(minNode)
                    && StringUtils.equals(lockPath, basePath + "/" + minNode)) {
                this.lockPath = lockPath; // 加锁成功，写入锁路径
                return;
            }

            // 加锁失败，设置 watch
            String watchNode = null;
            String node = lockPath.substring(lockPath.lastIndexOf("/") + 1);
            for (int i = children.size() - 1; i >= 0; i--) {
                String child = children.get(i);
                if (child.compareTo(node) < 0) {
                    watchNode = child;
                    break;
                }
            }

            // 找到需要监视的节点，设置 watch
            if (watchNode != null) {
                System.out.println(Thread.currentThread().getName() + " watch: " + watchNode);

                String watchPath = basePath + "/" + watchNode;

                // 监视 getData 而非 exists 的原因是: 在获取子节点和设置 watch 这段时间内，被监视的节点可能已被删除(锁释放/持有者崩溃)
                // exists 监视会成功设置，但永远不会触发NodeDeleted事件(顺序子节点序号自增，不会复用使用过的序号)。本方法会无限制等待下去
                // 若被监视节点已删除，getData 会抛出异常，避免线程浪费时间等待

                // 该调用中的 watch 回调当事件发生时会在另一个线程中执行
                try {
                    zk.getData(watchPath, event -> {
                        if (event.getType() == Watcher.Event.EventType.NodeDeleted) {
                            // 主线程会调用 this.wait()
                            // fixme: 这里有一个bug，若事件类型不是 NodeDeleted 应进行处理。分布式锁不会产生这种情况，可能是其它客户端操作所致
                            synchronized (this) {
                                notifyAll();
                            }
                        }
                    }, null);
                } catch (KeeperException.NoNodeException e) {
                    // 因为上一个节点被删除导致 getData watch 失败，进入下一个次循环，重新检查自己是否已持有锁
                    continue;
                }


                synchronized (this) {
                    // 等待被 watch 唤醒，唤醒后进入下一次循环，重新检查确认自己已持有锁
                    wait();
                    System.out.println(Thread.currentThread().getName() + " notified");
                }
            }
        }

    }

    // 释放锁
    public void unlock() throws KeeperException, InterruptedException {
        // 加锁成功时会将锁路径写入 lockPath
        if (StringUtils.isNotBlank(lockPath)) {
            zk.delete(lockPath, -1); // 删除锁记录释放锁
        } else {
            throw new IllegalStateException("don't has lock"); // 未设置锁记录说明本线程未持有锁
        }
    }

    public static void main(String[] args) {
        int concurrent = 10;
        ExecutorService service = Executors.newFixedThreadPool(concurrent);
        for (int i = 0; i < concurrent; i++) {
            service.execute(() -> {
                // 为保证各线程独立的持有锁，每个线程应持有独立的 zookeeper 会话
                ZooKeeper zk;
                try {

                    zk = new ZooKeeper("localhost:2181", 6000, watchedEvent -> {
                        if (Watcher.Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                            System.out.println("connection is established...");
                        }
                    });

                    ZKLock lock = new ZKLock(zk, "/test/node1");

                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + "  acquire success");

                    Thread.sleep(1000);
                    System.out.println("do sth, thread: " + Thread.currentThread().getName());

                    lock.unlock();
                    System.out.println(Thread.currentThread().getName() + "  release success");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();
    }
}
