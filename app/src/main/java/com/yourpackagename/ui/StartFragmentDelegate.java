package com.yourpackagename.ui;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Dylan on 2017/12/2.
 * Fragment中打开新Fragment时默认实现
 */
public class StartFragmentDelegate
		implements StartFragment {

	private FragmentManager mFM;
	private int mContainerId;

	public void setup(FragmentManager fm, @IdRes int containerId) {
		mFM = fm;
		mContainerId = containerId;
	}

	@Override
	public void startFragment(Fragment fragment, String tag) {
		startFragment(fragment, tag, true);
	}

	@Override
	public void startFragment(Fragment fragment, String tag, boolean toBackStack) {
		FragmentTransaction trans = mFM.beginTransaction();
		if (toBackStack) {
			trans.addToBackStack(tag + "_back");
		}
		trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
		     .add(mContainerId, fragment, tag).commit();
	}
}
