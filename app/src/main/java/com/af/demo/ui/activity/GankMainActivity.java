package com.af.demo.ui.activity;

import android.os.Bundle;

import com.af.demo.R;
import com.af.demo.ui.fragment.MainFragment;
import com.af.lib.base.BaseActivity;

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
public class GankMainActivity extends BaseActivity {


	@Override
	public int setRootViewId() {
		return R.layout.activity_gank_main;
	}

	@Override
	public void initView(Bundle savedInstanceState) {
		if (findFragment(MainFragment.class) == null) {
			loadRootFragment(R.id.container, MainFragment.getInstance("主页面"));
		}
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
