package com.jinghan.core.helper;

import android.Manifest;
import android.content.Context;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.text.TextUtils;

import java.util.UUID;


/**
 * DeviceId 工具类, 用于获取设备 id。
 * @author Jierain
 * @since 2015-12-29
 */
public final class DeviceId {

    /** Device Id在系统设置中存储的Key */
    private static final String KEY_DEVICE_ID = "cn.emagsoftware.gamehall";

    /** 无读写权限时拼接Device Id所用前缀 */
    private static final String FAILSAFE_PREFIX = "cn.emagsoftware";

    /**
     * private constructor.
     */
    private DeviceId() {
    }

    /**
     * 获取设备id。<br>
     * <p>由于有些设备没有电话功能,所以不能依赖于 imei。
     * {@link Secure Secure.ANDROID_ID}
     * 在 froyo以前版本也不可靠. 我们还需要考虑模拟器。<br>
     *
     * @param context
     *            Context
     * @return device id.
     */
    public static String getDeviceID(Context context) {

        String deviceId = ""; // 百度app android平台设备唯一标示.

        try {
            boolean hasSettingPermission = PermissionUtils.checkPermissions(context, Manifest.permission.WRITE_SETTINGS);

            if (hasSettingPermission) {
                // 获取系统设置里的key
                deviceId = Settings.System.getString(context.getContentResolver(), KEY_DEVICE_ID);
            }

            // 如果为空，需要重新生成一个，然后写入设置。
            if (TextUtils.isEmpty(deviceId)) {

                deviceId = createDeviceId(context);

                if (hasSettingPermission) {
                    // 写入DeviceId
                    Settings.System.putString(context.getContentResolver(), KEY_DEVICE_ID, deviceId);
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        if (TextUtils.isEmpty(deviceId)) {
            deviceId = createDeviceId(context);
        }
        return deviceId;
    }

    public static String createDeviceId(Context context) {
//        return MD5Util.MD5("81020848" + getAndroidId(context));
        return getAndroidId(context);
    }

    /**
     * 获取设备 android id。{@link Secure#ANDROID_ID}
     *
     * @param context
     *            application context
     * @return 如果没有返回 “”空字符串
     */
    public static String getAndroidId(Context context) {

        String androidId = null;
        try {
            // read android id
            androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);

            if (TextUtils.isEmpty(androidId)) {
                androidId = createAndroidId();
                Secure.putString(context.getContentResolver(), Secure.ANDROID_ID, androidId);
            }
        } catch (Throwable t) {
        }

        if (TextUtils.isEmpty(androidId)) {
            androidId = createAndroidId();
        }

        return androidId;
    }

    private static String createAndroidId() {
        return FAILSAFE_PREFIX + UUID.randomUUID().toString();
    }
}