package com.af.lib.app;

import java.io.File;

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

    private GlobalConfigModule(Builder builder) {
        mBaseUrl = builder.mBaseUrl;
        mCacheFile = builder.mCacheFile;
        mRetrofitConfiguration = builder.mRetrofitConfiguration;
        mOkHttpConfiguration = builder.mOkHttpConfiguration;
    }


    public static final class Builder {
        private String mBaseUrl;
        private File mCacheFile;
        private NetWorkModule.RetrofitConfiguration mRetrofitConfiguration;
        private NetWorkModule.OkHttpConfiguration mOkHttpConfiguration;

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

        public GlobalConfigModule build() {
            return new GlobalConfigModule(this);
        }
    }

    @Provides
    NetWorkModule.OkHttpConfiguration provideOkHttpConfiguration(){
        return mOkHttpConfiguration;
    }

    @Provides
    NetWorkModule.RetrofitConfiguration  providerRetrofitConfiguration(){
        return mRetrofitConfiguration;
    }
}
