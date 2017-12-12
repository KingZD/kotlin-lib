package com.zed.http.header;

import android.os.Build;

import com.zed.common.util.AppUtils;
import com.zed.common.util.PhoneUtils;
import com.zed.common.util.SPUtils;
import com.zed.common.util.Utils;
import com.zed.common.constant.AppConstant;

import java.util.Map;

import okhttp3.Request;

/**
 * Desc  :
 */
public class HeaderParams {

    private static String device_id;

    private static String app_version;

    private static String sys_version;

    private static String uuid;

    private static String token;

    private static String appkey = AppConstant.APPKEY;

    private static String timestamp;

    private static String model;

    private static String platform = "Android";

    private static String sign;

    private static String user_agent;

    private static String accept = "application/json";

    private static String channel;

    private static String app_type = "kagou";

    private static String push_id;

    private static String umeng_id;

    private HeaderParams() {
        throw new UnsupportedOperationException("HeaderParams can not be instantiated!");
    }

    public static String getDevice_id() {
        if (HeaderParams.device_id == null) {
            device_id = PhoneUtils.getUniquePsuedoID();
        }
        return device_id;
    }

    public static String getApp_version() {
        if (HeaderParams.app_version == null) {
            app_version = AppUtils.getAppVersionName() + "." + AppUtils.getAppVersionCode();
        }
        return app_version;
    }

    public static String getSys_version() {
        if (HeaderParams.sys_version == null) {
            sys_version = PhoneUtils.getSysVersion();
        }
        return sys_version;
    }

    public static String getUUID() {
        if (HeaderParams.uuid == null) {
            uuid = PhoneUtils.getInstallationID();
        }
        return uuid;
    }

    public static String getToken() {
//        if (!EmptyUtils.isEmpty(KGManager.getToken())) {
//            token = KGManager.getToken();
//        } else {
//            token = "hey_dude";
//        }
        return token;
    }

    public static String getAppkey() {
        return appkey;
    }

    public static String getTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getPlatform() {
        return platform;
    }

    public static String getSign(Request originalRequest, Map<String, String> signParams) {
        return Signature.sign(originalRequest, signParams);
    }

    public static String getUser_agent() {
        if (HeaderParams.user_agent == null) {
            user_agent = "Android/" + AppUtils.getAppVersionName() + " (Android)";
        }
        return user_agent;
    }

    public static String getAccept() {
        return accept;
    }

    public static String getChannel() {
        if (HeaderParams.channel == null) {
            channel = PackerNg.getMarket(Utils.getApplicationContext(), "dev");
        }
        return channel;
    }

    public static String getApp_type() {
        return app_type;
    }

    public static String getPush_id() {
        if (HeaderParams.push_id == null) {
            push_id = SPUtils.getInstance().getString(AppConstant.PUSH_ID);
        }
        return push_id;
    }

    public static String getUmeng_id() {
        if (HeaderParams.umeng_id == null) {
            umeng_id = SPUtils.getInstance().getString(AppConstant.UMENG_ID);
        }
        return umeng_id;
    }

    public static String getContent_type() {
        return "application/json";
    }
}
