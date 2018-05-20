package com.yourpackagename.ui;

import android.support.annotation.IdRes;
import android.view.View;

/**
 * Created by Dylan on 2017/11/23.
 * <p>
 * Copy from {@link android.support.v4.widget.ContentLoadingProgressBar}
 */
public class ContentLoadingDelegate
		implements View.OnAttachStateChangeListener {

	public static final int MIN_SHOW_TIME = 500; // ms
	public static final int MIN_DELAY = 500; // ms

	long mMinDelay = MIN_DELAY;

	long mMinShowTime = MIN_SHOW_TIME;

	OnContentLoadingListener mListener;

	long mStartTime = -1;

	boolean mPostedHide = false;

	boolean mPostedShow = false;

	boolean mDismissed = false;

	private final Runnable mDelayedHide = new Runnable() {

		@Override
		public void run() {
			mPostedHide = false;
			mStartTime = -1;
			setVisible(false);
		}
	};

	private final Runnable mDelayedShow = new Runnable() {

		@Override
		public void run() {
			mPostedShow = false;
			if (!mDismissed) {
				mStartTime = System.currentTimeMillis();
				setVisible(true);
			}
		}
	};

	private View mContentLoading;

	public void setup(@IdRes int id, View view) {
		mContentLoading = view.findViewById(id);
		throwIfNull();
		mContentLoading.addOnAttachStateChangeListener(this);
	}

	public void setConfig(long minDelay, long minShowTime, OnContentLoadingListener listener) {
		mMinDelay = minDelay;
		mMinShowTime = minShowTime;
		mListener = listener;
	}

	public void unset() {
		throwIfNull();
		mContentLoading.removeOnAttachStateChangeListener(this);
		mContentLoading = null;
	}

	/**
	 * Hide the progress view if it is visible. The progress view will not be
	 * hidden until it has been shown for at least a minimum show time. If the
	 * progress view was not yet visible, cancels showing the progress view.
	 */
	public void hide() {
		throwIfNull();

		mDismissed = true;
		removeCallbacks(mDelayedShow);
		long diff = System.currentTimeMillis() - mStartTime;
		if (diff >= mMinShowTime || mStartTime == -1) {
			// The progress spinner has been shown long enough
			// OR was not shown yet. If it wasn't shown yet,
			// it will just never be shown.
			setVisible(false);
		} else {
			// The progress spinner is shown, but not long enough,
			// so put a delayed message in to hide it when its been
			// shown long enough.
			if (!mPostedHide) {
				postDelayed(mDelayedHide, mMinShowTime - diff);
				mPostedHide = true;
			}
		}
	}

	/**
	 * Show the progress view after waiting for a minimum delay. If
	 * during that time, hide() is called, the view is never made visible.
	 */
	public void show() {
		throwIfNull();

		// Reset the start time.
		mStartTime = -1;
		mDismissed = false;
		removeCallbacks(mDelayedHide);
		if (!mPostedShow) {
			postDelayed(mDelayedShow, mMinDelay);
			mPostedShow = true;
		}
	}

	protected void setVisible(boolean visible) {
		if (visible) {
			mContentLoading.setVisibility(View.VISIBLE);
		} else {
			mContentLoading.setVisibility(View.GONE);
		}

		if (mListener != null) {
			mListener.onContentLoadingChanged(this, visible);
		}
	}

	private void postDelayed(Runnable action, long delayMillis) {
		mContentLoading.postDelayed(action, delayMillis);
	}

	private void removeCallbacks(Runnable action) {
		mContentLoading.removeCallbacks(action);
	}

	@Override
	public void onViewAttachedToWindow(View v) {
		removeCallbacks();
	}

	@Override
	public void onViewDetachedFromWindow(View v) {
		removeCallbacks();
	}

	private void removeCallbacks() {
		removeCallbacks(mDelayedHide);
		removeCallbacks(mDelayedShow);
	}

	private void throwIfNull() {
		if (mContentLoading == null) {
			throw new IllegalStateException("use before must call setup.");
		}
	}

	public interface OnContentLoadingListener {

		void onContentLoadingChanged(ContentLoadingDelegate delegate, boolean visible);
	}
}
