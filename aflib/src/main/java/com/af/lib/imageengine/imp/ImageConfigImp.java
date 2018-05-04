package com.af.lib.imageengine.imp;

import android.widget.ImageView;

import com.af.lib.imageengine.ImageConfig;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * 作者：thf on 2018/5/3 0003 10:31
 * <p>
 * 邮箱：tang5011235@163.com
 */
public class ImageConfigImp extends ImageConfig {
    private int cacheStrategy;//0对应DiskCacheStrategy.all,1对应DiskCacheStrategy.NONE,2对应DiskCacheStrategy.SOURCE,3对应DiskCacheStrategy.RESULT
    private int fallback; //请求 url 为空,则使用此图片作为占位符
    private int imageRadius;//图片每个圆角的大小
    private int blurValue;//高斯模糊值, 值越大模糊效果越大

    @Deprecated
    private BitmapTransformation transformation;//glide用它来改变图形的形状
    private ImageView[] imageViews;
    private boolean isCrossFade;//是否使用淡入淡出过渡动画
    private boolean isCenterCrop;//是否将图片剪切为 CenterCrop
    private boolean isCircle;//是否将图片剪切为圆形
    private boolean isClearMemory;//清理内存缓存
    private boolean isClearDiskCache;//清理本地缓存
    private int mCacheStrategy;

    private ImageConfigImp(Builder builder) {
        url = builder.url;
        imageView = builder.imageView;
        placeholder = builder.placeholder;
        errorPic = builder.errorPic;
        cacheStrategy = builder.cacheStrategy;
        fallback = builder.fallback;
        imageRadius = builder.imageRadius;
        blurValue = builder.blurValue;
        transformation = builder.transformation;
        imageViews = builder.imageViews;
        isCrossFade = builder.isCrossFade;
        isCenterCrop = builder.isCenterCrop;
        isCircle = builder.isCircle;
        isClearMemory = builder.isClearMemory;
        isClearDiskCache = builder.isClearDiskCache;
        mCacheStrategy = builder.mCacheStrategy;
    }

    public void setImageView(ImageView view){
        imageView = view;
    }

    public int getFallback() {
        return fallback;
    }

    public int getImageRadius() {
        return imageRadius;
    }

    public int getBlurValue() {
        return blurValue;
    }

    public BitmapTransformation getTransformation() {
        return transformation;
    }

    public ImageView[] getImageViews() {
        return imageViews;
    }

    public boolean isCrossFade() {
        return isCrossFade;
    }

    public boolean isCenterCrop() {
        return isCenterCrop;
    }

    public boolean isCircle() {
        return isCircle;
    }

    public boolean isClearMemory() {
        return isClearMemory;
    }

    public boolean isClearDiskCache() {
        return isClearDiskCache;
    }

    public int getCacheStrategy() {
        return mCacheStrategy;
    }


    public static final class Builder {
        private int cacheStrategy;
        private int fallback;
        private int imageRadius;
        private int blurValue;
        private BitmapTransformation transformation;
        private ImageView[] imageViews;
        private boolean isCrossFade;
        private boolean isCenterCrop;
        private boolean isCircle;
        private boolean isClearMemory;
        private boolean isClearDiskCache;
        private int mCacheStrategy;
        private String url;
        private ImageView imageView;
        private int placeholder;
        private int errorPic;

        public Builder() {
        }


        public Builder setCacheStrategy(int cacheStrategy) {
            this.cacheStrategy = cacheStrategy;
            return this;
        }

        public Builder setFallback(int fallback) {
            this.fallback = fallback;
            return this;
        }

        public Builder setImageRadius(int imageRadius) {
            this.imageRadius = imageRadius;
            return this;
        }

        public Builder setBlurValue(int blurValue) {
            this.blurValue = blurValue;
            return this;
        }

        public Builder setTransformation(BitmapTransformation transformation) {
            this.transformation = transformation;
            return this;
        }

        public Builder setImageViews(ImageView[] imageViews) {
            this.imageViews = imageViews;
            return this;
        }

        public Builder setIsCrossFade(boolean isCrossFade) {
            this.isCrossFade = isCrossFade;
            return this;
        }

        public Builder setIsCenterCrop(boolean isCenterCrop) {
            this.isCenterCrop = isCenterCrop;
            return this;
        }

        public Builder setIsCircle(boolean isCircle) {
            this.isCircle = isCircle;
            return this;
        }

        public Builder setIsClearMemory(boolean isClearMemory) {
            this.isClearMemory = isClearMemory;
            return this;
        }

        public Builder setIsClearDiskCache(boolean isClearDiskCache) {
            this.isClearDiskCache = isClearDiskCache;
            return this;
        }

        public Builder setMCacheStrategy(int mCacheStrategy) {
            this.mCacheStrategy = mCacheStrategy;
            return this;
        }

        public ImageConfigImp build() {
            return new ImageConfigImp(this);
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setImageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder setPlaceholder(int placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public Builder setErrorPic(int errorPic) {
            this.errorPic = errorPic;
            return this;
        }
    }
}
