package com.af.demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.af.demo.R;
import com.af.lib.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * 作者：thf on 2018/6/4 0004 14:29
 * <p>
 * 邮箱：tang5011235@163.com
 * <p>
 * name:AFArms
 * <p>
 * version:
 *
 * @description:
 */
public class MainFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {
	private final static String TITLE = "title";
	@BindView(R.id.fl_container)
	FrameLayout mFlContainer;
	@BindView(R.id.tab_main)
	TabLayout mTabMain;

	private List<ISupportFragment> mFragments;

	public static MainFragment getInstance(String title) {
		MainFragment fragment = new MainFragment();
		Bundle bundle = new Bundle();
		bundle.putString(TITLE, title);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected void initViews() {
		mFragments = new ArrayList<>();
		mFragments.add(GankIoDayDataFragment.getInstance("每日"));
		mFragments.add(GankIoCategoryFragment.getInstance("分类"));

		loadMultipleRootFragment(R.id.fl_container,
				0,
				mFragments.get(0),
				mFragments.get(1));

		mTabMain.addTab(mTabMain.newTab()
				.setText(((Fragment) mFragments.get(0))
						.getArguments()
						.getString(TITLE)));
		mTabMain.addTab(mTabMain.newTab()
				.setText(((Fragment) mFragments.get(1))
						.getArguments()
						.getString(TITLE)));

		mTabMain.addOnTabSelectedListener(this);
	}

	@Override
	public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_main, container, false);
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
