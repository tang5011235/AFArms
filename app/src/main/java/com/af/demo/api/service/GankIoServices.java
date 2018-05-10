package com.af.demo.api.service;

import com.af.demo.api.Bean.FuLiBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface GankIoServices {
    @GET("fuli")
    public Observable<FuLiBean> getFuLi();
}
