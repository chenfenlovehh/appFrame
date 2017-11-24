package com.jinghan.app.global.setting;

import android.support.annotation.StringRes;

import com.jinghan.app.AppContext;
import com.jinghan.core.helper.SharedPrefsUtils;

/**
 * @author liuzeren
 * @time 2017/11/24    下午2:55
 * @mail lzr319@163.com
 */
public abstract class SettingsEntry<T> implements SharedPrefsUtils.Entry<T> {

    private int mKeyResId;
    private int mDefaultValueResId;

    public SettingsEntry(@StringRes int keyResId, int defaultValueResId) {
        mKeyResId = keyResId;
        mDefaultValueResId = defaultValueResId;
    }

    @Override
    public String getKey() {
        return AppContext.getInstance().getString(mKeyResId);
    }

    protected int getDefaultValueResId() {
        return mDefaultValueResId;
    }

    public abstract T getValue();

    public abstract void putValue(T value);

    public void remove() {
        SharedPrefsUtils.remove(this);
    }
}
