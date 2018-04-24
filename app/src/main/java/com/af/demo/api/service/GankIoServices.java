package com.af.demo.api.service;

import com.af.demo.api.Bean.BaseResponse;
import com.af.demo.api.Bean.FuLiBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface GankIoServices {
    @GET("fuli")
    public Observable<BaseResponse<List<FuLiBean>>> getFuLi();
}
