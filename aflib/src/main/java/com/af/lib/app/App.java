package com.af.lib.app;

import android.support.annotation.NonNull;

import com.af.lib.app.component.AppComponent;

public interface App {
    @NonNull
    AppComponent getAppComponent();
}