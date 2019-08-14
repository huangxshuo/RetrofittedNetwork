package com.hxs.retrofittednetwork.auth;

import com.hxs.retrofittednetwork.Bean.LoginBean;
import com.hxs.retrofittednetwork.manager.NetworkManager;
import com.hxs.retrofittednetwork.manager.UserManager;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;

/**
 * Created by miracle on 2018/9/13.
 */

public class TokenAuthenticator implements Authenticator {
	@Override
	public Request authenticate(Route route, Response response) throws IOException {
		//取出本地的token
		String token = UserManager.getToken();
		// 通过一个特定的接口获取新的token，此处要用到同步的retrofit请求
		Call<LoginBean> call = NetworkManager.getInstance().getApiService().refreshToken(token);
		//要用retrofit的同步方式
		LoginBean result = call.execute().body();
		UserManager.saveRefreshToken(result.getRefreshToken());
		UserManager.saveToken(result.getAccessToken());
		UserManager.saveUserId(result.getUserId());
		return response.request().newBuilder()
				.header("Authorization", "Bearer " + UserManager.getToken())
				.build();
	}

}
