package com.hxs.retrofittednetwork.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by miracle on 2018/3/7.
 */

public class CryptConverterFactory extends Converter.Factory {
	/**
	 * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and
	 * decoding from JSON (when no charset is specified by a header) will use UTF-8.
	 */
	public static CryptConverterFactory create() {
		return create(new Gson());
	}

	/**
	 * Create an instance using {@code gson} for conversion. Encoding to JSON and
	 * decoding from JSON (when no charset is specified by a header) will use UTF-8.
	 */
	public static CryptConverterFactory create(Gson gson) {
		return new CryptConverterFactory(gson);
	}

	private final Gson gson;

	private CryptConverterFactory(Gson gson) {
		if (gson == null) throw new NullPointerException("gson == null");
		this.gson = gson;
	}

	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
	                                                        Retrofit retrofit) {
		TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
		return new CryptResponseBodyConverter<>(gson, adapter);
	}

	@Override
	public Converter<?, RequestBody> requestBodyConverter(Type type,
	                                                      Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
		TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
		return new CryptRequestBodyConverter<>(gson, adapter);
	}
}
