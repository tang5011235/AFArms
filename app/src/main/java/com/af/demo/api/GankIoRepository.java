package com.af.demo.api;

import android.app.Application;

import com.af.demo.api.Bean.FuLiBean;
import com.af.demo.api.cache.GankIoCache;
import com.af.demo.api.service.GankIoServices;
import com.af.lib.app.App;
import com.af.lib.app.component.AppComponent;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;

/**
 * 作者：thf on 2018/5/9 0009 15:14
 * <p>
 * 邮箱：tang5011235@163.com
 */
public class GankIoRepository {

    private AppComponent mAppComponent;

    public GankIoRepository(Application application) {
        mAppComponent = ((App) application.getApplicationContext()).getAppComponent();
    }

    public Observable<FuLiBean> getFuLi(boolean update) {
        return mAppComponent.repositoryManager()
                .creatRetrofitService(GankIoServices.class)
                .getFuLi()
                .flatMap(new Function<FuLiBean, ObservableSource<FuLiBean>>() {
                    @Override
                    public ObservableSource<FuLiBean> apply(FuLiBean fuLiBean) throws Exception {
                        return mAppComponent.repositoryManager().creatRxCacheService(GankIoCache.class)
                                .getFuLi(Observable.just(fuLiBean), new DynamicKey(0), new EvictDynamicKey(update))
                                .flatMap(new Function<Reply<FuLiBean>, ObservableSource<FuLiBean>>() {
                                    @Override
                                    public ObservableSource<FuLiBean> apply(Reply<FuLiBean> fuLiBeanReply) throws Exception {
                                        return Observable.just(fuLiBeanReply.getData());
                                    }
                                });
                    }
                });
    }
}
