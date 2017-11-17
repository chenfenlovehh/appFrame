package com.jinghan.core.mvp.view.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.jinghan.core.helper.PermissionUtils;
import com.jinghan.core.mvp.view.impl.view.BaseView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * @author liuzeren
 * @time 2017/11/6    下午2:08
 * @mail lzr319@163.com
 */
public abstract class BaseActivity<B extends ViewDataBinding> extends DaggerAppCompatActivity implements LifecycleProvider<ActivityEvent>,BaseView {

    static {
        /**vector支持,向5.0之前提供支持 */
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    protected final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();
    protected B mViewBinding;

    @CallSuper
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lifecycleSubject.onNext(ActivityEvent.CREATE);
        initContentView();
        initPresenter();
        initViewsAndListener();
        initToolbar();
        initData();
    }

    @CheckResult
    @Override
    public Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @CheckResult
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @CheckResult
    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }

    private void initContentView() {
        mViewBinding = DataBindingUtil.setContentView(this, layoutId());
    }

    protected abstract int layoutId();

    protected abstract void initViewsAndListener();

    protected abstract void initData();

    protected abstract void initPresenter();

    protected abstract void initToolbar();

    @CallSuper
    @Override
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @CallSuper
    @Override
    public void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @CallSuper
    @Override
    public void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @CallSuper
    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        PermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void toast(int txtId) {
        toast(getString(txtId));
    }

    @Override
    public void toast(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }

        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void close() {
        finish();
    }

    /**
     * 沉浸式
     */
    protected void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags;
        }
    }

    /**
     * 全屏
     */
    protected void fullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void error(View.OnClickListener listener) {
    }

    /**
     * * 显示或隐藏StatusBar
     *
     * @param enable false 显示，true 隐藏
     */
    protected void hideStatusBar(Boolean enable) {
        WindowManager.LayoutParams p = getWindow().getAttributes();
        if (enable)
        //|=：或等于，取其一
        {
            p.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        } else
        //&=：与等于，取其二同时满足，     ~ ： 取反
        {
            p.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        getWindow().setAttributes(p);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}