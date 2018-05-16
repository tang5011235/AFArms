package com.af.lib.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.af.lib.app.component.AppComponent;
import com.af.lib.app.component.DaggerAppComponent;
import com.af.lib.app.lifcycles.AppLifeCycleCallbacks;
import com.af.lib.app.lifcycles.ConfigModule;
import com.af.lib.app.lifcycles.imp.ApplifeCycleImp;
import com.af.lib.app.module.GlobalConfigModule;
import com.af.lib.http.exception.rxjava.RxErrorHandler;
import com.af.lib.imageengine.imp.ImageLoder;
import com.af.lib.utils.ManifestParser;
import com.af.lib.utils.Preconditions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.rx_cache2.internal.RxCache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * 把<mete-data>下value = ConfigModule的类的所有配置信息读取出来进行初始化
 */
public final class AppDelegate implements AppLifeCycleCallbacks, App {
    static final HashMap<String, Object> AF_SERVICE = new HashMap<>();

    final static HashMap<String, Object> CUSTOM_SERVICE = new HashMap<>();
    //所有的activitylifcycler缓存
    private final List<Application.ActivityLifecycleCallbacks> mActivityLifecycleCallbacks = new ArrayList<>();
    //所有的FragmentLifecycleCallbacks缓存
    @Inject
    List<FragmentManager.FragmentLifecycleCallbacks> mFragmentLifecycleCallbacks;
    //无法使用代理获取
    private final List<AppLifeCycleCallbacks> mAppLifeCycleCallbacks = new ArrayList<>();

    @Inject
    Application.ActivityLifecycleCallbacks mActivityLifecycleCallback;
    private final List<ConfigModule> mConfigModules;
    private AppComponent mAppComponent;

    public AppDelegate(Context context) {
        //解析xml的配置数据
        mConfigModules = new ManifestParser(context).parse();
        //加入框架自身的applifcycle
        mAppLifeCycleCallbacks.add(new ApplifeCycleImp());
        for (ConfigModule configModule : mConfigModules) {
            configModule.injectAppLifecycle(context, mAppLifeCycleCallbacks);
            configModule.injectActivityLifecycle(context, mActivityLifecycleCallbacks);
        }
    }

    @Override
    public void attachBaseContext(@NonNull Context base) {

        //执行外部插入的acttachBaseContext逻辑
        for (AppLifeCycleCallbacks lifeCycleCallback : mAppLifeCycleCallbacks) {
            lifeCycleCallback.attachBaseContext(base);
        }
    }

    @Override
    public void onCreate(@NonNull Application application) {

        GlobalConfigModule globalConfigModule = getGlobalConfigModule(application, mConfigModules);

        mAppComponent = DaggerAppComponent.builder()
                .application(application)
                .globalConfigModule(globalConfigModule)
                .build();
        mAppComponent.inject(this);
        addServices(application);
        //注册框架内部的activityLifeCycle
        application.registerActivityLifecycleCallbacks(mActivityLifecycleCallback);
        for (Application.ActivityLifecycleCallbacks lifecycleCallback : mActivityLifecycleCallbacks) {
            application.registerActivityLifecycleCallbacks(lifecycleCallback);
        }
        //执行外部插入的onCreate逻辑
        for (AppLifeCycleCallbacks lifeCycleCallback : mAppLifeCycleCallbacks) {
            lifeCycleCallback.onCreate(application);
        }
    }

    private void addServices(@NonNull Application application) {
        AFManager.putService(RxCache.class, mAppComponent.rxCache());
        AFManager.putService(Retrofit.class, mAppComponent.retrofit());
        AFManager.putService(OkHttpClient.class, mAppComponent.okHttp());
        AFManager.putService(ImageLoder.class, mAppComponent.getImageLoader());
        AFManager.putService(Application.class, application);
        AFManager.putService(RepositoryManager.class, mAppComponent.repositoryManager());
        AFManager.putService(RxErrorHandler.class, mAppComponent.rxExerrorHandler());
    }

    @Override
    public void onTerminate(@NonNull Application application) {

        //执行外部插入的onTerminate逻辑
        for (AppLifeCycleCallbacks lifeCycleCallback : mAppLifeCycleCallbacks) {
            lifeCycleCallback.onTerminate(application);
        }
    }


    private GlobalConfigModule getGlobalConfigModule(Context context, List<ConfigModule> modules) {

        GlobalConfigModule.Builder builder = new GlobalConfigModule.Builder();

        //遍历 ConfigModule 集合, 给全局配置 GlobalConfigModule 添加参数
        for (ConfigModule module : modules) {
            module.applyOptions(context, builder);
        }

        return builder.build();
    }

    @NonNull
    @Override
    public AppComponent getAppComponent() {
        Preconditions.checkNotNull(mAppComponent,
                "%s cannot be null,first call %s#onCreate(Application) in %s#onCreate()",
                AppComponent.class.getName(), getClass().getName(), Application.class.getName());
        return mAppComponent;
    }


}
