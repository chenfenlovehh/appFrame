package com.jinghan.core.dependencies.dragger2.module;

import com.jinghan.app.mvp.view.activity.HomeActivity;
import com.jinghan.app.mvp.view.activity.SplashActivity;
import com.jinghan.app.mvp.view.impl.module.HomeActivityModule;
import com.jinghan.app.mvp.view.impl.module.SplashActivityModule;
import com.jinghan.core.dependencies.dragger2.scope.ActivityScoped;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author liuzeren
 * @time 2017/11/2    下午3:14
 * @mail lzr319@163.com
 */
@Module
public abstract class ActivityBindingModule{

    @ActivityScoped
    @ContributesAndroidInjector(modules = {SplashActivityModule.class})
    public abstract SplashActivity splashActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {HomeActivityModule.class})
    public abstract HomeActivity homeActivity();

}