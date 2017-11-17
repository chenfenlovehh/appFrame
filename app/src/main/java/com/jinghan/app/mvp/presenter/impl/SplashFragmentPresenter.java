package com.jinghan.app.mvp.presenter.impl;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jinghan.app.mvp.model.bean.SplashInfo;
import com.jinghan.app.mvp.model.request.BaseRequest;
import com.jinghan.app.mvp.model.request.BaseUserRequestData;
import com.jinghan.app.mvp.presenter.ISplashFragmentPresenter;
import com.jinghan.app.mvp.view.impl.api.SplashService;
import com.jinghan.core.mvp.widget.CircleProgressBar;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.Glide;
import com.jinghan.core.R;
import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;


/**
 * @author liuzeren
 * @time 2017/11/9    上午11:04
 * @mail lzr319@163.com
 */
public class SplashFragmentPresenter extends ISplashFragmentPresenter {

    private Context context;

    @Inject
    public SplashFragmentPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void reqSplashInfo() {
        BaseRequest<BaseUserRequestData> request = new BaseRequest("loadingPageProvider", "queryLoadingPageInfo", new BaseUserRequestData());

        mOkHttp.getClient().builder(SplashService.class).queryLoadingPageInfo(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleAndroid.bindFragment(lifecycleSubject))
                .map(splashResponse -> splashResponse.getResultData())
                .subscribe(new DefaultObserver<SplashInfo>() {

                    private SplashInfo splashInfo = null;

                    @Override
                    public void onNext(SplashInfo splashInfo) {
                        this.splashInfo = splashInfo;
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.updateSplash(splashInfo);
                    }

                    @Override
                    public void onComplete() {
                        mView.updateSplash(splashInfo);
                    }
                });
    }

    @Override
    public void interval(ViewGroup viewGroup, ViewGroup viewProgress, CircleProgressBar pbCircle, TextView tvProgress, SplashInfo splashInfo) {
        if (null == splashInfo) {
            mView.toMain();
            return;
        }

        pbCircle.setMax(splashInfo.getCountDown() * 1000);
        pbCircle.setProgress(splashInfo.getCountDown());
        tvProgress.setText(context.getString(R.string.skip_progress, splashInfo.getCountDown()));

        if (!TextUtils.isEmpty(splashInfo.getInitialImageUrl())) {
            Glide.with(context).load(splashInfo.getInitialImageUrl()).fitCenter().into(

                    new ViewTarget<ViewGroup, GlideDrawable>(viewGroup) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                viewGroup.setBackground(resource);
                            } else {
                                viewGroup.setBackgroundDrawable(resource);
                            }

                            DefaultObserver<Long> mDefaultObserver = new DefaultObserver<Long>() {

                                {
                                    viewProgress.setOnClickListener(v -> {
                                        cancel();
                                        mView.toMain();
                                    });
                                }

                                @Override
                                public void onNext(@io.reactivex.annotations.NonNull Long o) {
                                    tvProgress.setText(context.getString(R.string.skip_progress, (splashInfo.getCountDown() * 1000 - o) / 1000 + 1));
                                    pbCircle.setProgress(splashInfo.getCountDown() * 1000 - o.intValue());
                                }

                                @Override
                                public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                                    //理论上是不会发生这一错误的
                                    mView.toMain();
                                }

                                @Override
                                public void onComplete() {
                                    mView.toMain();
                                }
                            };
                            viewProgress.setVisibility(View.VISIBLE);

                            //图片加载成功，则启动计时
                            Observable.interval(0, 1, TimeUnit.MILLISECONDS).compose(RxLifecycleAndroid.bindFragment(lifecycleSubject))
                                    .takeWhile(aLong -> aLong <= splashInfo.getCountDown() * 1000).observeOn(AndroidSchedulers.mainThread()).subscribe(mDefaultObserver);
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);

                            mView.toMain();
                        }
                    });
        } else {
            mView.toMain();
        }
    }
}