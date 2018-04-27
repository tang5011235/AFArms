package com.af.demo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.af.demo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*RetrofitClient.getInstance().getRetrofit()
                .create(GankIoServices.class)
                .getFuLi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<FuLiBean>>>(BaseApplication.mRxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<FuLiBean>> listBaseResponse) {

                    }
                });*/
    }
}
