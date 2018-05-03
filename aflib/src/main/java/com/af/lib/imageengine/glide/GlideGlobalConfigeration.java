package com.af.lib.imageengine.glide;

import android.content.Context;
import android.support.annotation.NonNull;

import com.af.lib.app.App;
import com.af.lib.app.component.AppComponent;
import com.af.lib.imageengine.BaseImageLoaderStrategy;
import com.af.lib.imageengine.GlideConfigrationPermission;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

/**
 * 作者：thf on 2018/5/3 0003 10:33
 * <p>
 * 邮箱：tang5011235@163.com
 */

@GlideModule(glideName = "AFGlide")
public class GlideGlobalConfigeration extends AppGlideModule {

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        AppComponent appComponent = ((App) context.getApplicationContext()).getAppComponent();

        BaseImageLoaderStrategy imageLoaderStrategy = appComponent.imageLoaderStrategy();
        if (imageLoaderStrategy instanceof GlideConfigrationPermission) {
            ((GlideConfigrationPermission)imageLoaderStrategy).GlideConfig(context, builder);
        }
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        //将glide内部网络框架替换为okHttp
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }
}
