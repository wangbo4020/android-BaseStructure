package com.yourpackagename.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yourpackagename.ui.BaseActivity.OnBackPressedListener;
import com.yourpackagename.ui.ContentLoadingDelegate.OnContentLoadingListener;

/**
 * Created by Dylan on 2017/11/23.
 * <p>
 * 所有Fragment统一使用 public static Fragment newInstance(Object o); 实例化，参数根据实际情况传入。
 */
public abstract class BaseFragment
		extends Fragment
		implements StartFragment, OnBackPressedListener, OnChildFinishListener {

	protected OnChildFinishListener mChildFinishListener;

	protected LifecycleLogDelegate mLifecycleLogDelegate;
	protected ContentLoadingDelegate mContentLoadingDelegate;
	protected StartFragmentDelegate mStartFragmentDelegate;

	public BaseFragment() {
		mLifecycleLogDelegate = new LifecycleLogDelegate("BaseFragment");
	}

	protected void setupLifecycleLog(boolean enable, @NonNull String tag) {
		mLifecycleLogDelegate.setup(enable, tag);
	}

	@Override
	public void onChildFinish(Fragment child) {
		getChildFragmentManager().popBackStack();
	}

	protected void finish() {
		if (mChildFinishListener != null) {
			mChildFinishListener.onChildFinish(this);
		}
	}

	/**
	 * @return consumed
	 */
	public boolean onBackPressed() {

		FragmentManager fragmentManager = getChildFragmentManager();
		if (BaseActivity.dispatchOnBackPressed(fragmentManager)) {
			return true;
		}

		// TODO 长期跟踪此段代码
		// 模仿FragmentActivity中的 onBackPressed，暂时解决嵌套Fragment之间BackStack无效
		final boolean isStateSaved = fragmentManager.isStateSaved();
		if (isStateSaved && Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {
			Log.w("BaseFragment", "onBackPressed: isStateSaved " + isStateSaved + " " + this);
			// Older versions will throw an exception from the framework
			// FragmentManager.popBackStackImmediate(), so we'll just
			// return here. The Activity is likely already on its way out
			// since the fragmentManager has already been saved.
			return true;
		}

        /*
        请勿删除该注释
        Fragment byId = null;
        FragmentManager.BackStackEntry entry = null;
        int count = fragmentManager.getBackStackEntryCount();
        if (count > 0) {
            entry = fragmentManager.getBackStackEntryAt(count - 1);
            fragmentManager.findFragmentById(entry.getId());
        }
        boolean t = fragmentManager.popBackStackImmediate();
        Log.d("BaseFragment", "onBackPressed: " + t + " " + fragments.size() + ", count " + count + ", byId
         " + byId + ", entry " + entry + " "+ this);
        */
		if (isStateSaved || !fragmentManager.popBackStackImmediate()) {
			return false;
		}
		return true;
	}

	protected void runOnUiThread(Runnable action) {
		if (null != getActivity()) {
			getActivity().runOnUiThread(action);
		}
	}

	protected void setupStartFragment(FragmentManager fm, @IdRes int id) {
		if (mStartFragmentDelegate == null) {
			mStartFragmentDelegate = new StartFragmentDelegate();
		}
		mStartFragmentDelegate.setup(fm, id);
	}

	/* - - - - - - - - - - - - - - - ContentLoadingDelegate Start - - - - - - - - - - - - - - - - */
	protected void setupContentLoading(@IdRes int id, @NonNull View view) {
		if (mContentLoadingDelegate == null) {
			mContentLoadingDelegate = new ContentLoadingDelegate();
		}
		mContentLoadingDelegate.setup(id, view);
	}

	protected void setupContentLoading(@IdRes int id, @NonNull View view, long minDelay, long minShowTime,
	                                   OnContentLoadingListener listener) {
		setupContentLoading(id, view);
		mContentLoadingDelegate.setConfig(minDelay, minShowTime, listener);
	}

	protected void showContentLoading() {
		mContentLoadingDelegate.show();
	}

	protected void hideContentLoading() {
		mContentLoadingDelegate.hide();
	}

	protected void unsetContentLoading() {
		mContentLoadingDelegate.unset();
	}

    /* * * * * * * * * * * * * * * * * ContentLoadingDelegate End * * * * * * * * * * * * * * * * */

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		mLifecycleLogDelegate.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mLifecycleLogDelegate.onConfigurationChanged(newConfig);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mLifecycleLogDelegate.onAttach(context);
		if (getParentFragment() instanceof OnChildFinishListener) {
			mChildFinishListener = (OnChildFinishListener) getParentFragment();
		} else if (getActivity() instanceof OnChildFinishListener) {
			mChildFinishListener = (OnChildFinishListener) getActivity();
		}
	}

	@Override
	public void onAttachFragment(Fragment childFragment) {
		super.onAttachFragment(childFragment);
		mLifecycleLogDelegate.onAttachFragment(childFragment);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLifecycleLogDelegate.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mLifecycleLogDelegate.onCreateView(savedInstanceState);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mLifecycleLogDelegate.onViewCreated(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mLifecycleLogDelegate.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
		mLifecycleLogDelegate.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		mLifecycleLogDelegate.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mLifecycleLogDelegate.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
		super.onPause();
		mLifecycleLogDelegate.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
		mLifecycleLogDelegate.onStop();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mLifecycleLogDelegate.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mLifecycleLogDelegate.onDestroy();
	}

	@Override
	public void onDetach() {
		mChildFinishListener = null;
		super.onDetach();
		mLifecycleLogDelegate.onDetach();
	}

	@Override
	public void startFragment(Fragment fragment, String tag) {
		mStartFragmentDelegate.startFragment(fragment, tag);
	}

	@Override
	public void startFragment(Fragment fragment, String tag, boolean toBackStack) {
		mStartFragmentDelegate.startFragment(fragment, tag, toBackStack);
	}
}
