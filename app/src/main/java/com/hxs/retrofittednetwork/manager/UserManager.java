package com.hxs.retrofittednetwork.manager;

import com.hxs.retrofittednetwork.App;
import com.hxs.retrofittednetwork.utils.SpUtils;

/**
 * 用户信息管理
 */
public class UserManager {

	public static String getToken() {
		return SpUtils.getString(App.getContext(), "token", "");
	}

	public static void saveToken(String token) {
		SpUtils.putString(App.getContext(), "token", token);
	}

	public static String getRefreshToken() {
		return SpUtils.getString(App.getContext(), "refresh_token", "");
	}

	public static void saveRefreshToken(String token) {
		SpUtils.putString(App.getContext(), "refresh_token", token);
	}

	public static String getUserId() {
		return SpUtils.getString(App.getContext(), "user_id", "");
	}

	public static void saveUserId(String token) {
		SpUtils.putString(App.getContext(), "user_id", token);
	}
}
