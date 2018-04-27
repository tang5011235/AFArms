package com.af.lib.app.module;

import android.app.Application;

import com.af.lib.http.exception.interfaces.ResponseErrorListener;
import com.af.lib.http.exception.rxjava.RxErrorHandler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ExceptionModule {

    @Singleton
    @Provides
    static RxErrorHandler provideRxErrorHandler(Application application, ResponseErrorListener responseErrorListener) {
        return RxErrorHandler.builder()
                .with(application)
                .responseErrorListener(responseErrorListener)
                .build();
    }

}
