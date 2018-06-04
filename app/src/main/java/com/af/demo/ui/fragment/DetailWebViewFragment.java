package com.af.demo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.af.demo.R;
import com.af.demo.ui.sonic.SonicImpl;
import com.af.demo.ui.sonic.SonicJavaScriptInterface;
import com.af.lib.base.BaseFragment;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.MiddlewareWebClientBase;

import butterknife.BindView;

import static com.af.demo.ui.sonic.SonicJavaScriptInterface.PARAM_CLICK_TIME;

/**
 * 作者：thf on 2018/6/4 0004 13:40
 * <p>
 * 邮箱：tang5011235@163.com
 * <p>
 * name:AFArms
 * <p>
 * version:
 *
 * @description:
 */
public class DetailWebViewFragment extends BaseFragment {
	private final static String TITLE = "title";
	private final static String URL = "url";
	@BindView(R.id.fl_web)
	FrameLayout mFlWeb;
	private String title;
	private String url;
	private AgentWeb mAgentWeb;
	private SonicImpl mSonicImpl;

	public static DetailWebViewFragment getInstance(String title, String url) {
		DetailWebViewFragment fragment = new DetailWebViewFragment();
		Bundle bundle = new Bundle();
		bundle.putString(TITLE, title);
		bundle.putString(URL, url);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected void initViews() {
		initArguments();


		// 1. 首先创建SonicImpl
		mSonicImpl = new SonicImpl(url, this.getContext());
		// 2. 调用 onCreateSession
		mSonicImpl.onCreateSession();
		//3. 创建AgentWeb ，注意创建AgentWeb的时候应该使用加入SonicWebViewClient中间件
		mAgentWeb = AgentWeb.with(this)
				.setAgentWebParent(mFlWeb, new LinearLayout.LayoutParams(-1, -1))
				.useDefaultIndicator()
				.useMiddlewareWebClient(getMiddlewareWebClient())
				.createAgentWeb()
				.ready()
				.go(url);
		//4. 注入 JavaScriptInterface
		mAgentWeb.getJsInterfaceHolder().addJavaObject("sonic", new SonicJavaScriptInterface(mSonicImpl.getSonicSessionClient(), new Intent().putExtra(PARAM_CLICK_TIME,getArguments().getLong(PARAM_CLICK_TIME)).putExtra("loadUrlTime", System.currentTimeMillis())));
		//5. 最后绑定AgentWeb
		mSonicImpl.bindAgentWeb(mAgentWeb);
	}

	public MiddlewareWebClientBase getMiddlewareWebClient() {
		return mSonicImpl.createSonicClientMiddleWare();
	}

	private void initArguments() {
		title = getArguments().getString(TITLE);
		url = getArguments().getString(URL);
	}

	@Override
	public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_detail_web_view, container, false);
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
