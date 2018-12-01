package com.yingxin.tec.validator;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ValidateResponseFactory {
    public static ValidateResponse createValidateFailureResponse(Map<String, String> errorsDic) {
        ValidateResponse response = new ValidateResponse();
        response.setErrorsMsg(generateValidatorResults(errorsDic));
        response.setIspass(false);
        response.setSuccess("false");
        return response;
    }

    public static ValidateResponse createValidateFailureResponse(BindingResult binding) {
        ValidateResponse response = new ValidateResponse();
        response.setErrorsMsg(generateValidatorResults(binding));
        response.setIspass(false);
        response.setSuccess("false");
        return response;
    }

    public static ValidateResponse createValidateFailureResponse(String msg) {
        ValidateResponse response = new ValidateResponse();
        List<ValidateResult> result = new LinkedList<ValidateResult>();
        result.add(new ValidateResult("", msg, ""));
        response.setErrorsMsg(result);
        response.setIspass(false);
        response.setSuccess("false");
        return response;
    }

    public static List<ValidateResult> generateValidatorResults(Map<String, String> errorsDic) {
        List<ValidateResult> result = new ArrayList<ValidateResult>(10);
        if (errorsDic != null && !errorsDic.isEmpty()) {
            for (Entry<String, String> error : errorsDic.entrySet()) {
                result.add(new ValidateResult(error.getKey(), error.getValue()));
            }
        }
        return result;
    }

    public static List<ValidateResult> generateValidatorResults(BindingResult binding) {
        List<FieldError> errors = binding.getFieldErrors();
        List<ValidateResult> result = new ArrayList<ValidateResult>(10);
        if (errors != null && !errors.isEmpty()) {
            for (FieldError error : errors) {
                result.add(new ValidateResult(error.getField(),
                        error.getDefaultMessage(),
                        error.getRejectedValue()));
            }
        }
        return result;
    }

    public static String generateValidatorMsg(BindingResult binding) {
        List<FieldError> errors = binding.getFieldErrors();
        String result = "";
        if (errors != null && !errors.isEmpty()) {
            for (FieldError error : errors) {
                result = result + error.getField() + ":" + error.getDefaultMessage() + "\n\r";
            }
        }
        return result;
    }
}
