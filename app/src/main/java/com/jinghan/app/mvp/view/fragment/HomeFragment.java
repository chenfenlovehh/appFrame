package com.jinghan.app.mvp.view.fragment;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.widget.TabHost;
import com.jinghan.app.mvp.model.bean.CatalogInfo;
import com.jinghan.app.mvp.presenter.IHomeFragmentPresenter;
import com.jinghan.app.mvp.view.impl.view.IHomeFragmentView;
import com.jinghan.core.databinding.FgHomeBinding;
import com.jinghan.core.mvp.view.fragment.BaseFragment;
import javax.inject.Inject;
import com.jinghan.core.R;
import com.jinghan.core.databinding.FgHomeBottomBarBinding;
import com.jinghan.core.dependencies.dragger2.scope.ActivityScoped;
import io.reactivex.Flowable;

import java.util.ArrayList;

/**
 * 首页
 * @author liuzeren
 * @time 2017/11/10    上午11:33
 * @mail lzr319@163.com
 */
@ActivityScoped
public class HomeFragment extends BaseFragment<FgHomeBinding> implements IHomeFragmentView {

    @SuppressLint("ValidFragment")
    @Inject
    public HomeFragment() {
    }

    @Inject
    protected IHomeFragmentPresenter presenter;

    @Override
    public int layoutId() {
        return R.layout.fg_home;
    }

    @Override public void initViews() {
        super.initViews();

        mViewBinding.tabHost.setup(getContext(), getFragmentManager(), R.id.realtabcontent);
        mViewBinding.tabHost.setOnTabChangedListener(tabId -> toast(tabId));

        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
            mViewBinding.tabHost.getTabWidget().setShowDividers(0);
        }
    }

    @Override public void initData() {
        presenter.homeBottomBars();
    }

    @Override public void initPresenter() {
        presenter.takeView(this);
        presenter.lifecycle(lifecycleSubject);
    }



    @Override public void updateBottomBars( ArrayList<CatalogInfo> catalogInfos) {
        Flowable.fromIterable(catalogInfos).map(info -> {
            TabHost.TabSpec tabSpec = mViewBinding.tabHost.newTabSpec(info.getJumpType());

            FgHomeBottomBarBinding bottomBarBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fg_home_bottom_bar, null, false);
            bottomBarBinding.tvTitle.setText(info.getCatalogName());
            presenter.initImage(bottomBarBinding.ivImage, info.getLogo(), info.getSelectedLogo());
            tabSpec.setIndicator(bottomBarBinding.getRoot());

            Bundle bundle = new Bundle();
            bundle.putString("service", info.getService());
            bundle.putString("method", info.getMethod());
            bundle.putLong("id", info.getCatalogId());

            return new Pair<TabHost.TabSpec,Bundle>(tabSpec,bundle);
        }).subscribe(tabData -> mViewBinding.tabHost.addTab(tabData.first, RecommendFragment.class, tabData.second) );
    }
}