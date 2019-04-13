package com.ik.service.miniprogram.util;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author Yinkl
 */
public class PageUtil {

    /**
     * 根据查询结果封装返回值
     *
     * @param pageInfo
     * @param list
     * @return
     */
    public static JSONObject getReturnInfo(PageInfo pageInfo, List list) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        jsonObject.put("total_count", pageInfo.getTotal());
        jsonObject.put("page", pageInfo.getPageNum());
        jsonObject.put("per_page", pageInfo.getPageSize());
        return jsonObject;
    }

    /**
     * 根据查询结果封装返回值
     *
     * @param counts
     * @return
     */
    public static JSONObject getReturnInfo(Map params, List<Map<String, Object>> maps, Integer counts) {

        Integer page = (Integer) params.get("page");
        Integer perPage = (Integer) params.get("per_page");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", ObjectConvertUtils.convertMapToListMap(maps));
        jsonObject.put("total_count", counts);
        jsonObject.put("page", page);
        jsonObject.put("per_page", perPage);
        return jsonObject;
    }

    /**
     * 开启分页
     *
     * @param page
     * @param perPage
     */
    public static void start(Integer page, Integer perPage) {

        page = page == null ? 1 : page;
        perPage = perPage == null ? 20 : perPage;

        // 开始分页
        PageHelper.startPage(page, perPage);
    }

    /**
     * 根据前台的page，per_page，生成对应参数
     *
     * @param params
     * @return
     */
    public static Map parseParams(Map params) {

        Integer page = (Integer) params.get("page");
        Integer perPage = (Integer) params.get("per_page");

        page = page == null ? 1 : page;
        perPage = perPage == null ? 20 : perPage;

        // page：页码；start：开始条数；per_page：每页条数
        params.put("page", page);
        params.put("start", (page - 1) * perPage);
        params.put("per_page", perPage);
        return params;
    }

    /**
     * 获取总数
     *
     * @param maps
     * @return
     */
    public static Integer getCounts(List<Map<String, Object>> maps) {
        JSONArray jSONArray = ObjectConvertUtils.convertMapToListMap(maps);
        JSONObject jsonObject = (JSONObject) jSONArray.get(0);

        return jsonObject.getInteger("counts");
    }
}
