package com.af.lib.http;

import com.af.lib.http.convert.CustomGsonConverterFactory;
import com.af.lib.http.response.interfaces.IResponse;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by thf on 2017/11/27.
 */

public class RetrofitClient {
    private static RetrofitClient mRetrofitClient = null;
    private OkHttpClient.Builder mOkHttpBuilder = null;
    private Retrofit.Builder mRetrofitBuilder = null;
    private Retrofit mRetrofit;

    private RetrofitClient(Class<? extends IResponse> ic) {
        mOkHttpBuilder = HttpClient.getInstance().getBuilder();
        mRetrofitBuilder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(CustomGsonConverterFactory.create(new Gson(), ic))
                .client(mOkHttpBuilder.build());
    }


    public static RetrofitClient getInstance(){
        if (mRetrofitClient == null) {
            throw new  IllegalArgumentException("mRetrofitClient is null,firstly invoke method getInstance(Class<? extends IResponse> ic)");
        }
        return mRetrofitClient;
    }

    public static RetrofitClient getInstance(Class<? extends IResponse> ic) {
        synchronized (RetrofitClient.class) {
            if (mRetrofitClient == null) {
                mRetrofitClient = new RetrofitClient(ic);
            }
        }
        return mRetrofitClient;
    }



    public Retrofit.Builder getRetrofitBuilder() {
        return mRetrofitBuilder;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public Retrofit setBaseUrl(String baseUrl) {
        return mRetrofit = mRetrofitBuilder.baseUrl(baseUrl)
                .build();
    }
}
