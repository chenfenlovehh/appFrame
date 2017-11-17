package com.jinghan.app.mvp.view.impl.module;

import com.jinghan.app.mvp.presenter.ISplashActivityPresenter;
import com.jinghan.app.mvp.presenter.ISplashFragmentPresenter;
import com.jinghan.app.mvp.presenter.impl.SplashActivityPresenter;
import com.jinghan.app.mvp.presenter.impl.SplashFragmentPresenter;
import com.jinghan.app.mvp.view.fragment.GuideFragment;
import com.jinghan.app.mvp.view.fragment.SplashFragment;
import com.jinghan.core.dependencies.dragger2.scope.ActivityScoped;
import com.jinghan.core.dependencies.dragger2.scope.FragmentScoped;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author liuzeren
 * @time 2017/11/6    下午3:19
 * @mail lzr319@163.com
 */
@Module
public abstract class SplashActivityModule{

    @FragmentScoped
    @ContributesAndroidInjector
    public abstract SplashFragment splashFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    public abstract GuideFragment guideFragment();

    @ActivityScoped
    @Binds
    public abstract ISplashActivityPresenter splashActivityPresenter(SplashActivityPresenter presenter);

    @ActivityScoped
    @Binds
    public abstract ISplashFragmentPresenter splashFragmentPresenter(SplashFragmentPresenter presenter);

}