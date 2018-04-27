package com.af.lib.app.lifcycles;

import android.support.v4.app.FragmentManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FragmentLifcycle extends FragmentManager.FragmentLifecycleCallbacks {
    @Inject
    public FragmentLifcycle() {
    }
}
