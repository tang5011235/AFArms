package com.af.lib.app.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
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
    ,String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder();
        if (retrofitConfiguration != null) {
            retrofitConfiguration.configRetrofit(application, builder);
        }
        return builder
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
    }

    public interface OkHttpConfiguration {
        void configOkhttp(Application application, OkHttpClient.Builder builder);
    }

    public interface RetrofitConfiguration {
        void configRetrofit(Application application, Retrofit.Builder builder);
    }
}
