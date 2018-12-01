package com.yingxin.tec.validator;

import java.util.List;

public class ValidateResponse {
	private List<ValidateResult> errorsMsg;
	private String success;
	private boolean ispass;
	
	public boolean isIspass() {
		return ispass;
	}
	public void setIspass(boolean ispass) {
		this.ispass = ispass;
	}
	public List<ValidateResult> getErrorsMsg() {
		return errorsMsg;
	}
	public void setErrorsMsg(List<ValidateResult> errorsMsg) {
		this.errorsMsg = errorsMsg;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
}
