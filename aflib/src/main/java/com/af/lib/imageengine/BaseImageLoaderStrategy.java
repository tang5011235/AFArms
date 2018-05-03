package com.af.lib.imageengine;

import android.content.Context;

public interface BaseImageLoaderStrategy<T extends ImageConfig> {

    /**
     * 加载图片
     *
     * @param ctx
     * @param config
     */
    void loadImage(Context ctx, T config);

    /**
     * 停止加载
     *
     * @param ctx
     * @param config
     */
    void clear(Context ctx, T config);
}