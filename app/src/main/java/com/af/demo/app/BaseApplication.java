package com.af.demo.app;

import android.app.Application;

import com.af.demo.BuildConfig;
import com.af.demo.api.Bean.BaseResponse;
import com.af.lib.http.HttpClient;
import com.af.lib.http.RetrofitClient;
import com.af.lib.http.exception.imp.ResponseErrorListenerImpl;
import com.af.lib.http.exception.rxjava.RxErrorHandler;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;

public class BaseApplication extends Application {

    public  static RxErrorHandler mRxErrorHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        initHtpp();
        mRxErrorHandler = RxErrorHandler.builder()
                .with(this)
                .responseErrorListener(new ResponseErrorListenerImpl())
                .build();
    }


    /**
     * 初始化网络请求，并添加log打印
     */
    private void initHtpp() {
        OkHttpClient build = HttpClient.getInstance().getBuilder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());
                        response.body();
                        return response;
                    }
                })
                .addInterceptor(new LoggingInterceptor.Builder()
                        .loggable(BuildConfig.DEBUG)
                        .setLevel(Level.BASIC)
                        .log(Platform.WARN)
                        .request("Request")
                        .response("Response")
//              .logger(new Logger() {
//                  @Override
//                  public void log(int level, String tag, String msg) {
//                      Log.w(tag, msg);
//                  }
//              })
//              .executor(Executors.newSingleThreadExecutor())
                        .build())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        RetrofitClient.getInstance(BaseResponse.class).setBaseUrl(" https://easy-mock.com/mock/5adf28008017f454bb7bfe63/afapi/");
    }


}
