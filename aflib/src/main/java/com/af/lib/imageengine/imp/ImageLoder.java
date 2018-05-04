package com.af.lib.imageengine.imp;

import android.content.Context;

import com.af.lib.imageengine.BaseImageLoaderStrategy;
import com.af.lib.imageengine.ImageConfig;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 作者：thf on 2018/4/28 0028 10:31
 * <p>
 * 邮箱：tang5011235@163.com
 */
@Singleton
public class ImageLoder {
    @Inject
    BaseImageLoaderStrategy mStrategy;

    @Inject
    public ImageLoder() {
    }

    public void loadImage(Object object, ImageConfig config) {
        mStrategy.loadImage(object, config);
    }

    public void clear(Context context,ImageConfig config) {
        mStrategy.clear(context, config);
    }
}
