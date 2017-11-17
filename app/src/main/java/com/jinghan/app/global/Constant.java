package com.jinghan.app.global;

import android.os.Environment;

import com.orhanobut.logger.Logger;

import java.io.File;

/**
 * @author liuzeren
 * @time 2017/11/16    下午3:06
 * @mail lzr319@163.com
 */
public class Constant {

    public static String APP_NAME = "kotlin";

    /**
     * app scheme
     * */
    public static final String APP_SCHEME = String.format("%s://",APP_NAME);

    /**
     * 应用输出根路径
     */
    public static String ROOT_PAHT = String.format("%s%s%s%s", Environment.getExternalStorageDirectory().getPath(), File.separator,APP_NAME, File.separator);

    /**
     * apk更新下载所在路径
     */
    public static String APK_DOWNLOAD_PATH = String.format("%s%sapk%s",ROOT_PAHT,File.separator,File.separator);

    /**
     * 热修复补丁路径
     */
    public static String ANDFIX_PATCH_PATH = String.format("%s%sandfix%s",ROOT_PAHT,File.separator,File.separator);

    /**
     * http请求相关信息
     */
    public static class HttpInfo {
        /**
         * 请求根域名
         * baseUlr 必须以 /（斜线） 结束
         */
        public static String BASE_URL = "http://game.migufun.com/gateway/";

        /**
         * HTTPS证书名称
         * 私有证书，公有证书是不需要下载到本地的
         * */
        public static String SSL_NAME_IN_ASSETS = "";

        /**
         * 是否缓存请求
         */
        public static boolean IS_CACHE = true;

        /**
         * 是否启用文件缓存请求
         */
        public static boolean IS_FILE_CACHE = true;

        /**
         * 是否启用内存缓存请求
         */
        public static boolean IS_MEMORY_CACHE = true;

        /**
         * 内存缓存时间
         * 单位（秒）
         * */
        public static int MEMORY_CACHE_TIME = 5 * 60;

        /**
         * 磁盘缓存时间
         * 单位（秒）
         * */
        public static int DISK_CACHE_TIME = 60 * 60 * 24 * 28;

        /**
         * 网络请求缓存路径
         */
        public static String CACHE_PATH = String.format("%s%snet%scache",ROOT_PAHT,File.separator,File.separator);

        /**
         * 网络请求缓存大小
         */
        public static long CACHE_SIZE = 10 * 1024 * 1024L;

        /**
         * 网络连接超时时长
         * 单位（秒）
         * */
        public static long CONNETC_TIMEOUT = 10L;

        /**
         * 读流超时时长
         * 单位（秒）
         * */
        public static long READ_STREAM_TIMEOUT = 15L;

        /**
         * 写流超时时长
         * 单位（秒）
         * */
        public static long WRITE_STREAM_TIMEOUT = 20L;
    }

    /**
     * 日志相关信息
     */
    public static class LogInfo {
        /**
         * 日志的tag标志
         */
        public static String LOG_TAG = APP_NAME;

        /**
         * 日志信息是否保存在本地
         */
        public static boolean IS_WRITE_IN_LOCAL = true;

        /**
         * 日志输出路径
         */
        public static String LOG_PATH = String.format("%s%s", ROOT_PAHT, "log");

        /**
         * 单个日志文件的大小
         */
        public static int LOG_FILE_SIZE = 1 * 1024 * 1024;

        /**
         * 写入文件的日志的level
         */
        public static int WRITE_LOCAL_LOG_LEVEL = Logger.ERROR;
    }

    /**
     * 图片配置相关信息
     */
    public static class GlideInfo {

        /**
         * 图片缓存大小
         */
        public static int CACHE_SIZE = 100 * 1024 * 1024;

        /**
         * 图片缓存路径
         */
        public static String CACHE_PATH = Constant.ROOT_PAHT + File.separator + "glide";
    }
}
