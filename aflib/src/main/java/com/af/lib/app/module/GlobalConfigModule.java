package com.af.lib.app.module;

import android.app.Application;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.af.lib.http.exception.interfaces.ResponseErrorListener;
import com.af.lib.imageengine.BaseImageLoaderStrategy;
import com.af.lib.imageengine.glide.GlideStrategy;
import com.blankj.utilcode.util.FileUtils;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 存储用户的配置信息,
 */
@Module
public class GlobalConfigModule {
    private String mBaseUrl;
    private File mCacheFile;
    private @Nullable
    Integer maxCacheSizeOfMb;
    private BaseImageLoaderStrategy mLoaderStrategy;
    private NetWorkModule.RetrofitConfiguration mRetrofitConfiguration;
    private NetWorkModule.OkHttpConfiguration mOkHttpConfiguration;
    private NetWorkModule.RxCacheConfiguration rxCacheConfiguration;
    private ResponseErrorListener mResponseErrorListener;

    private GlobalConfigModule(Builder builder) {
        mBaseUrl = builder.mBaseUrl;
        mCacheFile = builder.mCacheFile;
        maxCacheSizeOfMb = builder.maxCacheSizeOfMb;
        mLoaderStrategy = builder.mLoaderStrategy;
        mRetrofitConfiguration = builder.mRetrofitConfiguration;
        mOkHttpConfiguration = builder.mOkHttpConfiguration;
        rxCacheConfiguration = builder.rxCacheConfiguration;
        mResponseErrorListener = builder.mResponseErrorListener;
    }


    public static final class Builder {
        private String mBaseUrl;
        private File mCacheFile;
        private Integer maxCacheSizeOfMb;
        private BaseImageLoaderStrategy mLoaderStrategy;
        private NetWorkModule.RetrofitConfiguration mRetrofitConfiguration;
        private NetWorkModule.OkHttpConfiguration mOkHttpConfiguration;
        private NetWorkModule.RxCacheConfiguration rxCacheConfiguration;
        private ResponseErrorListener mResponseErrorListener;

        public Builder() {
        }

        public Builder setMBaseUrl(String mBaseUrl) {
            this.mBaseUrl = mBaseUrl;
            return this;
        }

        public Builder setMCacheFile(File mCacheFile) {
            this.mCacheFile = mCacheFile;
            return this;
        }

        public Builder setMaxCacheSizeOfMb(Integer maxCacheSizeOfMb) {
            this.maxCacheSizeOfMb = maxCacheSizeOfMb;
            return this;
        }

        public Builder setMLoaderStrategy(BaseImageLoaderStrategy mLoaderStrategy) {
            this.mLoaderStrategy = mLoaderStrategy;
            return this;
        }

        public Builder setMRetrofitConfiguration(NetWorkModule.RetrofitConfiguration mRetrofitConfiguration) {
            this.mRetrofitConfiguration = mRetrofitConfiguration;
            return this;
        }

        public Builder setMOkHttpConfiguration(NetWorkModule.OkHttpConfiguration mOkHttpConfiguration) {
            this.mOkHttpConfiguration = mOkHttpConfiguration;
            return this;
        }

        public Builder setRxCacheConfiguration(NetWorkModule.RxCacheConfiguration rxCacheConfiguration) {
            this.rxCacheConfiguration = rxCacheConfiguration;
            return this;
        }

        public Builder setMResponseErrorListener(ResponseErrorListener mResponseErrorListener) {
            this.mResponseErrorListener = mResponseErrorListener;
            return this;
        }

        public GlobalConfigModule build() {
            return new GlobalConfigModule(this);
        }
    }

    @Singleton
    @Provides
    NetWorkModule.OkHttpConfiguration provideOkHttpConfiguration() {
        return mOkHttpConfiguration;
    }

    @Singleton
    @Provides
    NetWorkModule.RetrofitConfiguration providerRetrofitConfiguration() {
        return mRetrofitConfiguration;
    }

    @Singleton
    @Provides
    ResponseErrorListener provideResponseErrorListener() {
        return mResponseErrorListener == null ? ResponseErrorListener.EMPTY : mResponseErrorListener;
    }

    @Singleton
    @Provides
    File provideCacheFile(Application application) {
        if (mCacheFile == null) {
            File netCache = new File(application.getExternalCacheDir(), "net_cache");
            if (FileUtils.createOrExistsDir(netCache)) {
                return netCache;
            } else {
                throw new IllegalArgumentException("can't create cacheFile");
            }
        }
        return mCacheFile;
    }

    @Singleton
    @Provides
    BaseImageLoaderStrategy provideImageLoaderStragegy() {
        return mLoaderStrategy != null ? mLoaderStrategy : new GlideStrategy();
    }

    @Singleton
    @Provides
    String provideBaseUrl() {
        return TextUtils.isEmpty(mBaseUrl) ? "http://www.baidu.com" : mBaseUrl;
    }

    @Singleton
    @Provides
    @Nullable
    Integer provideMaxCacheSizeOfMb() {
        return maxCacheSizeOfMb;
    }

    @Singleton
    @Provides
    NetWorkModule.RxCacheConfiguration privateRxCacheConfiguration(){
        return rxCacheConfiguration;
    }
}
