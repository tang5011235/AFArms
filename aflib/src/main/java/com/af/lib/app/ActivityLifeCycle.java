package com.af.lib.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

/**
 * 框架内部的activityLifeCycle用于做一些关于activity的统一操作
 */
@Singleton
public class ActivityLifeCycle implements Application.ActivityLifecycleCallbacks {
    @Inject
    List<FragmentManager.FragmentLifecycleCallbacks> mFragmentLifecycleCallbacks;

    //框架内部的fragmentLifeCyle
    @Inject
    FragmentManager.FragmentLifecycleCallbacks mFragmentLifecycleCallback;

    @Inject
    public ActivityLifeCycle() {

    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Timber.tag("AfActivityLifeCycle");
        Timber.d("onActivityCreated");
        //注入框架内部的fragmentflifeCycle
        ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(mFragmentLifecycleCallback, true);
        //注入外部的fragment的lifeCycles
        for (FragmentManager.FragmentLifecycleCallbacks lifecycleCallback : mFragmentLifecycleCallbacks) {
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(lifecycleCallback, true);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Timber.d("onActivityStarted");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Timber.d("onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Timber.d("onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Timber.d("onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Timber.d("onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Timber.d("onActivityDestroyed");
    }
}
