package com.af.demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.af.lib.base.BaseFragment;

/**
 * 作者：thf on 2018/5/29 0029 12:01
 * <p>
 * 邮箱：tang5011235@163.com
 * <p>
 * name:AFArms
 * <p>
 * version:
 *
 * @description:
 */
public class GankIoCategoryFragment extends BaseFragment {

	private final static String TITLE = "title";

	/**
	 * 获取fragment
	 *
	 * @param title
	 * @return
	 */
	public static GankIoCategoryFragment getInstance(String title) {
		GankIoCategoryFragment gankIoCategoryFragment = new GankIoCategoryFragment();
		Bundle bundle = new Bundle();
		bundle.putString(TITLE, title);
		gankIoCategoryFragment.setArguments(bundle);
		return gankIoCategoryFragment;
	}

	@Override
	public void initView(Bundle savedInstanceState) {

	}

	@Override
	public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return null;
	}


	@Override
	public void loadData() {

	}


	@Override
	public void showProgress() {

	}

	@Override
	public void hideProgress() {

	}
}
