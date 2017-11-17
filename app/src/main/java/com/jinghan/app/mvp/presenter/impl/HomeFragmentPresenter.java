package com.jinghan.app.mvp.presenter.impl;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jinghan.app.mvp.model.bean.CatalogInfo;
import com.jinghan.app.mvp.model.request.BaseRequest;
import com.jinghan.app.mvp.model.request.BaseUserRequestData;
import com.jinghan.app.mvp.model.response.CatalogListResponse;
import com.jinghan.app.mvp.presenter.IHomeFragmentPresenter;
import com.jinghan.app.mvp.view.impl.api.HomeService;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * @author liuzeren
 * @time 2017/11/10    下午3:20
 * @mail lzr319@163.com
 */
public class HomeFragmentPresenter extends IHomeFragmentPresenter {

    @Inject
    public HomeFragmentPresenter() {
    }

    @Override
    public void initImage(ImageView imageView, String normalUrl, String selectedUrl) {
        StateListDrawable drawable = new StateListDrawable();
        imageView.setImageDrawable(drawable);

        Glide.with(imageView.getContext()).load(normalUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                BitmapDrawable bitmap = new BitmapDrawable(resource);

                drawable.addState(new int[]{-android.R.attr.state_pressed, -android.R.attr.state_selected}, bitmap);
                drawable.addState(new int[]{android.R.attr.state_pressed, -android.R.attr.state_selected}, bitmap);
            }
        });

        Glide.with(imageView.getContext()).load(selectedUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                BitmapDrawable bitmap = new BitmapDrawable(resource);

                drawable.addState(new int[]{-android.R.attr.state_pressed, android.R.attr.state_selected}, bitmap);
                drawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_selected}, bitmap);
            }
        });
    }

    @Override
    public void homeBottomBars() {
        mView.showLoading();

        BaseRequest<BaseUserRequestData> request = new BaseRequest("catalogInfoProvider", "queryBottombarInfo", new BaseUserRequestData());

        mOkHttp.getClient().builder(HomeService.class).queryBottombars(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).compose(RxLifecycleAndroid.bindFragment(lifecycleSubject))
                .concatMap(catalogInfo -> Observable.just(catalogInfo.getResultData().bottombarInfoList))
                .subscribe(new DefaultObserver<ArrayList<CatalogInfo>>() {

                    private ArrayList<CatalogInfo> catalogInfos = null;

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull ArrayList<CatalogInfo> catalogInfos) {
                        this.catalogInfos = catalogInfos;
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if (null == catalogInfos || catalogInfos.size() == 0) {
                            mView.error(v -> homeBottomBars());
                        } else {
                            mView.hideLoading();
                            mView.updateBottomBars(catalogInfos);
                        }
                    }
                });
    }
}