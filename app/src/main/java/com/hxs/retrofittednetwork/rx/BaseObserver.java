package com.hxs.retrofittednetwork.rx;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.hxs.retrofittednetwork.exception.ApiException;
import com.hxs.retrofittednetwork.response.BaseResponse;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by miracle on 2018/8/9.
 * <p>
 * 带进度条的observer
 */

public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {

	//出错提示
	private static final String SERVER_ERR = "服务器繁忙";
	private static final String NETWORK_ERR = "网络错误";
	private static final String PARSE_ERR = "数据解析错误";
	private static final String CONNECTION_ERR = "连接服务器错误,请检查网络";
	private static final String CONNECTION_TIMEOUT = "连接服务器超时,请检查网络";
	private static final String UNKNOW_ERR = "未知错误";

	private RxManager rxManager;

	public BaseObserver(RxManager rxManager) {
		this.rxManager = rxManager;
	}

	@Override
	public void onSubscribe(Disposable d) {
		rxManager.register(d);
	}

	@Override
	public void onNext(BaseResponse<T> baseResponse) {
		if (baseResponse.isSuccess()) {
			T data = baseResponse.getData();
			if (data != null) {
				onSuccess(data);
			} else {
				onSuccess();
			}
		} else {//失败情况当作异常抛出
			onError(new ApiException(baseResponse.getCode(), baseResponse.getMsg()));
		}

	}

	@Override
	public void onError(Throwable e) {

		Throwable throwable = e;
		//获取最根源的异常
		while (throwable.getCause() != null) {
			e = throwable;
			throwable = throwable.getCause();
		}
		String errMsg = "";

		if (e instanceof HttpException) {             //HTTP错误
			HttpException httpException = (HttpException) e;
			if (httpException.code() == 500) {
				errMsg = SERVER_ERR;
			} else {
				errMsg = NETWORK_ERR;
			}
		} else if (e instanceof ConnectException) {
			errMsg = CONNECTION_ERR;
		} else if (e instanceof SocketTimeoutException) {
			errMsg = CONNECTION_TIMEOUT;
		} else if (e instanceof UnknownHostException) {
			errMsg = SERVER_ERR;
		} else if (e instanceof JsonParseException  //均视为解析错误
				|| e instanceof JSONException
				|| e instanceof ParseException) {
			errMsg = PARSE_ERR;
		} else if (e instanceof ApiException) {    //服务器返回的错误
			ApiException ae = (ApiException) e;
			//处理服务器返回的错误信息,如token过期
			errMsg = ae.getDisplayMsg();
		}
		onFail(errMsg);
		e.printStackTrace();

	}


	@Override
	public void onComplete() {
	}


	/**
	 * 返回成功
	 *
	 * @param t
	 */
	protected abstract void onSuccess(T t);

	/**
	 * 成功，但无数据的情况
	 */
	protected void onSuccess() {
	}


	/**
	 * 返回失败
	 */
	protected abstract void onFail(String errMsg);


}
