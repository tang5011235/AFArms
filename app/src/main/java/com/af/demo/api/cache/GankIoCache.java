package com.af.demo.api.cache;

import com.af.demo.api.Bean.CategoryListBean;
import com.af.demo.api.Bean.FuLiBean;
import com.af.demo.api.Bean.GankIoDayDataBean;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.DynamicKeyGroup;
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

    /**
     * 获取指定日期数据
     *
     * @param observable
     * @param dynamicKey
     * @param evictProvider
     * @return
     */
    @LifeCache(duration = 15, timeUnit = TimeUnit.DAYS)
    Observable<Reply<GankIoDayDataBean>> getDayData(Observable<GankIoDayDataBean> observable,
                                                    DynamicKey dynamicKey,
                                                    EvictProvider evictProvider);


    /**
     * 获取类型的数据
     *
     * @param observable
     * @param dynamicKey
     * @param evictProvider
     * @return
     */
    @LifeCache(duration = 15, timeUnit = TimeUnit.DAYS)
    Observable<Reply<CategoryListBean>> getCategoryListData(Observable<CategoryListBean> observable,
                                                  DynamicKeyGroup dynamicKey,
                                                  EvictProvider evictProvider);
}
