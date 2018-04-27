package com.af.lib.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.af.lib.app.component.AppComponent;
import com.af.lib.app.interfaces.App;
import com.af.lib.app.interfaces.AppLifeCycleCallbacks;
import com.af.lib.utils.Preconditions;

/**
 * User: tourdt（tourdt@qq.com)
 * Date: 2018-04-24
 * Time: 12:01
 */
public class BaseApplication extends Application implements App{
    private AppLifeCycleCallbacks mAppDelegate;

    /**
     * 这里会在 {@link BaseApplication#onCreate} 之前被调用,可以做一些较早的初始化
     * 常用于 MultiDex 以及插件化框架的初始化
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (mAppDelegate == null) {
            this.mAppDelegate = new AppDelegate(base);
        }
        this.mAppDelegate.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mAppDelegate != null) {
            this.mAppDelegate.onCreate(this);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null) {
            this.mAppDelegate.onTerminate(this);
        }
    }

    @NonNull
    @Override
    public AppComponent getAppComponent() {
        Preconditions.checkNotNull(mAppDelegate, "%s cannot be null", AppDelegate.class.getName());
        Preconditions.checkState(mAppDelegate instanceof App, "%s must be implements %s", AppDelegate.class.getName(), App.class.getName());
        return ((App) mAppDelegate).getAppComponent();
    }
}
