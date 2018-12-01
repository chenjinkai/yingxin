package com.yingxin.tec.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;
import java.util.Map;

public class DatatableRequest {
    @NotEmpty(groups = {DatatableQuery.class}, message = "分页参数start不能为空")
    private String start;
    @NotEmpty(groups = {DatatableQuery.class}, message = "分页参数length不能为空")
    private String length;
    private List<Map<Object, Object>> sort;
    private Map<Object, Object> search;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public List<Map<Object, Object>> getSort() {
        return sort;
    }

    public void setSort(List<Map<Object, Object>> sort) {
        this.sort = sort;
    }

    public Map<Object, Object> getSearch() {
        return search;
    }

    public void setSearch(Map<Object, Object> search) {
        this.search = search;
    }
}
