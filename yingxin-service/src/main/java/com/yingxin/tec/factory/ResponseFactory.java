package com.yingxin.tec.factory;

import com.yingxin.tec.model.ResultResponse;

public class ResponseFactory {
    public static ResultResponse createSuccessResponserFactory(String msg) {
        ResultResponse response = new ResultResponse();
        response.setMessage(msg);
        response.setSuccess("true");
        return response;
    }

    public static ResultResponse createSuccessResponserFactory() {
        ResultResponse response = new ResultResponse();
        response.setSuccess("true");
        return response;
    }

    public static ResultResponse createFailuerResponserFactory(String msg) {
        ResultResponse response = new ResultResponse();
        response.setMessage(msg);
        response.setSuccess("false");
        return response;
    }

    public static ResultResponse createFailuerResponserFactory() {
        ResultResponse response = new ResultResponse();
        response.setSuccess("true");
        return response;
    }
}
