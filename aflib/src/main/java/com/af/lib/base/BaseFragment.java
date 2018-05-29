package com.af.lib.base;

import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.af.lib.app.App;
import com.af.lib.app.component.AppComponent;
import com.af.lib.utils.IViewProcess;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class BaseFragment extends SupportFragment implements LifecycleProvider<FragmentEvent>, IViewProcess {

	protected AppComponent mAppComponent;
	protected View mRootView;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAppComponent = ((App) (getActivity().getApplication())).getAppComponent();
		lifecycleSubject.onNext(FragmentEvent.CREATE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		mRootView = initView(inflater, container, savedInstanceState);
		return mRootView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		loadData();
	}

	/**
	 * 空实现用于activity
	 *
	 * @param savedInstanceState
	 */
	@Override
	public void initView(Bundle savedInstanceState) {

	}

	private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

	@Override
	@NonNull
	@CheckResult
	public final Observable<FragmentEvent> lifecycle() {
		return lifecycleSubject.hide();
	}

	@Override
	@NonNull
	@CheckResult
	public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
		return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
	}

	@Override
	@NonNull
	@CheckResult
	public final <T> LifecycleTransformer<T> bindToLifecycle() {
		return RxLifecycleAndroid.bindFragment(lifecycleSubject);
	}

	@Override
	public void onAttach(android.app.Activity activity) {
		super.onAttach(activity);
		lifecycleSubject.onNext(FragmentEvent.ATTACH);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
	}

	@Override
	public void onStart() {
		super.onStart();
		lifecycleSubject.onNext(FragmentEvent.START);
	}

	@Override
	public void onResume() {
		super.onResume();
		lifecycleSubject.onNext(FragmentEvent.RESUME);
	}

	@Override
	public void onPause() {
		lifecycleSubject.onNext(FragmentEvent.PAUSE);
		super.onPause();
	}

	@Override
	public void onStop() {
		lifecycleSubject.onNext(FragmentEvent.STOP);
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		lifecycleSubject.onNext(FragmentEvent.DESTROY);
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		lifecycleSubject.onNext(FragmentEvent.DETACH);
		super.onDetach();
	}
}
