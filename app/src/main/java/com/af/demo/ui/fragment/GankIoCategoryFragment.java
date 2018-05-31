package com.af.demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.af.demo.R;
import com.af.lib.base.BaseFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
	private List<String> mFragmentTiList = new ArrayList<>();
	@BindView(R.id.tablayout_category)
	SlidingTabLayout mTablayoutCategory;
	@BindView(R.id.vp_category)
	ViewPager mVpCategory;

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
	public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_gank_io_category, container, false);
	}

	@Override
	protected void initViews() {
		mFragmentTiList.add("全部");
		mFragmentTiList.add("Android");
		mFragmentTiList.add("iOS ");
		mFragmentTiList.add("福利");
		mFragmentTiList.add("休息视频");
		mFragmentTiList.add("拓展资源");
		mFragmentTiList.add("前端");
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
