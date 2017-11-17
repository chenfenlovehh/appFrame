package com.jinghan.app.mvp.model.bean;

/**
 * 升级信息体
 * @author liuzeren
 * @time 2017/11/10    下午3:29
 * @mail lzr319@163.com
 */
public class UpdateInfo {

    /**
     * 强制升级
     */
    public static final int FORCE_UPDATE = 3;

    /**
     * 建议升级
     */
    public static final int SUGGEST_UPDATE = 2;

    /**
     * 无需升级
     */
    public static final int NO_UPDATE = 1;

    private int version_code = SUGGEST_UPDATE;
    private String version_name;
    private String apkUrl;
    private int support_os_version;
    private String apk_size;
    private String discription;
    private int updateType = NO_UPDATE;//1无需升级 2建议升级 3强制升级

    public static int getForceUpdate() {
        return FORCE_UPDATE;
    }

    public static int getSuggestUpdate() {
        return SUGGEST_UPDATE;
    }

    public static int getNoUpdate() {
        return NO_UPDATE;
    }

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public int getSupport_os_version() {
        return support_os_version;
    }

    public void setSupport_os_version(int support_os_version) {
        this.support_os_version = support_os_version;
    }

    public String getApk_size() {
        return apk_size;
    }

    public void setApk_size(String apk_size) {
        this.apk_size = apk_size;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public int getUpdateType() {
        return updateType;
    }

    public void setUpdateType(int updateType) {
        this.updateType = updateType;
    }
}
