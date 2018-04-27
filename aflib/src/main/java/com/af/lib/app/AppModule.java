package com.af.lib.app;

import android.app.Application;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * 提供App 必须的配置
 */
@Module
public abstract class AppModule {

    @Singleton
    @Provides
    static List<Application.ActivityLifecycleCallbacks> provideActivitylifeCycles() {
        return new ArrayList<>();
    }

    @Singleton
    @Provides
    static List<FragmentManager.FragmentLifecycleCallbacks> provideFragmentLifeCycles() {
        return new ArrayList<>();
    }


    @Binds
    abstract Application.ActivityLifecycleCallbacks bindActivityLifeCycle(ActivityLifeCycle activityLifeCycle);

    @Binds
    abstract FragmentManager.FragmentLifecycleCallbacks bindFragmentLifecycle(FragmentLifcycle fragmentLifcycle);
}
