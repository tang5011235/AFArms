package com.af.lib.imageengine.imp;

import com.af.lib.app.App;
import com.af.lib.app.AppDelegate;
import com.af.lib.app.component.AppComponent;
import com.af.lib.imageengine.BaseImageLoaderStrategy;
import com.af.lib.imageengine.ImageConfig;

/**
 * 作者：thf on 2018/4/28 0028 10:31
 * <p>
 * 邮箱：tang5011235@163.com
 */
public class ImageLoder {
    static AppComponent appComponent = ((App) (AppDelegate.glideContextStack.get(AppDelegate.glideContextStack.size()-1)).getApplicationContext()).getAppComponent();
    static BaseImageLoaderStrategy sStrategy = appComponent.imageLoaderStrategy();
    public static void loadImage(ImageConfig config) {
        sStrategy.loadImage(AppDelegate.glideContextStack.get(AppDelegate.glideContextStack.size()-1), config);
    }

    public static void clear(ImageConfig config) {
        sStrategy.clear(AppDelegate.glideContextStack.get(AppDelegate.glideContextStack.size()-1), config);
    }
}
