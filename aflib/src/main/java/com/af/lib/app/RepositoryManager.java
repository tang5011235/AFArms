package com.af.lib.app;

import android.app.Application;
import android.util.LruCache;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.rx_cache2.internal.RxCache;
import retrofit2.Retrofit;

/**
 * 作者：thf on 2018/5/10 0010 15:18
 * <p>
 * 邮箱：tang5011235@163.com
 * a
 */
@Singleton
public class RepositoryManager {
    @Inject
    RxCache mRxCache;
    @Inject
    Retrofit mRetrofit;
    @Inject
    Application mApplication;

    @Inject
    @Named("repository")
    LruCache<String, Object> repository;

    @Inject
    @Named("rxCacheService")
    LruCache<String, Object> retrofitService;

    @Inject
    @Named("retrofitService")
    LruCache<String, Object> rxCacheService;

    @Inject
    public RepositoryManager() {
    }

    public <T> T creatRepository(Class<T> clazz) {

        //
        T t = (T) repository.get(clazz.getCanonicalName());
        if (t == null) {
            try {
                t = clazz.getConstructor(RepositoryManager.class).newInstance(this);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        repository.put(clazz.getCanonicalName(), t);
        return t;
    }


    public <T> T creatRetrofitService(Class<T> clazz) {
        T t = (T) retrofitService.get(clazz.getCanonicalName());
        if (t == null) {
            t = mRetrofit.create(clazz);
        }
        retrofitService.put(clazz.getCanonicalName(), t);
        return t;
    }


    public <T> T creatRxCacheService(Class<T> clazz) {
        T t = (T) rxCacheService.get(clazz.getCanonicalName());
        if (t == null) {
            t = mRxCache.using(clazz);
        }
        rxCacheService.put(clazz.getCanonicalName(), t);
        return t;
    }


    public void release() {
        retrofitService = null;
        rxCacheService = null;
    }
}
