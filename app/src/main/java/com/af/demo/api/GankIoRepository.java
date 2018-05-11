package com.af.demo.api;

import com.af.demo.api.Bean.FuLiBean;
import com.af.demo.api.cache.GankIoCache;
import com.af.demo.api.service.GankIoServices;
import com.af.lib.app.RepositoryManager;

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

    private RepositoryManager mManager;

    public GankIoRepository(RepositoryManager manager) {
        mManager = manager;
    }

    public Observable<FuLiBean> getFuLi(boolean update) {

        /*return Observable.just(mManager.creatRetrofitService(GankIoServices.class).getFuLi())
                .flatMap(new Function<Observable<FuLiBean>, ObservableSource<FuLiBean>>() {
                    @Override
                    public ObservableSource<FuLiBean> apply(Observable<FuLiBean> observable) throws Exception {
                        return mManager.creatRxCacheService(GankIoCache.class)
                                .getFuLi(observable, new DynamicKey(0), new EvictDynamicKey(update))
                                .map(new Function<Reply<FuLiBean>, FuLiBean>() {
                                    @Override
                                    public FuLiBean apply(Reply<FuLiBean> fuLiBeanReply) throws Exception {
                                        return fuLiBeanReply.getData();
                                    }
                                });
                    }
                });*/
        return mManager.creatRxCacheService(GankIoCache.class)
                .getFuLi(mManager.creatRetrofitService(GankIoServices.class).getFuLi(), new DynamicKey(0), new EvictDynamicKey(update))
                .flatMap(new Function<Reply<FuLiBean>, ObservableSource<FuLiBean>>() {
                    @Override
                    public ObservableSource<FuLiBean> apply(Reply<FuLiBean> fuLiBeanReply) throws Exception {
                        return Observable.just(fuLiBeanReply.getData());
                    }
                });

    }
}
