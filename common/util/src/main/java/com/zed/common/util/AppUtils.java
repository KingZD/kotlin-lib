package com.zed.common.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;

/**
 * Desc  : App相关工具类
 * <p>
 * https://github.com/Blankj/AndroidUtilCode
 */
public final class AppUtils {

    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 安装App(支持6.0及以上)
     *
     * @param context  上下文
     * @param filePath 文件路径
     */
    public static void installApp(Context context, String filePath) {
        installApp(context, FileUtils.getFileByPath(filePath));
    }

    /**
     * 安装App（支持6.0及以上）
     *
     * @param context 上下文
     * @param file    文件
     */
    public static void installApp(Context context, File file) {
        if (!FileUtils.isFileExists(file)) {
            return;
        }
        try {
            Intent intent = getInstallAppIntent(context, file);
            if (intent != null) {
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("AppUtils", "Method installApp() occur error: " + e.toString());
        }
    }

    /**
     * 安装App（支持6.0及以上）startActivityForResult
     *
     * @param activity    activity
     * @param filePath    文件路径
     * @param requestCode 请求值
     */
    public static void installApp(Activity activity, String filePath, int requestCode) {
        installApp(activity, FileUtils.getFileByPath(filePath), requestCode);
    }

    /**
     * 安装App(支持6.0及以上) startActivityForResult
     *
     * @param activity    activity
     * @param file        文件
     * @param requestCode 请求值
     */
    public static void installApp(Activity activity, File file, int requestCode) {
        if (!FileUtils.isFileExists(file)) {
            return;
        }
        try {
            Intent intent = getInstallAppIntent(activity, file);
            if (intent != null) {
                activity.startActivityForResult(intent, requestCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("AppUtils", "Method installApp(requestCode) occur error: " + e.toString());
        }
    }

    /**
     * 获取app安装intent (支持6.0及以上)
     * <p>
     * 需要新建 res/xml/provider_paths.xml，并在 AndroidManifest.xml 中声明 provider
     */
    private static Intent getInstallAppIntent(Context context, File file) {
        if (file == null) {
            return null;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri =
                    FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider",
                            file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        return intent;
    }

    /**
     * 获取App包名
     *
     * @return App包名
     */
    public static String getAppPackageName() {
        Context context = Utils.getApplicationContext();
        return context.getPackageName();
    }

    /**
     * 跳转至当前App具体设置
     */
    public static void goAppDetailsSettings() {
        Context context = Utils.getApplicationContext();
        goAppDetailsSettings(context.getPackageName());
    }

    /**
     * 获取App具体设置
     *
     * @param packageName 包名
     */
    public static void goAppDetailsSettings(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return;
        }
        Context context = Utils.getApplicationContext();
        context.startActivity(getAppDetailsSettingsIntent(packageName));
    }

    private static Intent getAppDetailsSettingsIntent(String packageName) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + packageName));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取App名称
     *
     * @return App名称
     */
    public static String getAppName() {
        Context context = Utils.getApplicationContext();
        return getAppName(context.getPackageName());
    }

    /**
     * 获取App名称
     *
     * @param packageName 包名
     * @return App名称
     */
    public static String getAppName(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return null;
        }
        try {
            Context context = Utils.getApplicationContext();
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App图标
     *
     * @return App图标
     */
    public static Drawable getAppIcon() {
        Context context = Utils.getApplicationContext();
        return getAppIcon(context.getPackageName());
    }

    /**
     * 获取App图标
     *
     * @param packageName 包名
     * @return App图标
     */
    public static Drawable getAppIcon(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return null;
        }
        try {
            Context context = Utils.getApplicationContext();
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App路径
     *
     * @return App路径
     */
    public static String getAppPath() {
        Context context = Utils.getApplicationContext();
        return getAppPath(context.getPackageName());
    }

    /**
     * 获取App路径
     *
     * @param packageName 包名
     * @return App路径
     */
    public static String getAppPath(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return null;
        }
        try {
            Context context = Utils.getApplicationContext();
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App版本号
     *
     * @return App版本号
     */
    public static String getAppVersionName() {
        Context context = Utils.getApplicationContext();
        return getAppVersionName(context.getPackageName());
    }

    /**
     * 获取App版本号
     *
     * @param packageName 包名
     * @return App版本号
     */
    public static String getAppVersionName(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return null;
        }
        try {
            Context context = Utils.getApplicationContext();
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App版本码
     *
     * @return App版本码
     */
    public static int getAppVersionCode() {
        Context context = Utils.getApplicationContext();
        return getAppVersionCode(context.getPackageName());
    }

    /**
     * 获取App版本码
     *
     * @param packageName 包名
     * @return App版本码
     */
    public static int getAppVersionCode(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return -1;
        }
        try {
            Context context = Utils.getApplicationContext();
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 读取application 节点  meta-data 信息
     *
     * @param key 键值对
     * @return meta-data信息
     */
    public static String getMetaData(String key) {
        if (StringUtils.isSpace(key)) {
            return null;
        }
        try {
            Context context = Utils.getApplicationContext();
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断App是否是Debug版本
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug() {
        Context context = Utils.getApplicationContext();
        return isAppDebug(context.getPackageName());
    }

    /**
     * 判断App是否是Debug版本 (可在 Manifest文件中 声明 android:debuggable)
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return false;
        }
        try {
            Context context = Utils.getApplicationContext();
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取App签名
     *
     * @return App签名
     */
    public static Signature[] getAppSignature() {
        Context context = Utils.getApplicationContext();
        return getAppSignature(context.getPackageName());
    }

    /**
     * 获取App签名
     *
     * @param packageName 包名
     * @return App签名
     */
    @SuppressLint("PackageManagerGetSignatures")
    public static Signature[] getAppSignature(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return null;
        }
        try {
            Context context = Utils.getApplicationContext();
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return pi == null ? null : pi.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取应用签名的的SHA1值
     * <p>可据此判断高德，百度地图key是否正确</p>
     *
     * @return 应用签名的SHA1字符串, 比如：53:FD:54:DC:19:0F:11:AC:B5:22:9E:F1:1A:68:88:1B:8B:E8:54:42
     */
    public static String getAppSignatureSHA1() {
        Context context = Utils.getApplicationContext();
        return getAppSignatureSHA1(context.getPackageName());
    }

    /**
     * 获取应用签名的的SHA1值
     * <p>可据此判断高德，百度地图key是否正确</p>
     *
     * @param packageName 包名
     * @return 应用签名的SHA1字符串, 比如：53:FD:54:DC:19:0F:11:AC:B5:22:9E:F1:1A:68:88:1B:8B:E8:54:42
     */
    public static String getAppSignatureSHA1(String packageName) {
        Signature[] signature = getAppSignature(packageName);
        if (signature == null) {
            return null;
        }
        return EncryptUtils.encryptSHA1ToString(signature[0].toByteArray()).
                replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
    }
}