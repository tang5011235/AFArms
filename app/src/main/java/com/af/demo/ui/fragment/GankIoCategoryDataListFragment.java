package com.af.demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.af.demo.R;
import com.af.lib.base.BaseFragment;
import com.github.jdsjlzx.recyclerview.LRecyclerView;

import butterknife.BindView;

/**
 * 作者：thf on 2018/5/31 0031 16:46
 * <p>
 * 邮箱：tang5011235@163.com
 * <p>
 * name:AFArms
 * <p>
 * version:
 *
 * @description:
 */
public class GankIoCategoryDataListFragment extends BaseFragment {
	@BindView(R.id.rv_data_list)
	LRecyclerView mRvDataList;
	@BindView(R.id.empty_view)
	RelativeLayout mEmptyView;

	@Override
	public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_gank_io_category_data_list, container, false);
	}

	@Override
	protected void initViews() {
		mRvDataList.setEmptyView(mEmptyView);
		mRvDataList.setLayoutManager(new LinearLayoutManager(getContext()));


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
