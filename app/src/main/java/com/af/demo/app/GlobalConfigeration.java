package com.af.demo.app;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.af.demo.api.Bean.BaseResponse;
import com.af.lib.BuildConfig;
import com.af.lib.app.lifcycles.AppLifeCycleCallbacks;
import com.af.lib.app.lifcycles.ConfigModule;
import com.af.lib.app.module.GlobalConfigModule;
import com.af.lib.app.module.NetWorkModule;
import com.af.lib.http.convert.CustomGsonConverterFactory;
import com.af.lib.imageengine.glide.GlideStrategy;
import com.google.gson.Gson;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.rx_cache2.internal.RxCache;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 初始化全局参数
 */
public class GlobalConfigeration implements ConfigModule {
	@Override
	public void applyOptions(Context context, GlobalConfigModule.Builder builder) {

		builder.setMBaseUrl("http://gank.io/api/")
				.setMaxCacheSizeOfMb(100)
				//.setMCacheFile(new File(Environment.getDownloadCacheDirectory(),"glide_cache"))
				.setRxCacheConfiguration(new NetWorkModule.RxCacheConfiguration() {
					@Override
					public void configRxCache(Application application, RxCache.Builder builder) {
					}
				})
				.setMOkHttpConfiguration(new NetWorkModule.OkHttpConfiguration() {
					@Override
					public void configOkhttp(Application application, OkHttpClient.Builder builder) {
						builder.addInterceptor(new LoggingInterceptor.Builder()
								.loggable(BuildConfig.DEBUG)
								.setLevel(Level.BASIC)
								.log(Platform.WARN)
								.request("Request")
								.response("Response")
								.executor(Executors.newSingleThreadExecutor())
								.build())
								.connectTimeout(15, TimeUnit.SECONDS)
								.readTimeout(300, TimeUnit.SECONDS)
								.writeTimeout(300, TimeUnit.SECONDS);
					}
				})
				.setMLoaderStrategy(new GlideStrategy())
				.setMRetrofitConfiguration(new NetWorkModule.RetrofitConfiguration() {
					@Override
					public void configRetrofit(Application application, Retrofit.Builder builder) {
						builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
								.addConverterFactory(CustomGsonConverterFactory.create(new Gson(), BaseResponse.class));
					}
				})
				.setMResponseErrorListener(new ResponseErrorListenerImpl());
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
