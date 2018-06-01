package com.af.demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.af.demo.R;
import com.af.demo.ui.adapter.CategoryAdapter;
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
    private List<Fragment> mFragmentList = new ArrayList<>();
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
        initFragmentList();

        mVpCategory.setAdapter(new CategoryAdapter(getChildFragmentManager(), mFragmentList));
        mTablayoutCategory.setViewPager(mVpCategory);
        mVpCategory.setOffscreenPageLimit(mFragmentList.size());
    }

    /**
     * 初始化FragmentList集合
     */
    private void initFragmentList() {
        mFragmentList.add(GankIoCategoryDataListFragment.getInstance("全部", "all"));
        mFragmentList.add(GankIoCategoryDataListFragment.getInstance("Android", "Android"));
        mFragmentList.add(GankIoCategoryDataListFragment.getInstance("iOS", "iOS"));
        mFragmentList.add(GankIoCategoryDataListFragment.getInstance("福利", "福利"));
        mFragmentList.add(GankIoCategoryDataListFragment.getInstance("休息视频", "休息视频"));
        mFragmentList.add(GankIoCategoryDataListFragment.getInstance("拓展资源", "拓展资源"));
        mFragmentList.add(GankIoCategoryDataListFragment.getInstance("前端", "前端"));
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
