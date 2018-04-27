package com.af.lib.app.module;

import android.app.Application;
import android.support.v4.app.FragmentManager;

import com.af.lib.app.lifcycles.imp.ActivityLifeCycleImp;
import com.af.lib.app.lifcycles.FragmentLifcycle;

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
    abstract Application.ActivityLifecycleCallbacks bindActivityLifeCycle(ActivityLifeCycleImp activityLifeCycleImp);

    @Binds
    abstract FragmentManager.FragmentLifecycleCallbacks bindFragmentLifecycle(FragmentLifcycle fragmentLifcycle);
}
