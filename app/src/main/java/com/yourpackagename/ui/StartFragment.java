package com.yourpackagename.ui;

import android.support.v4.app.Fragment;

/**
 * Created by Dylan on 2017/12/2.
 *
 * Fragment中打开新Fragment时需要实现该接口
 */
public interface StartFragment {

    void startFragment(Fragment fragment, String tag);

    void startFragment(Fragment fragment, String tag, boolean backToStack);
}
