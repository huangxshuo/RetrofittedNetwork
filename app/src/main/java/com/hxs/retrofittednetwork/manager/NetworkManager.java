package com.hxs.retrofittednetwork.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hxs.retrofittednetwork.App;
import com.hxs.retrofittednetwork.api.ApiService;
import com.hxs.retrofittednetwork.auth.TokenAuthenticator;
import com.hxs.retrofittednetwork.utils.SpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {

	private static final int TIMEOUT_READ = 20;
	private static final int TIMEOUT_CONNECTION = 10;
	private static NetworkManager sInstance;
	private static Retrofit retrofit;
	private static volatile ApiService apiService;
	private static Gson gson;

	public static NetworkManager getInstance() {
		if (sInstance == null) {
			synchronized (NetworkManager.class) {
				if (sInstance == null) {
					sInstance = new NetworkManager();
				}
			}
		}
		return sInstance;
	}

	private NetworkManager() {
		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
		loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		OkHttpClient client = new OkHttpClient.Builder()
				//token过期处理
				.authenticator(new TokenAuthenticator())
				.addInterceptor(loggingInterceptor)
				.connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
				.readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
				.writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
				//失败重连
				.retryOnConnectionFailure(true)
				.build();

		retrofit = new Retrofit.Builder()
				.client(client)
				.baseUrl(SpUtils.getString(App.getContext(), "host", ""))
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create(buildGson()))
				.build();
	}


	public ApiService getApiService() {
		if (apiService == null) {
			synchronized (ApiService.class) {
				apiService = retrofit.create(ApiService.class);
			}
		}
		return apiService;
	}

	public <T> T createApi(Class<T> clazz) {
		return retrofit.create(clazz);
	}

	private static Gson buildGson() {
		if (gson == null) {
			gson = new GsonBuilder()
					.setDateFormat("yyyy-MM-dd HH:mm:ss")
					.setLenient()
					.disableHtmlEscaping() //防止特殊字符出现乱码
					.setPrettyPrinting() //对结果进行格式化，增加换行
					.create();
		}
		return gson;
	}
}
