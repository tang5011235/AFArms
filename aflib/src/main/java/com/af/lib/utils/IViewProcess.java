package com.af.lib.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/12/6.
 */

public interface IViewProcess {
    void initView(Bundle savedInstanceState);
    View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);
    void loadData();
    void showProgress();
    void hideProgress();
}
