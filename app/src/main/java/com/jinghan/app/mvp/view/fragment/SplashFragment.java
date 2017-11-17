package com.jinghan.app.mvp.view.fragment;

import android.content.Intent;
import com.jinghan.app.mvp.model.bean.SplashInfo;
import com.jinghan.app.mvp.presenter.ISplashFragmentPresenter;
import com.jinghan.app.mvp.view.activity.HomeActivity;
import com.jinghan.app.mvp.view.impl.view.ISplashFragmentView;
import com.jinghan.core.R;
import com.jinghan.core.databinding.FgSplashBinding;
import com.jinghan.core.dependencies.dragger2.scope.ActivityScoped;
import com.jinghan.core.mvp.view.fragment.BaseFragment;
import javax.inject.Inject;

/**
 * @author liuzeren
 * @time 2017/11/7    下午6:02
 * @mail lzr319@163.com
 */
@ActivityScoped
public class SplashFragment extends BaseFragment<FgSplashBinding> implements ISplashFragmentView {

    @Inject
    protected ISplashFragmentPresenter presenter;

    @Inject
    public SplashFragment(){}


    @Override
    protected int layoutId() {
        return R.layout.fg_splash;
    }

    @Override public void initData() {
        presenter.reqSplashInfo();

    }

    @Override public void initPresenter() {
        presenter.takeView(this);
        presenter.lifecycle(lifecycleSubject);
    }

    @Override public void updateSplash(SplashInfo splashInfo) {
        presenter.interval(mViewBinding.viewContainer,mViewBinding.viewProgress,mViewBinding.pbCircle,mViewBinding.tvProgress,splashInfo);
    }

    @Override public void toMain() {
        mActivity.finish();
        Intent intent = new Intent(getContext(),HomeActivity.class);
        startActivity(intent);
    }
}