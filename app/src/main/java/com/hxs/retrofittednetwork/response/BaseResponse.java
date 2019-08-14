package com.hxs.retrofittednetwork.response;

/**
 * 服务器返回的数据结构，可根据实际自定义
 */
public class BaseResponse<T> {

	private static int SUCCESS_CODE = 0;//成功的code

	public int getCode() {
		return errorCode;
	}

	public void setCode(int code) {
		this.errorCode = code;
	}

	public String getMsg() {
		return errorMsg;
	}

	public void setMsg(String msg) {
		this.errorMsg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	private int errorCode;
	private String errorMsg;
	private T data;


	public boolean isSuccess() {
		return getCode() == SUCCESS_CODE;
	}
}
