package com.af.demo.app;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.af.demo.api.Bean.BaseResponse;
import com.af.lib.BuildConfig;
import com.af.lib.app.GlobalConfigModule;
import com.af.lib.app.NetWorkModule;
import com.af.lib.app.interfaces.AppLifeCycleCallbacks;
import com.af.lib.app.interfaces.ConfigModule;
import com.af.lib.http.convert.CustomGsonConverterFactory;
import com.google.gson.Gson;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import java.io.File;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class GlobalConfigeration implements ConfigModule {
    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        builder.setMBaseUrl("")
                .setMCacheFile(new File(""))
                .setMOkHttpConfiguration(new NetWorkModule.OkHttpConfiguration() {
                    @Override
                    public void configOkhttp(Application application, OkHttpClient.Builder builder) {
                        builder.addInterceptor(new LoggingInterceptor.Builder()
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
                                .build());
                    }
                })
                .setMRetrofitConfiguration(new NetWorkModule.RetrofitConfiguration() {
                    @Override
                    public void configRetrofit(Application application, Retrofit.Builder builder) {
                        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .addConverterFactory(CustomGsonConverterFactory.create(new Gson(), BaseResponse.class));
                    }
                });
    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifeCycleCallbacks> lifecycles) {
        lifecycles.add(new ApplifeCycleImp());
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
        lifecycles.add(new ActivityLifeCycleImp());
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        lifecycles.add(new FragmentLifeCycleImp());
    }

}
