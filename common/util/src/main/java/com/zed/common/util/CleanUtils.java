package com.zed.common.util;

import android.content.Context;

import java.io.File;

/**
 * Date  : 2017-06-13
 * <p>
 * Desc  : 清除相关工具类
 * <p>
 * https://github.com/Blankj/AndroidUtilCode
 */
public final class CleanUtils {

    private CleanUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 清除内部缓存
     * <p>/data/data/{@code <package name>}/cache</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalCache() {
        Context context = Utils.getApplicationContext();
        return FileUtils.deleteFilesInDir(context.getCacheDir());
    }

    /**
     * 清除内部文件
     * <p>/data/data/{@code <package name>}/files</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalFiles() {
        Context context = Utils.getApplicationContext();
        return FileUtils.deleteFilesInDir(context.getFilesDir());
    }

    /**
     * 清除内部数据库
     * <p>/data/data/{@code <package name>}/databases</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalDbs() {
        Context context = Utils.getApplicationContext();
        return FileUtils.deleteFilesInDir(
                context.getFilesDir().getParent() + File.separator + "databases");
    }

    /**
     * 根据名称清除内部数据库
     * <p>/data/data/{@code <package name>}/databases/{@code <db name>}</p>
     *
     * @param dbName 数据库名称
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalDbByName(String dbName) {
        Context context = Utils.getApplicationContext();
        return context.deleteDatabase(dbName);
    }

    /**
     * 清除内部SP
     * <p>/data/data/{@code <package name>}/shared_prefs</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalSP() {
        Context context = Utils.getApplicationContext();
        return FileUtils.deleteFilesInDir(
                context.getFilesDir().getParent() + File.separator + "shared_prefs");
    }

    /**
     * 清除外部缓存
     * <p>/storage/emulated/0/android/data/{@code <package name>}/cache</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanExternalCache() {
        Context context = Utils.getApplicationContext();
        return SDCardUtils.isExistSDCard() && FileUtils.deleteFilesInDir(
                context.getExternalCacheDir());
    }

    /**
     * 清除外部文件
     * <p>/storage/emulated/0/android/data/{@code <package name>}/files</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanExternalFiles() {
        Context context = Utils.getApplicationContext();
        return FileUtils.deleteFilesInDir(context.getExternalFilesDir(""));
    }

    /**
     * 清除自定义目录下的文件
     *
     * @param dirPath 目录路径
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanCustomCache(String dirPath) {
        return FileUtils.deleteFilesInDir(dirPath);
    }

    /**
     * 清除自定义目录下的文件
     *
     * @param dir 目录
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanCustomCache(File dir) {
        return FileUtils.deleteFilesInDir(dir);
    }

    /**
     * 清除浏览器缓存
     */
    public static void cleanWebviewCache(Context context) {
        context.deleteDatabase("webview.db");
        context.deleteDatabase("webviewCache.db");
    }
}
