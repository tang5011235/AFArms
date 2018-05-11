package com.af.lib.app.module;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.LruCache;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Module
public class NetWorkModule {

    /**
     * 提供okHttp对象
     *
     * @return
     */
    @Singleton
    @Provides
    static OkHttpClient provideOkhttp(Application application, OkHttpConfiguration okHttpConfiguration) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (okHttpConfiguration != null) {
            okHttpConfiguration.configOkhttp(application, builder);
        }
        return builder.build();
    }

    /**
     * 提供retrofit对象
     */
    @Singleton
    @Provides
    static Retrofit provideRetrofit(Application application,
                                    OkHttpClient okHttpClient,
                                    RetrofitConfiguration retrofitConfiguration
            , String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder();
        if (retrofitConfiguration != null) {
            retrofitConfiguration.configRetrofit(application, builder);
        }
        return builder
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
    }

    @Singleton
    @Provides
    static RxCache provideRxCache(Application application, File cacheFile, @Nullable Integer maxSize, RxCacheConfiguration configuration) {
        RxCache.Builder builder = new RxCache.Builder();
        builder.useExpiredDataIfLoaderNotAvailable(true);
        if (maxSize != null) {
            builder.setMaxMBPersistenceCache(maxSize);
        }
        if (configuration != null) {
            configuration.configRxCache(application, builder);
        }
        return builder.persistence(cacheFile, new GsonSpeaker());
    }


    @Singleton
    @Provides
    @Named("repository")
    static LruCache<String, Object> provideRepository(Application application) {
        ActivityManager activityManager = (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
        return new LruCache<>((int) (activityManager.getMemoryClass() * 0.005f * 1024));
    }

    @Singleton
    @Provides
    @Named("retrofitService")
    static LruCache<String, Object> provideRetrofitService(Application application) {
        ActivityManager activityManager = (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
        return new LruCache<>((int) (activityManager.getMemoryClass() * 0.005f * 1024));
    }

    @Singleton
    @Provides
    @Named("rxCacheService")
    static LruCache<String, Object> provideRxCacheService(Application application) {
        ActivityManager activityManager = (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
        return new LruCache<>((int) (activityManager.getMemoryClass() * 0.005f * 1024));
    }

    public interface OkHttpConfiguration {
        void configOkhttp(Application application, OkHttpClient.Builder builder);
    }

    public interface RetrofitConfiguration {
        void configRetrofit(Application application, Retrofit.Builder builder);
    }

    public interface RxCacheConfiguration {
        void configRxCache(Application application, RxCache.Builder builder);
    }
}
