package com.af.demo.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.af.demo.R;
import com.af.demo.ui.fragment.GankIoCategoryFragment;
import com.af.demo.ui.fragment.GankIoDayDataFragment;
import com.af.lib.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * 作者：thf on 2018/5/28 0028 11:57
 * <p>
 * 邮箱：tang5011235@163.com
 * <p>
 * name:AFArms
 * <p>
 * version:
 *
 * @description:
 */
public class GankMainActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {
	private final static String FRAGMENT_TITLE = "title";

	@BindView(R.id.tab_main)
	TabLayout mTabMain;
	private List<ISupportFragment> mFragments;

	@Override
	public int setRootViewId() {
		return R.layout.activity_gank_main;
	}

	@Override
	public void initView(Bundle savedInstanceState) {
		ButterKnife.bind(this);
		initFragments();
		mTabMain.addTab(mTabMain.newTab()
				.setText(((Fragment) mFragments.get(0))
						.getArguments()
						.getString(FRAGMENT_TITLE)));
		mTabMain.addTab(mTabMain.newTab()
				.setText(((Fragment) mFragments.get(1))
						.getArguments()
						.getString(FRAGMENT_TITLE)));

		mTabMain.addOnTabSelectedListener(this);
	}

	/**
	 * 初始化fragment
	 */
	private void initFragments() {
		mFragments = new ArrayList<>();
		mFragments.add(GankIoDayDataFragment.getInstance("每日"));
		mFragments.add(GankIoCategoryFragment.getInstance("分类"));
		loadMultipleRootFragment(R.id.container,
				0,
				mFragments.get(0),
				mFragments.get(1));
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


	/**
	 * 切换fragment
	 * @param tab
	 */
	@Override
	public void onTabSelected(TabLayout.Tab tab) {
		showHideFragment(mFragments.get(tab.getPosition()));
	}

	@Override
	public void onTabUnselected(TabLayout.Tab tab) {

	}

	@Override
	public void onTabReselected(TabLayout.Tab tab) {

	}
}
