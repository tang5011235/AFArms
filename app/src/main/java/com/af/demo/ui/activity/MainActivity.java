package com.af.demo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.af.demo.R;
import com.af.demo.api.Bean.BaseResponse;
import com.af.demo.api.Bean.FuLiBean;
import com.af.demo.api.service.GankIoServices;
import com.af.lib.app.App;
import com.af.lib.app.component.AppComponent;
import com.af.lib.http.exception.rxjava.ErrorHandleSubscriber;
import com.af.lib.imageengine.imp.ImageConfigImp;
import com.af.lib.imageengine.imp.ImageLoder;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    Retrofit mRetrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppComponent appComponent = ((App) getApplication()).getAppComponent();
        mRetrofit = appComponent.retrofit();
        mRetrofit
                .create(GankIoServices.class)
                .getFuLi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<FuLiBean>>>(appComponent.rxExerrorHandler()) {
                    @Override
                    public void onNext(BaseResponse<List<FuLiBean>> listBaseResponse) {

                    }
                });

        ImageLoder.loadImage(new ImageConfigImp.Builder()
                .setImageView(findViewById(R.id.iv))
                .setUrl("https://avatars0.githubusercontent.com/u/15711968?s=460&v=4")
                .build());

    }
}
