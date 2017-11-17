package com.jinghan.core.mvp.preseneter;

import com.jinghan.core.dependencies.http.OkHttp;
import com.jinghan.core.mvp.view.impl.view.BaseView;
import io.reactivex.subjects.BehaviorSubject;
import javax.inject.Inject;

/**
 * @author liuzeren
 * @time 2017/11/9    上午11:27
 * @mail lzr319@163.com
 */
public abstract class BaseLifecyclePresenter<T extends BaseView,V> implements BasePresenter<T> {

    protected T mView = null;

    @Inject
    protected OkHttp mOkHttp;

    @Override public void takeView(T view) {
        mView = view;
    }

    @Override public void dropView() {
        mView = null;
    }

    protected BehaviorSubject<V> lifecycleSubject;

    public void lifecycle(BehaviorSubject<V> lifecycleSubject){
        this.lifecycleSubject = lifecycleSubject;
    }

}