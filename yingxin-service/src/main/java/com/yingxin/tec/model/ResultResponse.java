package com.yingxin.tec.model;

public class ResultResponse {
    private String msg;
    private int code;
    private Object data;

    public static ResultResponse OK = new ResultResponse("ok", 0);

    public static ResultResponse INTERNAL_ERROR = new ResultResponse("inter error", -1);

    public ResultResponse() {

    }

    public ResultResponse(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public ResultResponse(String msg, int code, Object data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public ResultResponse setData(Object data) {
        this.data = data;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResultResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public int getCode() {
        return code;
    }

    public ResultResponse setCode(int code) {
        this.code = code;
        return this;
    }
}
