package com.jinghan.core.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class AndroidUtils {

    /**
     * 获取设备号（imei）
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getDeviceId(Context context) {
        String deviceId = "000000000000";
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
        } catch (Exception e) {
        }
        return deviceId;
    }

    /**
     * 获取手机IMSI号
     */
    @SuppressLint("MissingPermission")
    public static String getIMSI(Context context) {
        String imsi = "";
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imsi = tm.getSubscriberId();
        } catch (Exception e) {
        } finally {
            if (TextUtils.isEmpty(imsi)) {
                imsi = "";
            }
        }

        return imsi;
    }

    /**
     * 获取设备的mac地址
     *
     * @param context
     * @return 返回设备mac
     */
    public static String getMacAddress(Context context) {
        String macAddress = getMacAddressByWifiInfo(context);
        if (!"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }
        macAddress = getMacAddressByNetworkInterface();
        if (!"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }
        return "02:00:00:00:00:00";
    }

    private static String getMacAddressByWifiInfo(Context context) {
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wifi != null) {
                WifiInfo info = wifi.getConnectionInfo();
                if (info != null) {return info.getMacAddress();}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }
    @SuppressLint("HardwareIds")
    private static String getMacAddressByNetworkInterface() {
        try {
            List<NetworkInterface> nis = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ni : nis) {
                if (!ni.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = ni.getHardwareAddress();
                if (macBytes != null && macBytes.length > 0) {
                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02x:", b));
                    }
                    return res1.deleteCharAt(res1.length() - 1).toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    /**
     * @param context
     * @return 返回网络是否可用
     */
    public static boolean isNetworkAvailable(Context context) {
       /* if (context == null)
            return false;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info[] = manager.getAllNetworkInfo();
        for (int i = 0; i < info.length; i++) {
            NetworkInfo net = info[i];
            if (net.getTypeName().equalsIgnoreCase("WIFI") && net.isConnected()) {//忽略大小写
                return true;
            } else if (net.getTypeName().equalsIgnoreCase("mobile") && net.isConnected()) {
                return true;
            }
        }
        return false;*/
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * @param context
     * @param dpValue
     * @return 将dp转换成px
     */
    public static int dip2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static float dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }

    public static int sp2px(Context context, float spValue) {
        return (int) (context.getResources().getDisplayMetrics().scaledDensity * spValue + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * @return 设备版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取版本号名称
     *
     * @return 当前应用的版本号
     */
    public static String getVersionName(Context context) {
        return getVersionName(context, context.getPackageName());
    }

    /**
     * 获取版本号名称
     *
     * @param context
     * @param packageName
     * @return 应用的版本号
     */
    public static String getVersionName(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info;
        try {
            info = manager.getPackageInfo(packageName, 0);
            return info.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context) {
        return getVersionCode(context, context.getPackageName());
    }

    /**
     * 获取版本号
     *
     * @param context
     * @param packageName
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info;
        try {
            info = manager.getPackageInfo(packageName, 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 是否需要更新
     *
     * @param context
     * @param packageName
     * @param newVersionName
     * @return
     */
    public static boolean needUpdate(Context context, String packageName, String newVersionName) {
        try {
            String curVersionName = getVersionName(context, packageName);
            String[] newVersions = newVersionName.split("\\.");
            String[] curVersions = curVersionName.split("\\.");
            int nl = newVersionName.length(), cl = curVersionName.length();
            int len = nl < cl ? nl : cl;
            for (int i = 0; i < len; i++) {
                String n = newVersions[i];
                String c = curVersions[i];
                if (TextUtils.isDigitsOnly(n) && TextUtils.isDigitsOnly(c) &&
                        Integer.parseInt(n) > Integer.parseInt(c)) {
                    return true;
                }
            }
        } catch (Throwable t) {
        }
        return false;
    }

    /**
     * 是否需要更新
     *
     * @param context
     * @param packageName
     * @param newVersionCode
     * @return
     */
    public static boolean newNeedUpdate(Context context, String packageName, String newVersionCode) {
        try {
            int curVersionCode = getVersionCode(context, packageName);
            return Integer.parseInt(newVersionCode) > curVersionCode;
        } catch (Throwable t) {
        }
        return false;
    }

    /**
     * 获取metaData。
     *
     * @param act
     * @return
     */
    public static Bundle getApplicaitonMetaData(Application act) {
        try {
            ApplicationInfo appInfo = act.getPackageManager().getApplicationInfo(act.getPackageName(),
                    PackageManager.GET_META_DATA);
            return appInfo.metaData;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getMetaData(Context context, String key) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            return appInfo.metaData.get(key);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @return 判断sd卡是否挂载好
     */
    public static boolean isSDCardMounted() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 隐藏键盘
     *
     * @param context
     * @since 隐藏输入法
     */
    public static boolean hideInputMethod(Activity context) {
        if (context == null) {
            return false;
        }
        View currentFocus = context.getCurrentFocus();
        if (currentFocus != null) {
            InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            return manager.hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return false;
    }

    public static boolean hideInputMethod(Context context, IBinder currentFocus) {
        if (context == null) {
            return false;
        }

        if (currentFocus != null) {
            InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            return manager.hideSoftInputFromWindow(currentFocus, InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return false;
    }

    public static boolean isInputMethodVisible(Context context){
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return manager.isActive();
    }

    public static void showInputMethod(Context context,View view) {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInput(view, 0);
    }

    /**
     * 显示键盘
     *
     * @param context
     * @param view
     * @since 显示软键盘
     */
    public static void showInputMethod(Activity context, View view) {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInput(view, 0);
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 通过包名检测系统中是否安装某个应用程序
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean checkApkExist(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 获取文件大小
     *
     * @param f
     * @return
     * @throws Exception
     */
    public static long getFileSize(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            File subFile = flist[i];
            if (subFile.isDirectory()) {
                size = size + getFileSize(subFile);
            } else {
                size = size + subFile.length();
            }
        }
        return size;
    }


    public static boolean saveBitmapToFile(Bitmap bitmap, String path, Bitmap.CompressFormat format) {
        boolean ret = false;
        File file = new File(path);
        if (!bitmap.isRecycled()) {
            try {
                ret = bitmap.compress(format, 80, new FileOutputStream(file, false));
            } catch (Exception e) {
            }
            if (!ret) {
                file.delete();
            }
        }
        return ret;
    }

    public static void setTextViewLeftDrawable(Context context, TextView tv, int id) {
        Drawable img = context.getResources().getDrawable(id);
        // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
        tv.setCompoundDrawables(img, null, null, null); //设置左图标
    }


    public static void installApp(Context context, String filePath) {
//    	String appName = FileUtil.getFileNameFromUrl(downloadUrl).replace("/", "");
        File appFile = new File(filePath);
        if (appFile.exists()) {
            /*if (!appName.endsWith(".apk")) {
                appName = appName.substring(0, appName.lastIndexOf(".apk") + 4);
    			appFile.renameTo(new File(Globals.GAMEAPK_PATH + appName));
    		}*/
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//service中启动activity
            intent.setDataAndType(Uri.fromFile(appFile),
                    "application/vnd.android.package-archive");
            context.startActivity(intent);
        }
    }
    public static boolean isApkCanInstall(Context mContext, String filePath) {
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
            if (info != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    public static String getAppPackageName(Context context, String appName) {
        List<ApplicationInfo> mAppList = context.getPackageManager().getInstalledApplications(0);
        for (ApplicationInfo item : mAppList) {
            if ((item.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                if (item.loadLabel(context.getPackageManager()).toString()
                        .contains(appName)) {
                    return item.packageName;
                }
            } else {

            }
        }
        return null;
    }

    public static void openApp(Context context, String appPackageName) {
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(appPackageName);
        List<ResolveInfo> resolveInfoList = context.getPackageManager()
                .queryIntentActivities(resolveIntent, 0);
        if (resolveInfoList != null && resolveInfoList.size() > 0) {
            ResolveInfo resolveInfo = resolveInfoList.get(0);
            String activityPackageName = resolveInfo.activityInfo.packageName;
            String className = resolveInfo.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName componentName = new ComponentName(
                    activityPackageName, className);

            intent.setComponent(componentName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public static void openMiGuChessApp(Context context, String appPackageName, String mainActivity) {
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.setPackage(appPackageName);
        List<ResolveInfo> resolveInfoList = context.getPackageManager()
                .queryIntentActivities(resolveIntent, 0);
        if (resolveInfoList != null && resolveInfoList.size() > 0) {
            ResolveInfo resolveInfo = resolveInfoList.get(0);
            String activityPackageName = appPackageName;
            String className = mainActivity;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName componentName = new ComponentName(
                    activityPackageName, className);
            intent.setComponent(componentName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    /**
     * 获取apk包名
     *
     * @param context
     * @param apkPath
     * @return
     */
    public static String getPackageName(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            if (appInfo != null) {
                return appInfo.packageName;
            }
        }
        return null;
    }

    /**
     * 判断当前网络是否已经连接，并且是2G状态
     *
     * @param ctx
     * @return
     */
    public static boolean is2GMobileNetwork(Context ctx) {
        ConnectivityManager manager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info;
        try {
            info = manager.getActiveNetworkInfo();
            if (info != null && info.getType() == ConnectivityManager.TYPE_MOBILE) {
                int currentNetworkType = info.getSubtype();
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean isWifiNetwork(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info;
            try {
                info = mConnectivityManager.getActiveNetworkInfo();
                if (info != null && info.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        java.lang.reflect.Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }

        statusBarHeight = dip2px(context, 25);
        return statusBarHeight;
    }

    /**
     * 获取渠道号
     *
     * @param context
     * @return
     */
    public static long getChannel(Context context) {

        long iChannel = 1000100;

        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        ZipFile zipfile = null;
        final String start_flag = "META-INF/CPAChannel.txt";
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.equals(start_flag)) {
                    String channel = inputStreamToString(zipfile.getInputStream(entry));
//                    String channel = entryName.replaceAll(start_flag, "");

                    try {
                        iChannel =Long.parseLong(channel);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(40296534685L == iChannel && Mi_COUNT > 1){
            return 40296538890L ;
        }
        return iChannel;
    }

    /**
     * 截屏
     *
     * @param activity
     * @return
     */

    public static Bitmap captureScreen(Activity activity) {

        activity.getWindow().getDecorView().setDrawingCacheEnabled(true);

        Bitmap bmp = activity.getWindow().getDecorView().getDrawingCache();

        return bmp;

    }

    /**
     * 是否是第一次使用软件
     *
     * @return true:首次安装 false:升级了
     */
    public static int isFirstUse(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            int curVersion = info.versionCode;
            // SettingUtils.setEditor(context, "version", paramString2);
            // int lastVersion = SettingUtils.getSharedPreferences(context, "version", 0);
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            int lastVersion = sp.getInt("version", 0);
            if (curVersion > lastVersion && lastVersion == 0) {
                // 如果当前版本大于上次版本，该版本属于第一次启动
                // 将当前版本写入preference中，则下次启动的时候，据此判断，不再为首次启动
                return 1;// 首次安装
            } else {
                if (curVersion != lastVersion) {
                    Log.i("TTT", " 升级 curVersion  " + curVersion);
                    return 2;// 升级
                } else {
                    Log.i("TTT", " 不升级 curVersion  " + curVersion);
                    return 0;// 正常安装
                }

            }
        } catch (NameNotFoundException e) {
            Log.i("TTT", " isFirstUse e " + e.toString());
        }
        return 0;// 正常安装
    }

    /**
     * setAPPUsed:设置APP已经使用过了. <br/>
     *
     * @author wangheng
     */
    public static void setAPPUsed(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            int curVersion = info.versionCode;
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            sp.edit().putInt("version", curVersion).commit();
            // SettingUtils.setEditor(context, "version", curVersion);
        } catch (NameNotFoundException e) {
            Log.i("TTT", " setAPPUsed e " + e.toString());
        }
    }


    public static String inputStreamToString(InputStream is) {

        String s = "";
        String line = "";
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = rd.readLine()) != null) {
                s += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static int lengthByChar(String s) {
        if (s == null) {
            return 0;
        }
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }

    private static boolean isLetter(char c) {
        return c / ASCII_UPPER_LIMIT == 0;
    }

    private static final int ASCII_UPPER_LIMIT = 0x80;

    // 得到本机Mac地址
    public static String getLocalMac(Context context) {
        // 获取wifi管理器
        WifiManager wifiMng = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfor = wifiMng.getConnectionInfo();
        return wifiInfor.getMacAddress();
    }

    /**
     * 获取ip地址
     *
     * @return
     */
    public static String getHostIP() {
        String hostIp = null;
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;
                    }

                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return hostIp;
    }

    /**
     * 2G:1 3G:2 4G:3 WIFI:4其他:5
     */
    public static String getNetworkType(Context context) {
        String strNetworkType = "5";


        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        try {
            networkInfo = mConnectivityManager.getActiveNetworkInfo();
        } catch (Exception e) {
        }

        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "4";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();

                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "1";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "2";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = "3";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "2";
                        } else if (_strSubTypeName.equalsIgnoreCase("TD-LTE_CA")) {
                            strNetworkType = "3";
                        } else {
                            strNetworkType = "5";
                        }

                        break;
                }
            }
        }

        return strNetworkType;
    }

    public static void setSystemBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (null != activity) {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                activity.getWindow().setStatusBarColor(color);
            }

        }
    }


    public static void setSystemBarColorFullScreen(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (null != activity) {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                activity.getWindow().setStatusBarColor(color);
            }

        }
    }
    public static void setSystemBarVisable(View view,int visable){
        if (Build.VERSION.SDK_INT >= 23) {
            view.setVisibility(visable);
        }
    }


    public static void setMi_COUNT(int mi_count) {
        Mi_COUNT = mi_count;
    }

    public static int Mi_COUNT =0;

    public static long getPreChannel(Context context) {

        long iChannel = 1000100;
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        ZipFile zipfile = null;
        final String start_flag = "META-INF/CPAChannel.txt";
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.equals(start_flag)) {
                    String channel = inputStreamToString(zipfile.getInputStream(entry));
//                    String channel = entryName.replaceAll(start_flag, "");

                    try {
                        iChannel =Long.parseLong(channel);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return iChannel;
    }

    public static String headerSetCookie( String value) {

        if (isNull(value)) {
            return "";
        }
        StringBuilder sbCheck = new StringBuilder();
        for (int i = 0, length = value.length(); i < length; i++) {
            char c = value.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sbCheck.append( String.format ("\\u%04x", (int)c) );
            } else {
                sbCheck.append(c);
            }
        }
        return sbCheck.toString();

    }
    public static boolean isNull(Object object) {
        try {
            if (null == object) {
                return true;
            }

            if (object instanceof String) {
                if (object.equals("")) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;

    }

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        if(fragment.isAdded()) return;

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(fragment, tag);
        transaction.commit();
    }

    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}