package com.hxs.retrofittednetwork.api;

import com.hxs.retrofittednetwork.Bean.LoginBean;
import com.hxs.retrofittednetwork.Bean.MainBean;
import com.hxs.retrofittednetwork.response.BaseResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {


	/***
	 * 获取首页列表数据
	 * @param page
	 * @return
	 */
	@GET("article/list/{page}/json")
	Observable<BaseResponse<MainBean>> getHomeList(@Path("page") int page);

	/**
	 * 刷新token
	 */
	@POST("xxx")
	@FormUrlEncoded
	Call<LoginBean> refreshToken(@Field("token") String token);

}
