package com.jinghan.app.mvp.view.impl.view;

import com.jinghan.app.mvp.model.bean.SplashInfo;
import com.jinghan.core.mvp.view.impl.view.BaseView;

/**
 * @author liuzeren
 * @time 2017/11/9    上午11:03
 * @mail lzr319@163.com
 */
public interface ISplashFragmentView extends BaseView{

    /**
     * 更新欢迎页信息
     * */
    void updateSplash(SplashInfo splashInfo);

    /**
     * 跳转到主页
     * */
    void toMain();
}