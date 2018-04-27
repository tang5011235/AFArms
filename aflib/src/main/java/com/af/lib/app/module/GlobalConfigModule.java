package com.af.lib.app.module;

import com.af.lib.http.exception.interfaces.ResponseErrorListener;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 存储用户的配置信息
 */
@Module
public class GlobalConfigModule {
    private String mBaseUrl;
    private File mCacheFile;
    private NetWorkModule.RetrofitConfiguration mRetrofitConfiguration;
    private NetWorkModule.OkHttpConfiguration mOkHttpConfiguration;
    private ResponseErrorListener mResponseErrorListener;

    private GlobalConfigModule(Builder builder) {
        mBaseUrl = builder.mBaseUrl;
        mCacheFile = builder.mCacheFile;
        mRetrofitConfiguration = builder.mRetrofitConfiguration;
        mOkHttpConfiguration = builder.mOkHttpConfiguration;
        mResponseErrorListener = builder.mResponseErrorListener;
    }


    public static final class Builder {
        private String mBaseUrl;
        private File mCacheFile;
        private NetWorkModule.RetrofitConfiguration mRetrofitConfiguration;
        private NetWorkModule.OkHttpConfiguration mOkHttpConfiguration;
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

        public Builder setMRetrofitConfiguration(NetWorkModule.RetrofitConfiguration mRetrofitConfiguration) {
            this.mRetrofitConfiguration = mRetrofitConfiguration;
            return this;
        }

        public Builder setMOkHttpConfiguration(NetWorkModule.OkHttpConfiguration mOkHttpConfiguration) {
            this.mOkHttpConfiguration = mOkHttpConfiguration;
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
    ResponseErrorListener provideResponseErrorListener(){
        return mResponseErrorListener == null ? ResponseErrorListener.EMPTY : mResponseErrorListener;
    }
}
