package com.jinghan.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.jinghan.app.global.Constant;
import com.jinghan.core.BuildConfig;
import com.jinghan.core.dependencies.dragger2.component.DaggerAppComponent;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.orhanobut.logger.XDiskLogStrategy;
import com.squareup.leakcanary.LeakCanary;

import java.util.Stack;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * @author liuzeren
 * @time 2017/11/16    下午3:17
 * @mail lzr319@163.com
 */
public class AppContext extends DaggerApplication {

        public static AppContext instance;

        /**
         * 记录应用activity生命周期信息
         * */
        private Stack<Activity> store;


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
        LeakCanary.install(this);
        initStetho();
        initLogger(BuildConfig.DEBUG);
        registerLifecycle();
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

    /**突破65535限制*/
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 注册activity生命周期查看信息
     * */
    private void registerLifecycle() {
        store = new Stack();
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                store.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                store.remove(activity);
            }
        });
    }

    /**当前activity*/
    public Activity currentActivity(){
        return store.lastElement();
    }

    /**
     * 初始化日志信息
     * @param allowLog true记录日志
    * */
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

}