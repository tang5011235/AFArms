package com.af.demo.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.af.demo.ui.fragment.GankIoCategoryDataListFragment;

import java.util.List;

/**
 * Created by HaoFeng on 2018/5/31 0031.
 */

public class CategoryAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public CategoryAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getArguments().getString(GankIoCategoryDataListFragment.TITLE);
    }
}
