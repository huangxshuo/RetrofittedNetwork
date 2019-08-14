package com.hxs.retrofittednetwork.exception;

/**
 * 自定义异常类
 */
public class ApiException extends RuntimeException {

	private int code;
	private String displayMsg;

	public ApiException(int code, String displayMsg) {
		this.code = code;
		this.displayMsg = displayMsg;
	}

	public ApiException(int code, String message, String displayMsg) {
		super(message);
		this.code = code;
		this.displayMsg = displayMsg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDisplayMsg() {
		return displayMsg;
	}

	public void setDisplayMsg(String displayMsg) {
		this.displayMsg = displayMsg;
	}
}
