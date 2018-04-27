package com.af.lib.app.lifcycles.imp;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.af.lib.BuildConfig;
import com.af.lib.app.lifcycles.AppLifeCycleCallbacks;

import timber.log.Timber;

public class ApplifeCycleImp implements AppLifeCycleCallbacks {
    @Override
    public void attachBaseContext(@NonNull Context base) {

    }

    @Override
    public void onCreate(@NonNull Application application) {
        initTimber();
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }


    //初始化日志打印Timber
    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new Timber.Tree() {
                @Override
                protected void log(int priority, String tag, String message, Throwable t) {
                    Timber.d(tag, message);
                }
            });
        }
    }
}
