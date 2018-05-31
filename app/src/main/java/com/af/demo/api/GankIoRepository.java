package com.af.demo.api;

import com.af.demo.api.Bean.CategoryListBean;
import com.af.demo.api.Bean.FuLiBean;
import com.af.demo.api.Bean.GankIoDayDataBean;
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
        return mManager.creatRxCacheService(GankIoCache.class)
                .getFuLi(mManager.creatRetrofitService(GankIoServices.class).getFuLi(), new DynamicKey(0), new EvictDynamicKey(update))
                .flatMap(new Function<Reply<FuLiBean>, ObservableSource<FuLiBean>>() {
                    @Override
                    public ObservableSource<FuLiBean> apply(Reply<FuLiBean> fuLiBeanReply) throws Exception {
                        return Observable.just(fuLiBeanReply.getData());
                    }
                });

    }

    /**
     * 获取指定日期数据
     *
     * @param date   日期
     * @param update 是否更新
     * @return
     */
    public Observable<GankIoDayDataBean> getDayData(String date, boolean update) {
        return mManager.creatRxCacheService(GankIoCache.class)
                .getDayData(mManager.creatRetrofitService(GankIoServices.class).getDayData(date), new DynamicKey(date), new EvictDynamicKey(update))
                .flatMap(new Function<Reply<GankIoDayDataBean>, ObservableSource<GankIoDayDataBean>>() {
                    @Override
                    public ObservableSource<GankIoDayDataBean> apply(Reply<GankIoDayDataBean> GankIoDayDataBeanReply) throws Exception {
                        return Observable.just(GankIoDayDataBeanReply.getData());
                    }
                });

    }

    /**
     * 获取分类数据
     *
     * @param type    数据类型 ios android ...
     * @param pageNum 分页数
     * @param update  书否更新
     * @return
     */
    public Observable<CategoryListBean> getCategoryListData(String type, int pageNum,
                                                            boolean update) {
        return mManager.creatRxCacheService(GankIoCache.class)
                .getCategoryListData(mManager.creatRetrofitService(GankIoServices.class).getCategoryListData(type, pageNum),
                        new DynamicKey(pageNum), new EvictDynamicKey(update))
                .flatMap(new Function<Reply<CategoryListBean>, ObservableSource<CategoryListBean>>() {
                    @Override
                    public ObservableSource<CategoryListBean> apply(Reply<CategoryListBean> categoryListBeanReply) throws Exception {
                        return Observable.just(categoryListBeanReply.getData());
                    }
                });
    }
}
