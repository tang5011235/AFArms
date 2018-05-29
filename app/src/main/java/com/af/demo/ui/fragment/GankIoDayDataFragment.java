package com.af.demo.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
import com.github.chrisbanes.photoview.PhotoView;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
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
public class GankIoDayDataFragment extends BaseFragment implements OnRefreshListener, OnNetWorkErrorListener {

	@BindView(R.id.tv_select_date)
	TextView tvSelectDate;
	@BindView(R.id.rv_day_list)
	LRecyclerView mRvDayList;
	@BindView(R.id.empty_view)
	RelativeLayout mEmptyView;
	@BindView(R.id.empty_view_tv)
	TextView mEmptyViewTv;
	Unbinder unbinder;

	private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
	private String mCurrentDate;
	private ProgressDialog mProgressDialog;
	private TimePickerView mTimePickerView;

	private List<GankIoDayDataMultpleItem> mItemEntities = new ArrayList<>();
	private LRecyclerViewAdapter mLRecyclerViewAdapter;
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
	public void loadData() {
		mCurrentDate = TimeUtils.getNowString(mSimpleDateFormat);
		mProgressDialog = ProgressDialog.getInstance(true);
		mRvDayList.setLayoutManager(new LinearLayoutManager(getContext()));

		mDateAdapter = new GankIoDayDateAdapter(getContext());
		mRvDayList.setEmptyView(mEmptyView);
		mLRecyclerViewAdapter = new LRecyclerViewAdapter(mDateAdapter);
		View headerView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_header_gank_io_day_data, (ViewGroup) getActivity().findViewById(android.R.id.content), false);
		mIvFuli = headerView.findViewById(R.id.iv_fuli);
		mLRecyclerViewAdapter.addHeaderView(headerView);

		mRvDayList.setAdapter(mLRecyclerViewAdapter);

		mRvDayList.setOnRefreshListener(this);
		mRvDayList.setOnNetWorkErrorListener(this);
		mRvDayList.setLoadMoreEnabled(false);

		getDayDate(mCurrentDate);
	}

	@Override
	public View initView(@android.support.annotation.NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.fragment_gank_io_day_data, container, false);
		unbinder = ButterKnife.bind(this, inflate);
		return inflate;
	}


	@SuppressLint("CheckResult")
	private void getDayDate(String data) {
		AFManager.getService(RepositoryManager.class)
				.creatRepository(GankIoRepository.class)
				.getDayData(data, false)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.compose(GankIoDayDataFragment.this.bindUntilEvent(FragmentEvent.DESTROY))
				.subscribe(new ErrorHandleSubscriber<GankIoDayDataBean>(mAppComponent.rxExerrorHandler()) {
					@Override
					public void onNext(GankIoDayDataBean gankIoDayDataBean) {
						GankIoDayDataBean results = gankIoDayDataBean.getResults();
						makeData(gankIoDayDataBean, results);
					}

					@Override
					public void onError(@NonNull Throwable t) {
						super.onError(t);
						mRvDayList.refreshComplete(12);
					}
				});

	}

	private void makeData(GankIoDayDataBean gankIoDayDataBean, GankIoDayDataBean results) {
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
					mItemEntities.add(e);
				} else {
					GankIoDayDataMultpleItem e = new GankIoDayDataMultpleItem(GankIoDayDataMultpleItem.CONTENT);
					e.setData(results.getAndroid().get(i));
					mItemEntities.add(e);
				}
			}


			//添加IOS数据
			for (int i = 0; i < results.getIOS().size(); i++) {
				if (i == 0) {
					GankIoDayDataMultpleItem e = new GankIoDayDataMultpleItem(GankIoDayDataMultpleItem.TITLE);
					e.setData("IOS");
					mItemEntities.add(e);
				} else {
					GankIoDayDataMultpleItem e = new GankIoDayDataMultpleItem(GankIoDayDataMultpleItem.CONTENT);
					e.setData(results.getIOS().get(i));
					mItemEntities.add(e);
				}
			}
		}
		mDateAdapter.setDataList(mItemEntities);
		mRvDayList.refreshComplete(12);
	}

	@OnClick(R.id.tv_select_date)
	public void onViewClicked() {
		// 默认全部显示
		if (mTimePickerView == null) {
			mTimePickerView = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
				@Override
				public void onTimeSelect(Date date, View v) {
					mCurrentDate = TimeUtils.date2String(date, mSimpleDateFormat);
					mRvDayList.forceToRefresh();
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

	@Override
	public void onRefresh() {
		mItemEntities.clear();
		getDayDate(mCurrentDate);
	}

	@Override
	public void reload() {
		mItemEntities.clear();
		getDayDate(mCurrentDate);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}
}
