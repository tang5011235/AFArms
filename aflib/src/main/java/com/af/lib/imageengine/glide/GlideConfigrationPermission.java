package com.af.lib.imageengine.glide;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;

public interface GlideConfigrationPermission {

    /**
     * 配置 Glide的必须参数，通过此方法判定是否是glid的配置
     *
     * @param context
     * @param builder {@link GlideBuilder} 此类被用来创建 Glide
     */
    void GlideConfig(Context context, GlideBuilder builder);
}