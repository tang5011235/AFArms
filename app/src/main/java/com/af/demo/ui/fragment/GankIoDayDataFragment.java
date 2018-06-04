package com.af.demo.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.af.demo.R;
import com.af.demo.api.Bean.GankIoDayDataBean;
import com.af.demo.api.Bean.GankIoDayDataMultpleItem;
import com.af.demo.api.GankIoRepository;
import com.af.demo.ui.adapter.GankIoDayDateAdapter;
import com.af.lib.app.AFManager;
import com.af.lib.app.RepositoryManager;
import com.af.lib.base.BaseFragment;
import com.af.lib.http.exception.rxjava.ErrorHandleSubscriber;
import com.af.lib.imageengine.imp.ImageConfigImp;
import com.af.lib.imageengine.imp.ImageLoder;
import com.af.lib.utils.ProgressDialog;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.chrisbanes.photoview.PhotoView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * @author thf
 * 作者：thf on 2018/5/29 0029 11:53
 * <p>
 * 邮箱：tang5011235@163.com
 * <p>
 * name:AFArms
 * <p>
 * version:
 *
 * @description:
 */
public class GankIoDayDataFragment extends BaseFragment implements com.scwang.smartrefresh.layout.listener.OnRefreshListener, OnLoadMoreListener {

	@BindView(R.id.tv_select_date)
	TextView tvSelectDate;
	@BindView(R.id.rv_day_list)
	RecyclerView mRvDayList;
	@BindView(R.id.smart_refresh_layout)
	SmartRefreshLayout mSmartRefreshLayout;

	private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
	private String mCurrentDate;
	private ProgressDialog mProgressDialog;
	private TimePickerView mTimePickerView;

	private List<GankIoDayDataMultpleItem> mDataLists = new ArrayList<>();
	private PhotoView mIvFuli;
	private GankIoDayDateAdapter mDateAdapter;

	private final static String TITLE = "title";

	/**
	 * 获取fragment
	 *
	 * @param title
	 * @return
	 */
	public static GankIoDayDataFragment getInstance(String title) {
		GankIoDayDataFragment gankIoDayDataFragment = new GankIoDayDataFragment();
		Bundle bundle = new Bundle();
		bundle.putString(TITLE, title);
		gankIoDayDataFragment.setArguments(bundle);
		return gankIoDayDataFragment;
	}

	@Override
	protected void initViews() {
		mCurrentDate = TimeUtils.getNowString(mSimpleDateFormat);
		mProgressDialog = ProgressDialog.getInstance(true);

		mSmartRefreshLayout.setOnRefreshListener(this);
		mSmartRefreshLayout.setOnLoadMoreListener(this);
		mSmartRefreshLayout.setEnableLoadMore(false);

		mRvDayList.setLayoutManager(new LinearLayoutManager(getContext()));

		mDateAdapter = new GankIoDayDateAdapter(mDataLists);
		View headerView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_header_gank_io_day_data, (ViewGroup) getActivity().findViewById(android.R.id.content), false);
		View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.empty_view, (ViewGroup) getActivity().findViewById(android.R.id.content), false);
		mIvFuli = headerView.findViewById(R.id.iv_fuli);
		mDateAdapter.setEnableLoadMore(false);
		mDateAdapter.addHeaderView(headerView);
		mDateAdapter.setEmptyView(emptyView);
		mDateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);

		mDateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				if(mDataLists.get(position).getItemType() == GankIoDayDataMultpleItem.CONTENT){
					((MainFragment)getParentFragment()).start(DetailWebViewFragment.getInstance("详情",((GankIoDayDataBean.ItemBean)(mDataLists.get(position).getData())).getUrl()));
				}
			}
		});
		mRvDayList.setAdapter(mDateAdapter);
	}


	@Override
	public void loadData() {
		getDayDate(mCurrentDate);
	}

	@Override
	public View initView(@android.support.annotation.NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.fragment_gank_io_day_data, container, false);
		return inflate;
	}


	@SuppressLint("CheckResult")
	private void getDayDate(String data) {
		AFManager.getService(RepositoryManager.class)
				.creatRepository(GankIoRepository.class)
				.getDayData(data, true)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.compose(GankIoDayDataFragment.this.bindUntilEvent(FragmentEvent.DESTROY))
				.subscribe(new ErrorHandleSubscriber<GankIoDayDataBean>(mAppComponent.rxExerrorHandler()) {
					@Override
					public void onNext(GankIoDayDataBean gankIoDayDataBean) {
						GankIoDayDataBean results = gankIoDayDataBean.getResults();
						makeData(gankIoDayDataBean, results);
						mDateAdapter.setNewData(mDataLists);
						mSmartRefreshLayout.finishRefresh();
					}

					@Override
					public void onError(@NonNull Throwable t) {
						super.onError(t);
						mDateAdapter.setNewData(mDataLists);
						mSmartRefreshLayout.finishRefresh();
					}
				});

	}

	private void makeData(GankIoDayDataBean gankIoDayDataBean, GankIoDayDataBean results) {
		mDataLists.clear();
		if (results != null && gankIoDayDataBean.getCategory().size() > 0) {
			AFManager.getService(ImageLoder.class)
					.loadImage(getActivity(), new ImageConfigImp.Builder()
							.setImageView(mIvFuli)
							.setPlaceholder(R.mipmap.ic_launcher)
							.setUrl(results.getFuLi().get(0).getUrl())
							.build());

			//添加Android数据
			for (int i = 0; i < results.getAndroid().size(); i++) {
				if (i == 0) {
					GankIoDayDataMultpleItem e = new GankIoDayDataMultpleItem(GankIoDayDataMultpleItem.TITLE);
					e.setData("Android");
					mDataLists.add(e);
				} else {
					GankIoDayDataMultpleItem e = new GankIoDayDataMultpleItem(GankIoDayDataMultpleItem.CONTENT);
					e.setData(results.getAndroid().get(i));
					mDataLists.add(e);
				}
			}


			//添加IOS数据
			for (int i = 0; i < results.getIOS().size(); i++) {
				if (i == 0) {
					GankIoDayDataMultpleItem e = new GankIoDayDataMultpleItem(GankIoDayDataMultpleItem.TITLE);
					e.setData("IOS");
					mDataLists.add(e);
				} else {
					GankIoDayDataMultpleItem e = new GankIoDayDataMultpleItem(GankIoDayDataMultpleItem.CONTENT);
					e.setData(results.getIOS().get(i));
					mDataLists.add(e);
				}
			}
		}
	}

	@OnClick(R.id.tv_select_date)
	public void onViewClicked() {
		// 默认全部显示
		if (mTimePickerView == null) {
			mTimePickerView = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
				@Override
				public void onTimeSelect(Date date, View v) {
					mCurrentDate = TimeUtils.date2String(date, mSimpleDateFormat);
					mSmartRefreshLayout.autoRefresh();
				}
			}).setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
					.build();
		}
		mTimePickerView.show();
	}

	@Override
	public void showProgress() {
		mProgressDialog.show(getFragmentManager(), "1");
	}

	@Override
	public void hideProgress() {
		mProgressDialog.dismiss();
	}

	/**
	 * 下拉刷新
	 */
	@Override
	public void onRefresh(RefreshLayout refreshLayout) {
		getDayDate(mCurrentDate);
	}

	/**
	 * 网络错误重点
	 */
	@Override
	public void onLoadMore(RefreshLayout refreshLayout) {
		mDataLists.clear();
		getDayDate(mCurrentDate);
	}
}
