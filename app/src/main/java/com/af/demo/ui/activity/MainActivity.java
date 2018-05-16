package com.af.demo.ui.activity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.af.demo.R;
import com.af.demo.api.Bean.FuLiBean;
import com.af.demo.api.GankIoRepository;
import com.af.lib.app.AFManager;
import com.af.lib.app.App;
import com.af.lib.base.BaseActivity;
import com.af.lib.http.exception.rxjava.ErrorHandleSubscriber;
import com.af.lib.imageengine.imp.ImageConfigImp;
import com.af.lib.imageengine.imp.ImageLoder;
import com.af.lib.utils.ProgressDialog;
import com.af.lib.utils.RxProcess;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends BaseActivity {


    Retrofit mRetrofit;
    @BindView(R.id.iv)
    ImageView mIv;

    @BindView(R.id.btn)
    Button mButton;

    private ProgressDialog mProgressDialog;

    @Override
    public void loadData() {

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @SuppressLint("CheckResult")
    @Override
    public void initView(Bundle savedInstanceState) {
        mProgressDialog = ProgressDialog.getInstance(true);
        ButterKnife.bind(this);
        mAppComponent = ((App) ((Application) AFManager.getService(Application.class))).getAppComponent();
        mRetrofit = mAppComponent.retrofit();


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
                    public void accept(Object o) {
                        mAppComponent.repositoryManager().creatRepository(GankIoRepository.class)
                                .getFuLi(false)
                                .subscribeOn(Schedulers.io())
                                .delaySubscription(500, TimeUnit.MILLISECONDS)
                                .compose(RxProcess.CommonProcess(MainActivity.this))
                                .compose(MainActivity.this.bindUntilEvent(ActivityEvent.DESTROY))
                                .subscribe(new ErrorHandleSubscriber<FuLiBean>(mAppComponent.rxExerrorHandler()) {
                                    @Override
                                    public void onNext(FuLiBean fuLiBean) {

                                    }
                                });


                        AFManager.getService(ImageLoder.class)
                                .loadImage(mIv, new ImageConfigImp.Builder()
                                        .setPlaceholder(R.mipmap.ic_launcher)
                                        .setUrl("https://github.com/YoKeyword/Fragmentation/raw/master/gif/logo.png")
                                        .setIsCircle(true)
                                        .build());
                    }
                });
    }

    @Override
    public void showProgress() {
        mProgressDialog.show(getFragmentManager(), "a");
    }

    @Override
    public void hideProgress() {
        mProgressDialog.dismiss();
    }

    @Override
    public int setRootViewId() {
        return R.layout.activity_main;
    }

}
