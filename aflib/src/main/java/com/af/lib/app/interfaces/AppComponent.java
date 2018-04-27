package com.af.lib.app.interfaces;

import android.app.Application;

import com.af.lib.app.AppDelegate;
import com.af.lib.app.AppModule;
import com.af.lib.app.GlobalConfigModule;
import com.af.lib.app.NetWorkModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class, NetWorkModule.class, GlobalConfigModule.class})
public interface AppComponent {

    OkHttpClient okHttp();

    Retrofit retrofit();

    void inject(AppDelegate appDelegate);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        Builder globalConfigModule(GlobalConfigModule globalConfigModule);
        AppComponent build();
    }
}
