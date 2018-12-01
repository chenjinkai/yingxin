package com.yingxin.tec.validator;

public class ValidateResult {
	private String field;
	private String msg;
	private Object value;
	
	public ValidateResult(String field, String msg) {
		super();
		this.field = field;
		this.msg = msg;
	}
	
	public ValidateResult(String field, String msg, Object value) {
		super();
		this.field = field;
		this.msg = msg;
		this.value = value;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
}
