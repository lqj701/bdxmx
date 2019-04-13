package com.ik.service.miniprogram.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Yinkl
 */
public class ObjectConvertUtils {


    /**
     * 将一个对象数组转化为另一个对象数组
     */
    public static <T, K> List<K> convertObjectList(Class<K> type, List<T> objectList) throws Exception {
        List<K> result = new ArrayList<K>();
        for (T rp : objectList) {
            Map<String, Object> map = ObjectConvertUtils.convertBeanToMap(rp);
            K temp = ObjectConvertUtils.convertMapToBean(type, map);
            result.add(temp);
        }
        return result;
    }

    /**
     * 对象转换，同名属性对转
     *
     * @param type
     * @param obj
     * @return
     * @throws Exception
     */
    public static <T, K> K convertObject(Class<K> type, T obj) throws Exception {
        Map<String, Object> map = ObjectConvertUtils.convertBeanToMap(obj);
        K temp = ObjectConvertUtils.convertMapToBean(type, map);
        return temp;
    }


    /**
     * 将一个Map转化为一个JavaBean对象
     */
    public static <T> T convertMapToBean(Class<T> type, Map<String, Object> map) throws Exception {
        Map<String, String> mapParams = new HashMap<String, String>();
        for (Object key : map.keySet()) {
            String keyValue = "";
            if (key instanceof String) {
                keyValue = (String) key;
            } else {
                keyValue = key + "";
            }
            mapParams.put(keyValue.replace("_", "").toLowerCase(), map.get(keyValue) + "");
        }
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
        T obj = type.newInstance();
        for (int i = 0; i < properties.length; i++) {
            PropertyDescriptor descriptor = properties[i];
            String propertyName = descriptor.getName();
            String value = mapParams.get(propertyName.replace("_", "").toLowerCase());
            if (value != null) {
                Class<?>[] parameterType = descriptor.getWriteMethod().getParameterTypes();
                String first = parameterType[0].getSimpleName();
                if (!"null".equals(value) && value.length() > 0) {
                    if ("int".equals(first) || "Integer".equals(first)) {
                        descriptor.getWriteMethod().invoke(obj, Integer.valueOf(value));
                    } else if ("Long".equalsIgnoreCase(first)) {
                        descriptor.getWriteMethod().invoke(obj, Long.valueOf(value));
                    } else if ("Double".equalsIgnoreCase(first)) {
                        descriptor.getWriteMethod().invoke(obj, Double.valueOf(value));
                    } else if ("Float".equalsIgnoreCase(first)) {
                        descriptor.getWriteMethod().invoke(obj, Float.valueOf(value));
                    } else if ("Boolean".equalsIgnoreCase(first)) {
                        descriptor.getWriteMethod().invoke(obj, Boolean.valueOf(value));
                    } else if ("Short".equalsIgnoreCase(first)) {
                        descriptor.getWriteMethod().invoke(obj, Short.valueOf(value));
                    } else if ("Byte".equalsIgnoreCase(first)) {
                        descriptor.getWriteMethod().invoke(obj, Byte.valueOf(value));
                    } else if ("BigDecimal".equals(first)) {
                        descriptor.getWriteMethod().invoke(obj, new BigDecimal(value));
                    } else if ("Date".equals(first)) {
                        descriptor.getWriteMethod().invoke(obj, DateTools.parseToDate(value));
                    } else if ("String".equals(first)) {
                        descriptor.getWriteMethod().invoke(obj, value);
                    }
                }
            }
        }
        return obj;
    }

    public static <T, K> T convertBeanToBean(K bean, T newBean) throws Exception {
        Map<String, Object> returnMap = ObjectConvertUtils.convertBeanToMap(bean);
        return (T) ObjectConvertUtils.convertMapToBean(newBean.getClass(), returnMap);
    }

    /**
     * 将一个 JavaBean 对象转化为一个 Map
     */
    public static <T> Map<String, Object> convertBeanToMap(T bean) throws Exception {
        Class<?> type = bean.getClass();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < properties.length; i++) {
            PropertyDescriptor descriptor = properties[i];
            String propertyName = descriptor.getName().toLowerCase();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                if (readMethod != null) {
                    Object result = readMethod.invoke(bean, new Object[0]);
                    if (result != null) {
                        if (result instanceof Date) {
                            String values = DateTools.formatToDate((Date) result);
                            returnMap.put(propertyName, values);
                        } else {
                            returnMap.put(propertyName, result.toString());
                        }
                    }
                }
            }
        }
        return returnMap;
    }

    public static Map<String, Object> convertJosntoMap(JSONObject jsonObject) {
        return (Map<String, Object>) jsonObject;
    }

    public static Map<String, Object> convertStringtoMap(String str) {
        JSONObject jsonObject = JSONObject.parseObject(str);
        return convertJosntoMap(jsonObject);
    }

    public static <T> T convertJosntoBean(Class<T> type, JSONObject jsonObject) throws Exception {
        Map<String, Object> map = convertJosntoMap(jsonObject);
        return convertMapToBean(type, map);
    }

    public static <T> T convertStrtoBean(Class<T> type, String str) throws Exception {
        Map<String, Object> map = convertStringtoMap(str);
        return convertMapToBean(type, map);
    }

    public static JSONArray convertMapToListMap(List<Map<String, Object>> maps) {
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < maps.size(); i++) {
            jsonArray.add(convertMapToJson(maps.get(i)));
        }
        return jsonArray;
    }

    public static JSONObject convertMapToJson(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        for (Object key : map.keySet()) {
            String keyValue = "";
            if (key instanceof String) {
                keyValue = (String) key;
            } else {
                keyValue = key + "";
            }
            jsonObject.put(keyValue, map.get(keyValue));
        }
        return jsonObject;
    }

    /**
     * 获取新的集合
     *
     * @param list
     * @return
     */
    public static List getSingle(List list) {
        List newList = new ArrayList(); // 创建新集合
        Iterator it = list.iterator(); // 根据传入的集合(旧集合)获取迭代器
        while (it.hasNext()) { // 遍历老集合
            Object obj = it.next(); // 记录每一个元素
            if (!newList.contains(obj)) { // 如果新集合中不包含旧集合中的元素
                newList.add(obj); // 将元素添加
            }
        }
        return newList;
    }
}