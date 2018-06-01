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
import com.af.demo.api.Bean.CategoryListBean;
import com.af.demo.api.GankIoRepository;
import com.af.demo.ui.adapter.CategoryListAdapter;
import com.af.lib.app.AFManager;
import com.af.lib.app.RepositoryManager;
import com.af.lib.base.BaseFragment;
import com.af.lib.http.exception.rxjava.ErrorHandleSubscriber;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
public class GankIoCategoryDataListFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.rv_data_list)
    LRecyclerView mRvDataList;
    @BindView(R.id.empty_view)
    RelativeLayout mEmptyView;

    public final static String TITLE = "title";
    public final static String TYPE = "type";

    private String type;
    private int pageNum = 1;
    private int totalPageNum;
    private CategoryListAdapter mDataAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;

    /**
     * 获取fragment
     *
     * @param title
     * @return
     */
    public static GankIoCategoryDataListFragment getInstance(String title, String type) {
        GankIoCategoryDataListFragment fragment = new GankIoCategoryDataListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gank_io_category_data_list, container, false);
    }

    @Override
    protected void initViews() {
        getArgumentData();

        mRvDataList.setEmptyView(mEmptyView);
        mRvDataList.setLayoutManager(new LinearLayoutManager(getContext()));

        mDataAdapter = new CategoryListAdapter(getContext());
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mDataAdapter);
        mRvDataList.setAdapter(mLRecyclerViewAdapter);
        mRvDataList.setOnRefreshListener(this);
        mRvDataList.setOnLoadMoreListener(this);
        mRvDataList.setNestedScrollingEnabled(false);

    }

    /**
     * 获取页面传送过来的数据
     */
    private void getArgumentData() {
        type = getArguments().getString(TYPE);
    }

    /**
     * 获取分类数据
     */
    public void getCategoryListData() {
        AFManager.getService(RepositoryManager.class)
                .creatRepository(GankIoRepository.class)
                .getCategoryListData(type, pageNum, true)
                .subscribeOn(Schedulers.io())
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<CategoryListBean>(mAppComponent.rxExerrorHandler()) {
                    @Override
                    public void onNext(CategoryListBean categoryListBean) {
                        mDataAdapter.addAll(categoryListBean.getResults());
                        mRvDataList.refreshComplete(10);
                        mLRecyclerViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@NonNull Throwable t) {
                        super.onError(t);
                        pageNum--;
                    }
                });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRvDataList.refresh();
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

    @Override
    public void onRefresh() {
        pageNum = 1;
        mDataAdapter.getDataList().clear();
        getCategoryListData();
    }

    @Override
    public void onLoadMore() {
        pageNum++;
        getCategoryListData();
    }
}
