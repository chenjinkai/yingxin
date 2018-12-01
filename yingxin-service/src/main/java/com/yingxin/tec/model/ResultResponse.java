package com.yingxin.tec.model;

public class ResultResponse {
	private String message;
	private String success;
	public ResultResponse(){
		
	}
	public ResultResponse(String message, String success) {
		super();
		this.message = message;
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
}
