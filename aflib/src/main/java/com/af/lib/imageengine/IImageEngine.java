package com.af.lib.imageengine;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

public interface IImageEngine {
    void loadImage(Context context,ImageView imageView, Uri uri);

    void placeHolder(@DrawableRes int id);

    void error(@DrawableRes int id);
}
