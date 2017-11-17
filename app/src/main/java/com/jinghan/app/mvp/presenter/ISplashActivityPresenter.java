package com.jinghan.app.mvp.presenter;

import com.jinghan.core.mvp.preseneter.BasePresenter;
import com.jinghan.core.mvp.view.impl.view.BaseView;

/**
 * @author liuzeren
 * @time 2017/11/8    下午2:17
 * @mail lzr319@163.com
 */
public interface ISplashActivityPresenter extends BasePresenter<BaseView>{
    boolean isFirst();
}