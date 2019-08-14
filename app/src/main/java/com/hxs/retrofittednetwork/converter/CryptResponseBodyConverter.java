package com.hxs.retrofittednetwork.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.hxs.retrofittednetwork.utils.JsonPrinter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by miracle on 2018/3/7.
 * <p>
 * 请求结果处理类
 */

public class CryptResponseBodyConverter<T> implements Converter<ResponseBody, T> {

	private static final Charset UTF_8 = Charset.forName("UTF-8");
	private final Gson gson;
	private final TypeAdapter<T> adapter;

	CryptResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
		this.gson = gson;
		this.adapter = adapter;
	}

	@Override
	public T convert(ResponseBody value) throws IOException {
		String response = value.toString();
		//若需要对返回数据进行解密处理，则在此处根据实际加密方式进行解密
		//String decryptResp = DESUtils.decrypt(response);
		JsonPrinter.printJson("XXX", response, "***START***");

		ByteArrayInputStream bais = new ByteArrayInputStream(response.getBytes());
		Reader reader = new InputStreamReader(bais, UTF_8);
		JsonReader jsonReader = gson.newJsonReader(reader);
		try {
			return adapter.read(jsonReader);
		} finally {
			value.close();
		}
	}

}
