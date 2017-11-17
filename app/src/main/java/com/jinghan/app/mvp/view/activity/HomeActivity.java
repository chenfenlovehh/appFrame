package com.jinghan.app.mvp.view.activity;

import android.support.v4.app.Fragment;

import com.airbnb.deeplinkdispatch.DeepLink;
import com.jinghan.app.mvp.presenter.IHomeActivityPresenter;
import com.jinghan.app.mvp.view.fragment.HomeFragment;
import com.jinghan.core.databinding.AtyHomeBinding;
import com.jinghan.core.mvp.view.activity.BaseActivity;
import com.jinghan.core.R;
import com.jinghan.core.helper.AndroidUtils;
import dagger.Lazy;
import javax.inject.Inject;

/**
 * @author liuzeren
 * @time 2017/11/9    上午10:52
 * @mail lzr319@163.com
 */
@DeepLink("kotlin://home")
public class HomeActivity extends BaseActivity<AtyHomeBinding>{

    @Inject
    protected Lazy<HomeFragment> homeFragment;

    @Inject
    protected IHomeActivityPresenter presenter;

    @Override
    protected int layoutId() {
        return R.layout.aty_home;
    }

    @Override public void initViewsAndListener() {
        Fragment rxFragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
        AndroidUtils.addFragmentToActivity(
                getSupportFragmentManager(), homeFragment.get(), R.id.fl_container);
    }

    @Override public void initData() {
    }

    @Override public void initPresenter() {
        presenter.takeView(this);
        presenter.lifecycle(lifecycleSubject);
    }

    @Override public void initToolbar() {
    }

    @Override public void onBackPressed() {
        presenter.onBackPressed();
    }
}