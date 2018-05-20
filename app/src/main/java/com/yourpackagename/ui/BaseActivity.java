package com.yourpackagename.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * Created by Dylan on 2017/11/23.
 */

public abstract class BaseActivity
		extends AppCompatActivity
		implements StartFragment, OnChildFinishListener {

	static boolean dispatchOnBackPressed(FragmentManager fragmentManager) {
		// TODO 长期跟踪测试下段代码
		// 下段代码将onBackPressed事件向子Fragment传递，并且子Fragment可拦截该事件
		List<Fragment> fragments = fragmentManager.getFragments();
		if (fragments.size() > 0) {
			Fragment fragment = fragments.get(fragments.size() - 1);
			if (fragment instanceof OnBackPressedListener) {
				if (((OnBackPressedListener) fragment).onBackPressed()) {
					return true;
				}
			}
		}
		return false;
	}

	public interface OnBackPressedListener {

		boolean onBackPressed();
	}

	protected LifecycleLogDelegate mLifecycleLogDelegate;
	protected StartFragmentDelegate mStartFragmentDelegate;

	public BaseActivity() {
		mLifecycleLogDelegate = new LifecycleLogDelegate("BaseActivity");
	}

	protected void setupStartFragment(FragmentManager fm, @IdRes int id) {
		if (mStartFragmentDelegate == null) {
			mStartFragmentDelegate = new StartFragmentDelegate();
		}
		mStartFragmentDelegate.setup(fm, id);
	}

	protected void setupLifecycleLog(boolean enable, String tag) {
		mLifecycleLogDelegate.setup(enable, tag);
	}

	@Override
	public void onChildFinish(Fragment child) {
		getSupportFragmentManager().popBackStack();
	}

	@Override
	public void onBackPressed() {
		if (!dispatchOnBackPressed(getSupportFragmentManager())) {
			super.onBackPressed();
		}
	}

	@Override
	public void onAttachFragment(Fragment childFragment) {
		super.onAttachFragment(childFragment);
		mLifecycleLogDelegate.onAttachFragment(childFragment);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLifecycleLogDelegate.onCreate(savedInstanceState);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		mLifecycleLogDelegate.onRestart();
	}

	@Override
	protected void onStart() {
		super.onStart();
		mLifecycleLogDelegate.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mLifecycleLogDelegate.onResume();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mLifecycleLogDelegate.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mLifecycleLogDelegate.onSaveInstanceState(outState);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		mLifecycleLogDelegate.onNewIntent(intent);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mLifecycleLogDelegate.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mLifecycleLogDelegate.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mLifecycleLogDelegate.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mLifecycleLogDelegate.onDestroy();
	}

	@Override
	public void startFragment(Fragment fragment, String tag) {
		mStartFragmentDelegate.startFragment(fragment, tag);
	}

	@Override
	public void startFragment(Fragment fragment, String tag, boolean backToStack) {
		mStartFragmentDelegate.startFragment(fragment, tag, backToStack);
	}
}
