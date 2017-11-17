package com.jinghan.app.mvp.view.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jinghan.app.mvp.presenter.ISplashActivityPresenter;
import com.jinghan.app.mvp.view.fragment.GuideFragment;
import com.jinghan.app.mvp.view.fragment.SplashFragment;
import com.jinghan.core.R;
import com.jinghan.core.databinding.AtySplashBinding;
import com.jinghan.core.dependencies.aspectj.annotation.Permission;
import com.jinghan.core.helper.AndroidUtils;
import com.jinghan.core.helper.BarHelper;
import com.jinghan.core.mvp.view.activity.BaseActivity;
import javax.inject.Inject;

import dagger.Lazy;

/**
 * @author liuzeren
 * @time 2017/11/6    下午2:07
 * @mail lzr319@163.com
 */
public class SplashActivity extends BaseActivity<AtySplashBinding>{

    @Inject Lazy<SplashFragment> splashFragment;
    @Inject Lazy<GuideFragment> guideFragment;

    @Inject ISplashActivityPresenter presenter;

    @Override public void onCreate(Bundle savedInstanceState) {
        BarHelper.fullScreen(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int layoutId() {
        return R.layout.aty_splash;
    }

    @Permission(value = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},isCallback = false)
    @Override
    protected void initViewsAndListener() {

    }

    @Override public void initData() {
        applyFragment(presenter.isFirst());
    }

    @Override public void initPresenter() {
        presenter.takeView(this);
    }

    @Override public void initToolbar() {
    }

    private void applyFragment(Boolean isFirstRun){
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_container);

        if(null == fragment) {
            fragment = isFirstRun ? guideFragment.get() : splashFragment.get();
        }

        AndroidUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fl_container);
    }

    @Override public void onDestroy() {
        presenter.dropView();
        super.onDestroy();
    }
}