package com.af.demo.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.af.lib.base.BaseActivity;
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
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

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
public class GankMainActivity extends BaseActivity implements OnRefreshListener, OnNetWorkErrorListener {


	@BindView(R.id.tv_select_date)
	TextView tvSelectDate;
	@BindView(R.id.rv_day_list)
	LRecyclerView mRvDayList;
	@BindView(R.id.empty_view)
	RelativeLayout mEmptyView;

	private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
	private String mCurrentDate;
	private List<GankIoDayDataBean> mDataBeanResults = new ArrayList<>();
	private ProgressDialog mProgressDialog;
	private TimePickerView mTimePickerView;

	private List<GankIoDayDataMultpleItem> mItemEntities = new ArrayList<>();
	private LRecyclerViewAdapter mLRecyclerViewAdapter;
	private PhotoView mIvFuli;
	private GankIoDayDateAdapter mDateAdapter;

	@Override
	public int setRootViewId() {
		return R.layout.activity_gank_main;
	}

	@Override
	public void initView(Bundle savedInstanceState) {
		ButterKnife.bind(this);
		mCurrentDate = "2018/5/25";
		mProgressDialog = ProgressDialog.getInstance(true);


		mRvDayList.setLayoutManager(new LinearLayoutManager(this));

		mDateAdapter = new GankIoDayDateAdapter(this);
		mRvDayList.setEmptyView(mEmptyView);
		mLRecyclerViewAdapter = new LRecyclerViewAdapter(mDateAdapter);
		View headerView = LayoutInflater.from(this).inflate(R.layout.adapter_header_gank_io_day_data, (ViewGroup) findViewById(android.R.id.content), false);
		mIvFuli = headerView.findViewById(R.id.iv_fuli);
		mLRecyclerViewAdapter.addHeaderView(headerView);

		mRvDayList.setAdapter(mLRecyclerViewAdapter);

		mRvDayList.setOnRefreshListener(this);
		mRvDayList.setOnNetWorkErrorListener(this);
		mRvDayList.setLoadMoreEnabled(false);
	}

	@Override
	public void loadData() {
		getDayDate(mCurrentDate);
	}

	@SuppressLint("CheckResult")
	private void getDayDate(String data) {
		AFManager.getService(RepositoryManager.class)
				.creatRepository(GankIoRepository.class)
				.getDayData(data, false)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.compose(GankMainActivity.this.bindUntilEvent(ActivityEvent.DESTROY))
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
					.loadImage(GankMainActivity.this, new ImageConfigImp.Builder()
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

	@Override
	public void showProgress() {
		mProgressDialog.show(getFragmentManager(), "1");
	}

	@Override
	public void hideProgress() {
		mProgressDialog.dismiss();
	}


	@OnClick(R.id.tv_select_date)
	public void onViewClicked() {
		// 默认全部显示
		if (mTimePickerView == null) {
			mTimePickerView = new TimePickerBuilder(GankMainActivity.this, new OnTimeSelectListener() {
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
	public void onRefresh() {
		mItemEntities.clear();
		getDayDate(mCurrentDate);
	}

	@Override
	public void reload() {
		mItemEntities.clear();
		getDayDate(mCurrentDate);
	}
}
