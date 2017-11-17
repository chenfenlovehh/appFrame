package com.jinghan.core.mvp.view.impl.view;

import android.view.View;

/**
 * @author liuzeren
 * @time 2017/11/6    下午2:16
 * @mail lzr319@163.com
 */
public interface BaseView {

    void showLoading();

    void hideLoading();

    void error(View.OnClickListener listener);

    void toast(String text);

    void toast(int txtId);

    void close();
}
