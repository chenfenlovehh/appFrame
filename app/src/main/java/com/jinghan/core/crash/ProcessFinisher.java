package com.jinghan.core.crash;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Takes care of cleaning up a process and killing it.
 *
 * @author F43nd1r
 * @since 4.9.2
 */

public final class ProcessFinisher {
    private final Context context;
    private final LastActivityManager lastActivityManager;

    public ProcessFinisher(@NonNull Context context, @NonNull LastActivityManager lastActivityManager) {
        this.context = context;
        this.lastActivityManager = lastActivityManager;
    }

    public void endApplication(@Nullable Thread uncaughtExceptionThread) {
        finishLastActivity(uncaughtExceptionThread);
        stopServices();
        killProcessAndExit();
    }

    public void finishLastActivity(@Nullable Thread uncaughtExceptionThread) {
        // Trying to solve https://github.com/ACRA/acra/issues/42#issuecomment-12134144
        // Determine the current/last Activity that was started and close
        // it. Activity#finish (and maybe it's parent too).
        final Activity lastActivity = lastActivityManager.getLastActivity();
        if (lastActivity != null) {
            Logger.d("Finishing the last Activity prior to killing the Process");
            lastActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lastActivity.finish();
                    Logger.d("Finished " + lastActivity.getClass());
                }
            });

            // A crashed activity won't continue its lifecycle. So we only wait if something else crashed
            if (uncaughtExceptionThread != lastActivity.getMainLooper().getThread()) {
                lastActivityManager.waitForActivityStop(100);
            }
            lastActivityManager.clearLastActivity();
        }
    }

    private void stopServices() {
            final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            final List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(Integer.MAX_VALUE);
            final int pid = Process.myPid();
            for (ActivityManager.RunningServiceInfo serviceInfo : runningServices) {
                if (serviceInfo.pid == pid) {
                    try {
                        final Intent intent = new Intent();
                        intent.setComponent(serviceInfo.service);
                        context.stopService(intent);
                    } catch (SecurityException e) {
                        Logger.d("Unable to stop Service " + serviceInfo.service.getClassName() + ". Permission denied");
                    }
                }
            }

    }

    private void killProcessAndExit() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }
}
