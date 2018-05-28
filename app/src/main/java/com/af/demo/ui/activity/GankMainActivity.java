package com.af.demo.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.af.demo.R;
import com.af.demo.api.Bean.GankIoDayDataBean;
import com.af.demo.api.GankIoRepository;
import com.af.lib.app.AFManager;
import com.af.lib.app.RepositoryManager;
import com.af.lib.base.BaseActivity;
import com.af.lib.http.exception.rxjava.ErrorHandleSubscriber;
import com.af.lib.utils.ProgressDialog;
import com.af.lib.utils.RxProcess;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.TimeUtils;
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

	@Override
	public int setRootViewId() {
		return R.layout.activity_gank_main;
	}

	@Override
	public void initView(Bundle savedInstanceState) {
		ButterKnife.bind(this);
		mCurrentDate = TimeUtils.getNowString(mSimpleDateFormat);
		mProgressDialog = ProgressDialog.getInstance(true);

		mRvDayList.setOnRefreshListener(this);
		mRvDayList.setOnNetWorkErrorListener(this);
		mRvDayList.setEmptyView(mEmptyView);
		mRvDayList.setLoadMoreEnabled(false);
		mRvDayList.setLayoutManager(new LinearLayoutManager(this));

		LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter();
		mRvDayList.setAdapter();
	}

	@Override
	public void loadData() {
		getDayDate(mCurrentDate);
	}

	@SuppressLint("CheckResult")
	private void getDayDate(String data) {
		AFManager.getService(RepositoryManager.class)
				.creatRepository(GankIoRepository.class)
				.getDayData(data, true)
				.subscribeOn(Schedulers.io())
				.compose(RxProcess.CommonProcess(GankMainActivity.this))
				.compose(GankMainActivity.this.bindUntilEvent(ActivityEvent.DESTROY))
				.subscribe(new ErrorHandleSubscriber<GankIoDayDataBean>(mAppComponent.rxExerrorHandler()) {
					@Override
					public void onNext(GankIoDayDataBean gankIoDayDataBean) {
						mDataBeanResults = gankIoDayDataBean.getResults();
						if (mDataBeanResults.size() == 0) {
						} else {
							/*String css = "<style type=\"text/css\"> </style>";
							String html = "<html><header><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=0.8, user-scalable=no>"+css+"</header>"+"<body>"+mDataBeanResults.get(0).getContent()+"</body>"+"</html>";*/
						}
					}
				});

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
				}
			}).setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
					.build();
		}
		mTimePickerView.show();
	}

	@Override
	public void onRefresh() {
		getDayDate(mCurrentDate);
	}

	@Override
	public void reload() {

	}
}
