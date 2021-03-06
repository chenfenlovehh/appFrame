package com.jinghan.app.mvp.presenter;

import com.jinghan.core.mvp.preseneter.BaseLifecyclePresenter;
import com.jinghan.core.mvp.view.impl.view.BaseView;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * @author liuzeren
 * @time 2017/11/10    下午2:13
 * @mail lzr319@163.com
 */
public abstract class IHomeActivityPresenter extends BaseLifecyclePresenter<BaseView,ActivityEvent>{

    public abstract void onBackPressed();

}