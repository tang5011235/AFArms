package com.af.demo.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.af.lib.app.lifcycles.AppLifeCycleCallbacks;

public class ApplifeCycleImp implements AppLifeCycleCallbacks {
    @Override
    public void attachBaseContext(@NonNull Context base) {

    }

    @Override
    public void onCreate(@NonNull Application application) {

    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }
}
