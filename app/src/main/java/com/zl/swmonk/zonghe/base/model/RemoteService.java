package com.zl.swmonk.zonghe.base.model;

import com.zl.swmonk.zonghe.joke.BeanJoke;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RemoteService {

    @GET("textJoke")
    Observable<RspMode<BeanJoke>> getBeanJokeText(@Query("maxResult")String maxResult, @Query("page")String page, @Query("time")String time);

    @GET("picJoke")
    Observable<RspMode<BeanJoke>> getBeanJokePic(@Query("maxResult")String maxResult,@Query("page")String page,@Query("time")String time);

    @GET("gifJoke")
    Observable<RspMode<BeanJoke>> getBeanJokeGif(@Query("maxResult")String maxResult,@Query("page")String page,@Query("time")String time);

}
