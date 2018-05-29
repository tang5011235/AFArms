package com.af.lib.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.af.lib.R;
import com.trello.rxlifecycle2.components.support.RxDialogFragment;

/**
 * 作者：thf on 2018/5/7 0007 15:16
 * <p>
 * 邮箱：tang5011235@163.com
 */
public class ProgressDialog extends RxDialogFragment {
    private static final String IS_SHOW_DES = "isShowDes";

    public static ProgressDialog getInstance(boolean isShowDes) {
        ProgressDialog fragment = new ProgressDialog();
        Bundle args = new Bundle();
        args.putBoolean(IS_SHOW_DES, isShowDes);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        setStyle(R.style.CustomDatePickerDialog, 0);

        View view = inflater.inflate(R.layout.dialog_progress, null);
        ProgressBar progressBar = view.findViewById(R.id.def_progress);
        TextView textView = view.findViewById(R.id.des_state);
        textView.setVisibility((boolean) getArguments().get(IS_SHOW_DES) ? View.VISIBLE :
                View.INVISIBLE);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}