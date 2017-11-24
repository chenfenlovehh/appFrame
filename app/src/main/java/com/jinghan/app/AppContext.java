package com.jinghan.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.jinghan.app.global.Constant;
import com.jinghan.core.BuildConfig;
import com.jinghan.core.crash.LastActivityManager;
import com.jinghan.core.dependencies.dragger2.component.DaggerAppComponent;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.orhanobut.logger.XDiskLogStrategy;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.Stack;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * @author liuzeren
 * @time 2017/11/16    下午3:17
 * @mail lzr319@163.com
 */
public class AppContext extends DaggerApplication {

    private static AppContext instance;

    /**
     * 最上面的activity管理器
     * */
    private LastActivityManager mLastActivityManager;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AndroidInjector<DaggerApplication> appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        mLastActivityManager = new LastActivityManager(this);

        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }

        initStetho();
        initLogger(BuildConfig.DEBUG);
    }

    /**
     * 初始化stetho
     */
    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }

    /**
     * 突破65535限制
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 初始化日志信息
     *
     * @param allowLog true记录日志
     */
    private void initLogger(boolean allowLog) {
        Logger.clearLogAdapters();

        if (!allowLog) {
            Logger.addLogAdapter(new AndroidLogAdapter() {
                @Override
                public boolean isLoggable(int priority, String tag) {
                    return false;
                }
            });
        } else {
            if (Constant.LogInfo.IS_WRITE_IN_LOCAL) {
                XDiskLogStrategy mLogStrategy = new XDiskLogStrategy(getMainLooper(), Constant.LogInfo.LOG_PATH, Constant.LogInfo.LOG_FILE_SIZE);
                Logger.addLogAdapter(new DiskLogAdapter(CsvFormatStrategy.newBuilder().logStrategy(mLogStrategy).tag(Constant.LogInfo.LOG_TAG).build()) {

                    @Override
                    public boolean isLoggable(int priority, String tag) {
                        if (priority >= Constant.LogInfo.WRITE_LOCAL_LOG_LEVEL) {
                            return true;
                        }

                        return false;
                    }
                });
            }

            //添加标准输出日志
            Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy.newBuilder().tag(Constant.LogInfo.LOG_TAG).build()));
        }
    }

    public static AppContext getInstance() {
        return instance;
    }

    public LastActivityManager getLastActivityManager() {
        return mLastActivityManager;
    }
}