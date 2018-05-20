package com.yourpackagename.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by Dylan on 2017/11/23.
 * 调试用，打印各个UI的生命周期。
 */
public class LifecycleLogDelegate {

    private boolean lifecycleLog;
    private String logTag;

    public LifecycleLogDelegate(@NonNull String logTag) {
        this(false, logTag);
    }

    public LifecycleLogDelegate(boolean lifecycleLog, @NonNull String logTag) {
        setup(lifecycleLog, logTag);
    }

    public void setup(boolean lifecycleLog, @NonNull String logTag) {
        this.lifecycleLog = lifecycleLog;
        this.logTag = logTag;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (lifecycleLog) Log.d(logTag, "setUserVisibleHint: " + isVisibleToUser);
    }

    public void onAttach(Context context) {
        if (lifecycleLog) Log.d(logTag, "onAttach");
    }

    public void onAttachFragment(Fragment childFragment) {
        if (lifecycleLog) Log.d(logTag, "onAttachFragment: " + childFragment);
    }

    public void onCreate(Bundle savedInstanceState) {
        if (lifecycleLog) Log.d(logTag, "onCreate: " + savedInstanceState);
    }

    public void onCreateView(Bundle savedInstanceState) {
        if (lifecycleLog) Log.d(logTag, "onCreateView: " + savedInstanceState);
    }

    public void onViewCreated(Bundle savedInstanceState) {
        if (lifecycleLog) Log.d(logTag, "onViewCreated: " + savedInstanceState);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        if (lifecycleLog) Log.d(logTag, "onActivityCreated: " + savedInstanceState);
    }

    public void onRestart() {
        if (lifecycleLog) Log.d(logTag, "onRestart");
    }

    public void onStart() {
        if (lifecycleLog) Log.d(logTag, "onStart");
    }

    public void onResume() {
        if (lifecycleLog) Log.d(logTag, "onResume");
    }

    public void onSaveInstanceState(Bundle outState) {
        if (lifecycleLog) Log.d(logTag, "onSaveInstanceState: " + outState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (lifecycleLog) Log.d(logTag, "onRestoreInstanceState: " + savedInstanceState);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        if (lifecycleLog) Log.d(logTag, "onConfigurationChanged: " + newConfig);
    }

    public void onNewIntent(Intent intent) {
        if (lifecycleLog) Log.d(logTag, "onNewIntent: " + intent);
    }

    public void onPause() {
        if (lifecycleLog) Log.d(logTag, "onPause");
    }

    public void onStop() {
        if (lifecycleLog) Log.d(logTag, "onStop");
    }

    public void onDestroyView() {
        if (lifecycleLog) Log.d(logTag, "onDestroyView");
    }

    public void onDestroy() {
        if (lifecycleLog) Log.d(logTag, "onDestroy");
    }

    public void onDetach() {
        if (lifecycleLog) Log.d(logTag, "onDetach");
    }
}
