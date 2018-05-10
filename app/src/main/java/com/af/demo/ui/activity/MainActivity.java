package com.af.demo.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.af.demo.R;
import com.af.demo.api.Bean.FuLiBean;
import com.af.demo.api.GankIoRepository;
import com.af.lib.base.BaseActivity;
import com.af.lib.utils.ProgressDialog;
import com.af.lib.utils.RxProcess;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.android.ActivityEvent;

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
                    public void accept(Object o) throws Exception {
                        mAppComponent.repositoryManager().creatRepository(GankIoRepository.class)
                                .getFuLi(false)
                                .subscribeOn(Schedulers.io())
                                .delaySubscription(500, TimeUnit.MILLISECONDS)
                                .compose(RxProcess.CommonProcess(MainActivity.this))
                                .compose(MainActivity.this.bindUntilEvent(ActivityEvent.DESTROY))
                                .subscribe(new Consumer<FuLiBean>() {
                                    @Override
                                    public void accept(FuLiBean fuLiBean) throws Exception {
                                        System.out.println("wo de dai" + fuLiBean.getResults().get(0).getDesc());
                                    }
                                });
                       /* mAppComponent.getImageLoader().loadImage(mIv, new ImageConfigImp.Builder()
                                .setPlaceholder(R.mipmap.ic_launcher)
                                .setUrl("https://github.com/YoKeyword/Fragmentation/raw/master/gif/logo.png")
                                .setIsCircle(true)
                                .build());*/
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
