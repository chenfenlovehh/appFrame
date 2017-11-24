package com.jinghan.core.crash;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;

/**
 * Responsible for tracking the last Activity other than any CrashReport dialog that was created.
 *
 * @since 4.8.0
 */
public final class LastActivityManager {

    @NonNull
    private WeakReference<Activity> lastActivityCreated = new WeakReference<Activity>(null);

    public LastActivityManager(@NonNull Application application) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            // ActivityLifecycleCallback only available for API14+
            application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
                    Logger.d("onActivityCreated " + activity.getClass());

                    /*if (!(activity instanceof BaseCrashReportDialog)) {
                        // Ignore CrashReportDialog because we want the last
                        // application Activity that was started so that we can explicitly kill it off.
                        lastActivityCreated = new WeakReference<Activity>(activity);
                    }*/

                    lastActivityCreated = new WeakReference<Activity>(activity);
                }

                @Override
                public void onActivityStarted(@NonNull Activity activity) {
                    Logger.d("onActivityStarted " + activity.getClass());
                }

                @Override
                public void onActivityResumed(@NonNull Activity activity) {
                    Logger.d("onActivityResumed " + activity.getClass());
                }

                @Override
                public void onActivityPaused(@NonNull Activity activity) {
                    Logger.d("onActivityPaused " + activity.getClass());
                }

                @Override
                public void onActivityStopped(@NonNull Activity activity) {
                    Logger.d("onActivityStopped " + activity.getClass());
                    synchronized (this){
                        notify();
                    }
                }

                @Override
                public void onActivitySaveInstanceState(@NonNull Activity activity, Bundle outState) {
                    Logger.d("onActivitySaveInstanceState " + activity.getClass());
                }

                @Override
                public void onActivityDestroyed(@NonNull Activity activity) {
                    Logger.d("onActivityDestroyed " + activity.getClass());
                }
            });
        }
    }

    @Nullable
    public Activity getLastActivity() {
        return lastActivityCreated.get();
    }

    public void clearLastActivity() {
        lastActivityCreated.clear();
    }

    public void waitForActivityStop(int timeOutInMillis){
        synchronized (this) {
            try {
                wait(timeOutInMillis);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
