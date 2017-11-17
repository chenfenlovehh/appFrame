package com.jinghan.app.mvp.view.fragment;

import android.content.Intent;
import android.view.View;
import com.jinghan.app.mvp.view.activity.HomeActivity;
import com.jinghan.app.mvp.view.adapter.GuideAdapter;
import com.jinghan.core.R;
import com.jinghan.core.databinding.FgGuideBinding;
import com.jinghan.core.dependencies.dragger2.scope.ActivityScoped;
import com.jinghan.core.mvp.view.fragment.BaseFragment;
import javax.inject.Inject;

/**
 * 欢迎引导页
 * @author liuzeren
 * @time 2017/11/7    下午6:03
 * @mail lzr319@163.com
 */
@ActivityScoped
public class GuideFragment extends BaseFragment<FgGuideBinding> implements View.OnClickListener {

    private GuideAdapter adapter;

    @Inject
    public GuideFragment(){}

    @Override
    protected int layoutId() {
        return R.layout.fg_guide;
    }

    @Override
    public void initData() {
        adapter = new GuideAdapter(getContext(), new Integer[]{R.mipmap.wel1,R.mipmap.wel2,R.mipmap.wel3,R.mipmap.wel4});
        adapter.setViewPageItemClick(this);
        mViewBinding.viewPager.setAdapter(adapter);
    }

    @Override public void onClick(View v) {
        mActivity.finish();
        Intent intent = new Intent(getContext(), HomeActivity.class);
        startActivity(intent);
    }

    @Override public void initPresenter() {}
}