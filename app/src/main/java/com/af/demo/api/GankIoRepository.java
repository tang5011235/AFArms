package com.af.demo.api;

import android.content.Context;

import com.af.demo.api.Bean.BaseResponse;
import com.af.demo.api.Bean.FuLiBean;
import com.af.demo.api.cache.GankIoCache;
import com.af.lib.app.App;
import com.af.lib.app.component.AppComponent;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;
import io.rx_cache2.internal.RxCache;

/**
 * 作者：thf on 2018/5/9 0009 15:14
 * <p>
 * 邮箱：tang5011235@163.com
 */
public class GankIoRepository {

    private AppComponent mAppComponent;

    public GankIoRepository(Context context) {
        mAppComponent = ((App) context.getApplicationContext()).getAppComponent();
    }

    public Observable<BaseResponse<List<FuLiBean>>> getFuLi(Observable<BaseResponse<List<FuLiBean>>> observable, boolean update) {
        RxCache rxCache =  mAppComponent.rxCache();
        GankIoCache gankIoCache = rxCache
                .using(GankIoCache.class);
        return gankIoCache
                .getFuLi(observable, new DynamicKey(0), new EvictDynamicKey(update))
                .flatMap(new Function<Reply<BaseResponse<List<FuLiBean>>>, ObservableSource<BaseResponse<List<FuLiBean>>>>() {
                    @Override
                    public ObservableSource<BaseResponse<List<FuLiBean>>> apply(Reply<BaseResponse<List<FuLiBean>>> baseResponseReply) throws Exception {
                        return Observable.just(baseResponseReply.getData());
                    }
                });
    }
}
