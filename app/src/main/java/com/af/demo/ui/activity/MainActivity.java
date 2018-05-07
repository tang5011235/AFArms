package com.af.demo.ui.activity;

import android.os.Bundle;

import com.af.demo.R;
import com.af.demo.api.Bean.BaseResponse;
import com.af.demo.api.Bean.FuLiBean;
import com.af.demo.api.service.GankIoServices;
import com.af.lib.base.BaseActivity;
import com.af.lib.http.exception.rxjava.ErrorHandleSubscriber;
import com.af.lib.imageengine.imp.ImageConfigImp;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends BaseActivity {


    Retrofit mRetrofit;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRetrofit = mAppComponent.retrofit();
        mRetrofit
                .create(GankIoServices.class)
                .getFuLi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<FuLiBean>>>(mAppComponent.rxExerrorHandler()) {
                    @Override
                    public void onNext(BaseResponse<List<FuLiBean>> listBaseResponse) {

                    }
                });

        mAppComponent.getImageLoader().loadImage(
                findViewById(R.id.iv), new ImageConfigImp.Builder()
                        .setUrl("https://avatars0.githubusercontent.com/u/15711968?s=460&v=4")
                        .setIsCircle(true)
                        .build()
        );
    }

    @Override
    public int setRootViewId() {
        return R.layout.activity_main;
    }
}
