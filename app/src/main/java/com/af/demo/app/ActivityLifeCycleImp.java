package com.af.demo.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ActivityLifeCycleImp implements Application.ActivityLifecycleCallbacks {
    @Inject
    public ActivityLifeCycleImp() {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
