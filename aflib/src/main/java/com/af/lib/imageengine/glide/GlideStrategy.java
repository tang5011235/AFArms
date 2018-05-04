package com.af.lib.imageengine.glide;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.af.lib.app.App;
import com.af.lib.app.component.AppComponent;
import com.af.lib.imageengine.BaseImageLoaderStrategy;
import com.af.lib.imageengine.imp.ImageConfigImp;
import com.af.lib.utils.FileUtils;
import com.af.lib.utils.Preconditions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：thf on 2018/5/3 0003 11:00
 * <p>
 * 邮箱：tang5011235@163.com
 */
public class GlideStrategy implements BaseImageLoaderStrategy<ImageConfigImp>, GlideConfigrationPermission {
    /**
     * 图片缓存文件最大值为100Mb
     */
    public static final int IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024;

    @Override
    public void loadImage(Object object, ImageConfigImp config) {
        Preconditions.checkNotNull(object, "Context is required");
        Preconditions.checkNotNull(config, "ImageConfigImpl is required");
        if (TextUtils.isEmpty(config.getUrl())) {
            throw new NullPointerException("Url is required");
        }

        GlideRequests requests;

        if (object instanceof FragmentActivity) {
            requests = (GlideRequests) Glide.with((FragmentActivity) object);
        } else if (object instanceof Activity) {
            requests = (GlideRequests) Glide.with((Activity) object);
        } else if (object instanceof android.app.Fragment) {
            requests = (GlideRequests) Glide.with((android.app.Fragment) object);
        } else if (object instanceof Fragment) {
            requests = (GlideRequests) Glide.with((Fragment) object);
        } else if (object instanceof View) {
            requests = (GlideRequests) Glide.with((View) object);
            config.setImageView((ImageView)object);
        } else if (object instanceof ContextWrapper) {
            requests = (GlideRequests) Glide.with((ContextWrapper) object);
        } else {
            throw new IllegalArgumentException("glide init fail,object is not available");
        }

        Preconditions.checkNotNull(config.getImageView(), "ImageView is reuired");
        GlideRequest<Drawable> glideRequest = requests.load(config.getUrl());

        //缓存策略
        switch (config.getCacheStrategy()) {
            case 0:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
            case 1:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case 2:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                break;
            case 3:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.DATA);
                break;
            case 4:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                break;
            default:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
        }

        if (config.isCrossFade()) {
            glideRequest.transition(DrawableTransitionOptions.withCrossFade());
        }

        if (config.isCenterCrop()) {
            glideRequest.centerCrop();
        }

        if (config.isCircle()) {
            glideRequest.circleCrop();
        }

        if (config.getImageRadius() > 0) {
            glideRequest.transform(new RoundedCorners(config.getImageRadius()));
        }

       /* if (config.getBlurValue() > 0) {
            glideRequest.transform(new BlurTransformation(config.getBlurValue()));
        }*/

        //glide用它来改变图形的形状
        if (config.getTransformation() != null) {
            glideRequest.transform(config.getTransformation());
        }
        //设置占位符
        if (config.getPlaceholder() != 0) {
            glideRequest.placeholder(config.getPlaceholder());
        }
        //设置错误的图片
        if (config.getErrorPic() != 0) {
            glideRequest.error(config.getErrorPic());
        }
        //设置请求 url 为空图片
        if (config.getFallback() != 0) {
            glideRequest.fallback(config.getFallback());
        }

        glideRequest
                .into(config.getImageView());
    }

    @SuppressLint("CheckResult")
    @Override
    public void clear(Context ctx, ImageConfigImp config) {
        Preconditions.checkNotNull(ctx, "Context is required");
        Preconditions.checkNotNull(config, "ImageConfigImpl is required");

        //取消正在执行的任务并且释放资源
        if (config.getImageViews() != null && config.getImageViews().length > 0) {
            for (ImageView imageView : config.getImageViews()) {
                Glide.get(ctx).getRequestManagerRetriever().get(ctx).clear(imageView);
            }
        }

        //清除本地缓存
        if (config.isClearDiskCache()) {
            Observable.just(0)
                    .observeOn(Schedulers.io())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(@NonNull Integer integer) throws Exception {
                            Glide.get(ctx).clearDiskCache();
                        }
                    });
        }

        //清除内存缓存
        if (config.isClearMemory()) {
            Observable.just(0)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(@NonNull Integer integer) throws Exception {
                            Glide.get(ctx).clearMemory();
                        }
                    });
        }
    }


    /**
     * 自行配置
     *
     * @param context
     * @param builder {@link GlideBuilder} 此类被用来创建 Glide
     */
    @Override
    public void GlideConfig(Context context, GlideBuilder builder) {
        AppComponent appComponent = ((App) context.getApplicationContext()).getAppComponent();

        builder.setDiskCache(new DiskCache.Factory() {
            @Nullable
            @Override
            public DiskCache build() {
                if (FileUtils.createOrExistsDir(appComponent.cacheFile())) {
                    return DiskLruCacheWrapper.create(appComponent.cacheFile(), IMAGE_DISK_CACHE_MAX_SIZE);
                } else {
                    throw new RuntimeException("can't create glide cacheFile");
                }
            }
        });


        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

    }
}
