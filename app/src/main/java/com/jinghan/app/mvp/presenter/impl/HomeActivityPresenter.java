package com.jinghan.app.mvp.presenter.impl;

import com.jinghan.app.mvp.presenter.IHomeActivityPresenter;
import com.jinghan.core.R;
import javax.inject.Inject;

/**
 * @author liuzeren
 * @time 2017/11/10    下午2:15
 * @mail lzr319@163.com
 */
public class HomeActivityPresenter extends IHomeActivityPresenter{

    @Inject
    public HomeActivityPresenter(){}

    private long mPressedTime = 0;

    @Override public void onBackPressed() {
        long mCurrentTime = System.currentTimeMillis();
        if (mCurrentTime - mPressedTime > 2000) {
            mView.toast(R.string.exit);
            mPressedTime = mCurrentTime;
        } else {
            mView.close();
        }
    }
}