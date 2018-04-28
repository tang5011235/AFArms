package com.af.lib.imageengine.imp;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.af.lib.imageengine.IImageEngine;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * 作者：thf on 2018/4/28 0028 10:31
 * <p>
 * 邮箱：tang5011235@163.com
 */
public class GlidEngine implements IImageEngine {

    public GlidEngine() {
    }

    @Override
    public void loadImage(Context context,ImageView imageView, Uri uri) {
        Glide.with(context)
                .load(uri)
                .apply(new RequestOptions().circleCrop());
    }

    @Override
    public void placeHolder(int id) {

    }

    @Override
    public void error(int id) {

    }
}
