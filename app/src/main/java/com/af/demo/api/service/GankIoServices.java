package com.af.demo.api.service;

import com.af.demo.api.Bean.FuLiBean;
import com.af.demo.api.Bean.GankIoDayDataBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GankIoServices {
    @GET("fuli")
    Observable<FuLiBean> getFuLi();


    /**
     * 获取指定日起的数据
     * @param date
     * @return
     */
    @GET("day/{date}")
    Observable<GankIoDayDataBean> getDayData(@Path("date") String date);
}
