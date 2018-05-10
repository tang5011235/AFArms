package com.af.demo.api.cache;

import com.af.demo.api.Bean.FuLiBean;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * 作者：thf on 2018/5/9 0009 15:08
 * <p>
 * 邮箱：tang5011235@163.com
 */
public interface GankIoCache {

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    public Observable<Reply<FuLiBean>> getFuLi(Observable<FuLiBean> observable,
                                                                   DynamicKey dynamicKey,
                                                                   EvictProvider evictProvider);
}
