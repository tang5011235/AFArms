package com.af.demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.af.demo.R;
import com.af.demo.api.Bean.CategoryListBean;
import com.af.demo.api.GankIoRepository;
import com.af.demo.ui.adapter.CategoryListAdapter;
import com.af.lib.app.AFManager;
import com.af.lib.app.RepositoryManager;
import com.af.lib.base.BaseFragment;
import com.af.lib.http.exception.rxjava.ErrorHandleSubscriber;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

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
public class GankIoCategoryDataListFragment extends BaseFragment implements com.scwang.smartrefresh.layout.listener.OnRefreshListener, com.scwang.smartrefresh.layout.listener.OnLoadMoreListener {
	@BindView(R.id.rv_data_list)
	RecyclerView mRvDataList;
	@BindView(R.id.srl)
	SmartRefreshLayout mSrl;

	public final static String TITLE = "title";
	public final static String TYPE = "type";


	private String type;
	private int pageNum = 1;
	private CategoryListAdapter mDataAdapter;
	private List<CategoryListBean> mDataList = new ArrayList<>();

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

		mSrl.setOnRefreshListener(this);
		mSrl.setOnLoadMoreListener(this);

		mRvDataList.setLayoutManager(new LinearLayoutManager(getContext()));

		mDataAdapter = new CategoryListAdapter(R.layout.adapter_category_list, mDataList);
		mDataAdapter.setEnableLoadMore(false);
		mDataAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
		mDataAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				((MainFragment)(getParentFragment().getParentFragment())).start(DetailWebViewFragment.getInstance("详情",mDataList.get(position).getUrl()));
			}
		});
		mRvDataList.setAdapter(mDataAdapter);
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
	public void getCategoryListData(boolean isRefresh) {
		AFManager.getService(RepositoryManager.class)
				.creatRepository(GankIoRepository.class)
				.getCategoryListData(type, pageNum, false)
				.subscribeOn(Schedulers.io())
				.compose(this.bindToLifecycle())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new ErrorHandleSubscriber<CategoryListBean>(mAppComponent.rxExerrorHandler()) {
					@Override
					public void onNext(CategoryListBean categoryListBean) {
						final int size = categoryListBean == null ? 0 : categoryListBean.getResults().size();
						if (isRefresh) {
							//上拉刷新
							mDataAdapter.setNewData(categoryListBean.getResults());
							mSrl.finishRefresh();
						} else {
							//下拉加载更多
							mDataAdapter.addData(categoryListBean.getResults());
							mSrl.finishLoadMore();
						}

						//设置没有更多数据界面
						if (size < 10) {
							mSrl.setNoMoreData(true);
						}
						mDataList = mDataAdapter.getData();
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
		mSrl.autoRefresh();
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
	public void onRefresh(RefreshLayout refreshLayout) {
		pageNum = 1;
		getCategoryListData(true);
	}

	@Override
	public void onLoadMore(RefreshLayout refreshLayout) {
		pageNum++;
		getCategoryListData(false);
	}
}
