package com.hxs.retrofittednetwork.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;


public class BasicParamsInterceptor implements Interceptor {

	Map<String, String> headerParams = new HashMap<>();
	Map<String, String> queryParams = new HashMap<>();
	Map<String, String> params = new HashMap<>();

	private BasicParamsInterceptor() {

	}

	@Override
	public Response intercept(Chain chain) throws IOException {

		Request request = chain.request();
		Request.Builder requestBuilder = request.newBuilder();

		Headers.Builder headerBuilder = request.headers().newBuilder();

		//添加公共头部参数
		if (headerParams.size() > 0) {
			Iterator iterator = headerParams.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				headerBuilder.add((String) entry.getKey(), (String) entry.getValue());
			}
			requestBuilder.headers(headerBuilder.build());
		}

		// 在url中添加公共参数
		if (queryParams.size() > 0) {

			Iterator iterator = params.entrySet().iterator();
			HttpUrl.Builder queryParamsBuilder = request.url().newBuilder();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				queryParamsBuilder.addQueryParameter((String) entry.getKey(), (String) entry.getValue());
			}
			requestBuilder.url(queryParamsBuilder.build());
			request = requestBuilder.build();

		}

		// 在请求体中添加公共参数
		if (params != null && params.size() > 0 && request.method().equals("POST")) {
			if (request.body() instanceof FormBody) {
				FormBody.Builder newFormBodyBuilder = new FormBody.Builder();
				if (params.size() > 0) {
					Iterator iterator = params.entrySet().iterator();
					while (iterator.hasNext()) {
						Map.Entry entry = (Map.Entry) iterator.next();
						newFormBodyBuilder.add((String) entry.getKey(), (String) entry.getValue());
					}
				}

				FormBody oldFormBody = (FormBody) request.body();
				int paramSize = oldFormBody.size();
				if (paramSize > 0) {
					for (int i = 0; i < paramSize; i++) {
						newFormBodyBuilder.add(oldFormBody.name(i), oldFormBody.value(i));
					}
				}

				requestBuilder.post(newFormBodyBuilder.build());
				request = requestBuilder.build();
			} else if (request.body() instanceof MultipartBody) {
				MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);

				Iterator iterator = params.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry entry = (Map.Entry) iterator.next();
					multipartBuilder.addFormDataPart((String) entry.getKey(), (String) entry.getValue());
				}

				List<MultipartBody.Part> oldParts = ((MultipartBody) request.body()).parts();
				if (oldParts != null && oldParts.size() > 0) {
					for (MultipartBody.Part part : oldParts) {
						multipartBuilder.addPart(part);
					}
				}

				requestBuilder.post(multipartBuilder.build());
				request = requestBuilder.build();
			}

		}
		return chain.proceed(request);
	}


	public static class Builder {

		BasicParamsInterceptor interceptor;

		public Builder() {
			interceptor = new BasicParamsInterceptor();
		}

		public Builder addParam(String key, String value) {
			interceptor.params.put(key, value);
			return this;
		}

		public Builder addParamsMap(Map<String, String> params) {
			interceptor.params.putAll(params);
			return this;
		}

		public Builder addHeaderParam(String key, String value) {
			interceptor.headerParams.put(key, value);
			return this;
		}

		public Builder addHeaderParamsMap(Map<String, String> headerParams) {
			interceptor.headerParams.putAll(headerParams);
			return this;
		}


		public Builder addQueryParam(String key, String value) {
			interceptor.queryParams.put(key, value);
			return this;
		}

		public Builder addQueryParamsMap(Map<String, String> queryParams) {
			interceptor.queryParams.putAll(queryParams);
			return this;
		}

		public BasicParamsInterceptor build() {
			return interceptor;
		}

	}
}