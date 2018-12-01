package com.yingxin.tec.util;

import com.yingxin.tec.model.DatatableRequest;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * mybatis相关工具
 *
 * @author chenjk
 * @since 2015年7月3日
 */
public class MybatisUtil {

    /**
     * 请求参数封装到map
     *
     * @param request
     * @return
     */
    public static Map<String, Object> datatableRequest2Map(DatatableRequest request) {
        Map<String, Object> queryParamters = new HashMap<String, Object>();
        if (request != null
                && StringUtils.isNotEmpty(request.getStart())) {
            queryParamters.put("start", new BigInteger(request.getStart()));
        }
        if (request != null
                && StringUtils.isNotEmpty(request.getLength())) {
            queryParamters.put("length", new BigInteger(request.getLength()));
        }
        List<Map<Object, Object>> sortDatas = request.getSort();
        if (sortDatas != null && sortDatas.size() > 0) {
            String sortStr = " order by ";
            queryParamters.put("hassortdata", true);
            for (int i = 0; i < sortDatas.size(); i++) {
                Map<Object, Object> sortData = sortDatas.get(i);
                String column = (String) sortData.get("column");
                String dir = (String) sortData.get("dir");
                queryParamters.put("sort_" + column, column);
                queryParamters.put("dir_" + column, dir);
                sortStr = sortStr + " " + column + " " + dir;
                if (i != sortDatas.size() - 1) {
                    sortStr = sortStr + " , ";
                }
            }
            queryParamters.put("sortstr", sortStr);
        }
        Map<Object, Object> searchDatas = request.getSearch();
        if (searchDatas != null
                && searchDatas.size() > 0) {
            queryParamters.put("hassearchdata", true);
            for (Entry<Object, Object> entry : searchDatas.entrySet()) {
                queryParamters.put("search_" + entry.getKey(), entry.getValue());
            }
        }
        return queryParamters;
    }
}
