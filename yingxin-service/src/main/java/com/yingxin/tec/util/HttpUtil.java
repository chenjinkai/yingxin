package com.yingxin.tec.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpUtil {
    /**
     * 判断是否ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        if (header != null && "XMLHttpRequest".equals(header))
            return true;
        else
            return false;
    }

    public static boolean isGetMethod(HttpServletRequest request) {
        return (request.getMethod().equalsIgnoreCase("get"));
    }

    public static boolean isPostMethod(HttpServletRequest request) {
        return (request.getMethod().equalsIgnoreCase("post"));
    }

    public static boolean isDeleteMethod(HttpServletRequest request) {
        return (request.getMethod().equalsIgnoreCase("delete"));
    }

    public static boolean isPutMethod(HttpServletRequest request) {
        return (request.getMethod().equalsIgnoreCase("put"));
    }

    public static <T> Map<String, String> getParameters(MultipartHttpServletRequest fileRequest, Class<T> clazz) {
        Map<String, String> params = new LinkedHashMap<String, String>();
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null
                && fields.length > 0) {
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                String fieldName = field.getName();
                String value = fileRequest.getParameter(fieldName);
                if (StringUtils.isNotBlank(value)) {
                    params.put(fieldName, value.trim());
                }
            }
        }
        return params;
    }
}
