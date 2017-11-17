package com.jinghan.core.mvp.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jinghan.core.mvp.view.activity.BaseActivity;
import com.jinghan.core.mvp.view.impl.view.BaseView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
import dagger.android.support.DaggerFragment;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * @author liuzeren
 * @time 2017/11/7    下午6:18
 * @mail lzr319@163.com
 */
public abstract class BaseFragment<B extends ViewDataBinding> extends DaggerFragment implements LifecycleProvider<FragmentEvent>, BaseView{
    protected final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    protected B mViewBinding;

    protected BaseActivity mActivity;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewBinding = DataBindingUtil.inflate(inflater,layoutId(),container,false);
        return mViewBinding.getRoot();
    }

    protected abstract int layoutId();

    @CheckResult
    @Override public Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @CheckResult
    @Override public <T> LifecycleTransformer<T> bindUntilEvent(FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @CheckResult
    @Override public <T> LifecycleTransformer<T> bindToLifecycle()  {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
    }

    @CallSuper
    @Override public void onAttach(Context context) {
        super.onAttach(context);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);

        mActivity = (BaseActivity)activity;
    }

    @CallSuper
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @CallSuper
    @Override public void onViewCreated(View view, Bundle savedInstanceState) {

        initPresenter();
        initViews();
        initData();

        super.onViewCreated(view, savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
    }

    /**
     * 初始化Views
     */
    protected void initViews() {}

    protected abstract void initData();

    protected abstract void initPresenter();

    @CallSuper
    @Override public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @CallSuper
    @Override public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @CallSuper
    @Override public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @CallSuper
    @Override public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @CallSuper
    @Override public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
    }

    @CallSuper
    @Override public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
    }

    @CallSuper
    @Override public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }

    @Override public void toast(int txtId) {
        toast(getString(txtId));
    }

    @Override public void toast(String text) {
        if (TextUtils.isEmpty(text)) {return;}

        mActivity.toast(text);
    }

    @Override public void close() {
        mActivity.close();
    }

    @Override public void showLoading() {
    }

    @Override public void hideLoading() {
    }

    @Override public void error(View.OnClickListener listener) {
    }
}