package com.jinghan.app.mvp.view.fragment;

import com.jinghan.core.databinding.FgRecommendBinding;
import com.jinghan.core.mvp.view.fragment.BaseFragment;
import com.jinghan.core.R;
import com.jinghan.core.dependencies.dragger2.scope.ActivityScoped;
import javax.inject.Inject;

/**
 * @author liuzeren
 * @time 2017/11/10    下午5:29
 * @mail lzr319@163.com
 */
@ActivityScoped
public class RecommendFragment extends BaseFragment<FgRecommendBinding>{

    @Inject
    public RecommendFragment(){}

    @Override
    protected int layoutId() {
        return R.layout.fg_recommend;
    }

    @Override public void initData() {
    }

    @Override public void initPresenter() {
    }
}