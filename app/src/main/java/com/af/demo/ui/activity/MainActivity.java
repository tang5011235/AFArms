package com.af.demo.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.af.demo.R;
import com.af.demo.api.Bean.BaseResponse;
import com.af.demo.api.Bean.FuLiBean;
import com.af.demo.api.service.GankIoServices;
import com.af.lib.base.BaseActivity;
import com.af.lib.http.exception.rxjava.ErrorHandleSubscriber;
import com.af.lib.imageengine.imp.ImageConfigImp;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends BaseActivity {


    Retrofit mRetrofit;
    @BindView(R.id.iv)
    ImageView mIv;

    @BindView(R.id.btn)
    Button mButton;

    @SuppressLint("CheckResult")
    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
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
                mIv, new ImageConfigImp.Builder()
                        .setUrl("https://avatars0.githubusercontent.com/u/15711968?s=460&v=4")
                        .setIsCircle(true)
                        .build()
        );

        RxView.clicks(mIv)
                .throttleWithTimeout(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        Toast.makeText(MainActivity.this, "sadf", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        RxView.clicks(mButton)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        sendMessage(mButton);
                    }
                });
    }

    @Override
    public int setRootViewId() {
        return R.layout.activity_main;
    }


    void sendMessage(Button button) {
        final long count = 3;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        button.setEnabled(false);
                        button.setTextColor(Color.BLACK);
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Long aLong) {
                        button.setText("剩余" + aLong + "秒");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        button.setEnabled(true);
                        button.setTextColor(Color.RED);
                        button.setText("发送验证码");
                    }
                });
    }

}