package com.zed.common.constant;


/**
 * Desc  :
 */
public class AppConstant {

    /**
     * APP 相关
     */

    // global tag
    public final static String TAG = AppConstant.class.getName();

    // 默认渠道名
    public final static String DEFAULT_CHANNEL = "dev";

    // app secret
    public final static String SECRET = "";

    // 百川key
    public final static String APPKEY = "";

    // Umeng
    public final static String UMENG_KEY = "";

    public final static String UMENG_SECRET = "";

    // app 更新存储文件名
    // SDCard/Download/qudong.apk
    public final static String ApkFileName = "qudong.apk";

    /**
     * SharePreference 存储
     */

    // SharePreference 本地存储文件名
    public final static String SPFileName = "qudong_config";

    // debug
    public final static String DEBUG = "debug";

    // hostOnline （线上or线下）
    public final static String HOST_ONLINE = "host_online";

    // first_open
    public final static String FIRST_OPEN = "first_open";

    // 极光id
    public final static String PUSH_ID = "push_id";

    // Umeng id
    public final static String UMENG_ID = "umeng_id";

    // taoabo auth
    public final static String TAOBAO_AUTH = "taobao_auth";

    // search history data
    public final static String HISTORY_SEARCH = "history_search";

    // config data
    public final static String CONFIG_DATA = "config_data";

    // login data
    public final static String LOGIN_DATA = "login_data";


    // channel data
    public final static String CHANNEL_CHOOSE = "channel_choose";

    public final static String CHANNEL_UNCHOOSE = "channel_unchoose";

    //notification end
    public final static String NOTICATION_ENDS = "notification_ends";

    /**
     * Log 相关
     */

    // Log 本地存储文件名
    // SDCard/Android/data/data/<application package>/cache/log
    public final static String LogCacheFileName = "log";

    /**
     * API 相关
     */

    // API Host
    public final static String BaseUrl = "http://106.15.201.180:10021/";

    // API Timeout 单位:秒
    public static final int TIMEOUT_READ = 25;

    public static final int TIMEOUT_WRITE = 25;

    public static final int TIMEOUT_CONNECTION = 25;

}
