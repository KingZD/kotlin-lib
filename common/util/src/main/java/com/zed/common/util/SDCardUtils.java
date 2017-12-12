package com.zed.common.util;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Date  : 2017-05-11
 * <p>
 * Desc  : SD卡相关工具类
 * <p>
 * https://github.com/Blankj/AndroidUtilCode
 */
public final class SDCardUtils {

    private SDCardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * SD卡是否存在
     *
     * @return true : 存在<br>false : 不存在
     */
    public static boolean isExistSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 判断SD卡是否可用 (剩余空间大于0)
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean isAvailableSDCard() {
        return getSDFreeSize() / 1024 / 1024 > 0;
    }

    /**
     * SD卡是否可用 (剩余空间大于要申请使用的空间)，多用于下载大文件场景
     *
     * @param needSize 申请使用的空间大小 单位 MB
     */
    public static boolean isAvailableSDCard(long needSize) {
        return getSDFreeSize() / 1024 / 1024 > needSize;
    }

    /**
     * 获取SD卡根目录
     *
     * @return String 文件路径 -> 字符串
     */
    public static String getRootPath() {
        File file = getRootDir();
        if (file != null) {
            return file.getPath();
        } else {
            return null;
        }
    }

    /**
     * 获取SD卡根目录
     *
     * @return File 文件
     */
    public static File getRootDir() {
        if (isExistSDCard()) {
            return Environment.getExternalStorageDirectory();
        } else {
            return null;
        }
    }

    /**
     * 获取SD卡剩余空间
     *
     * @return long SD卡剩余空间
     */
    public static long getSDFreeSize() {
        if (!isExistSDCard()) {
            return -1L;
        }
        StatFs stat = new StatFs(getRootPath());
        long blockSize, availableBlocks;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBlocks = stat.getAvailableBlocksLong();
            blockSize = stat.getBlockSizeLong();
        } else {
            availableBlocks = stat.getAvailableBlocks();
            blockSize = stat.getBlockSize();
        }
        //返回SD卡空闲大小
        //return freeBlocks * blockSize;  //单位Byte
        //return (freeBlocks * blockSize)/1024;   //单位KB
        //return (freeBlocks * blockSize) / 1024 / 1024; //单位MB
        return availableBlocks * blockSize;
    }

    /**
     * 获取SD卡剩余空间
     *
     * @return String SD卡剩余空间
     */
    public static String getSDFreeSpace() {
        if (!isExistSDCard()) {
            return null;
        }
        return FileUtils.byte2FitMemorySize(getSDFreeSize());
    }

    /**
     * 获取SD卡总容量
     *
     * @return long SD卡总容量
     */
    public static long getSDAllSize() {
        if (!isExistSDCard()) {
            return -1L;
        }
        StatFs stat = new StatFs(getRootPath());
        long blockSize, allBlocks;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            allBlocks = stat.getBlockSizeLong();
            blockSize = stat.getBlockCountLong();
        } else {
            allBlocks = stat.getBlockCount();
            blockSize = stat.getBlockSize();
        }
        //返回SD卡大小
        //return allBlocks * blockSize; //单位Byte
        //return (allBlocks * blockSize)/1024; //单位KB
        //return (allBlocks * blockSize) / 1024 / 1024; //单位MB
        return allBlocks * blockSize;
    }

    /**
     * 获取SD卡总容量
     *
     * @return String SD卡总容量
     */
    public static String getSDAllSpace() {
        if (!isExistSDCard()) {
            return null;
        }
        return FileUtils.byte2FitMemorySize(getSDAllSize());
    }

    //=================================  app 私有文件存储 (cache) =================================//

    /**
     * 获取[data]缓存目录（android 4.4后不需要获取权限）
     * getCacheDir()方法用于获取/data/data/<application package>/cache
     * getExternalCacheDir()方法用于获取SDCard/Android/data/<application package>/cache
     * 储存临时缓存数据
     */
    public static File getDiskCacheDir() {
        File cachePath;
        if (isExistSDCard()) {
            cachePath = Utils.getApplicationContext().getExternalCacheDir();
        } else {
            cachePath = Utils.getApplicationContext().getCacheDir();
        }
        return cachePath;
    }

    /**
     * 获取[data]缓存目录下特定目录（android 4.4后不需要获取权限）
     * <p>
     * /data/data/<application package>/cache/<uniqueName>
     * SDCard/Android/data/<application package>/cache/<uniqueName>
     */
    public static File getDiskCacheDir(String uniqueName) {
        String cacheDirPath = getDiskCacheDir().getPath() + File.separator + uniqueName;
        File cacheDir = FileUtils.getFileByPath(cacheDirPath);
        if (FileUtils.createOrExistsDir(cacheDir)) {
            return cacheDir;
        } else {
            return null;
        }
    }

    //=================================  app 私有文件存储 (files) =================================//

    /**
     * 获取[data]文件目录（android 4.4后不需要获取权限）
     * <p>
     * getFilesDir()方法用于获取/data/data/<application package>/files
     * getExternalFilesDir()方法用于获取SDCard/Android/data/<application package>/files
     * 储存一些长时间保存的数据
     */
    public static File getDiskFilesDir() {
        File filesPath;
        if (isExistSDCard()) {
            filesPath = Utils.getApplicationContext().getExternalFilesDir(null);
        } else {
            filesPath = Utils.getApplicationContext().getFilesDir();
        }
        return filesPath;
    }

    /**
     * 获取[data]文件目录下特定目录（android 4.4后不需要获取权限）
     * <p>
     * /data/data/<application package>/files/<typeName>
     * SDCard/Android/data/<application package>/files/<typeName>
     * 目录，一般放一些特定类型的文件，如图片、日志等
     */
    public static File getDiskFilesDir(String type) {
        String filesDirPath = getDiskFilesDir().getPath() + File.separator + type;
        File filesDir = FileUtils.getFileByPath(filesDirPath);
        if (FileUtils.createOrExistsDir(filesDir)) {
            return filesDir;
        } else {
            return null;
        }
    }

    //=====================================  app 公共文件存储  ========================================//

    /**
     * 获取外部存储 "下载" 文件夹
     * <p>
     * SDCard/Download
     */
    public static File getExternalDownloadDir() {
        File filesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (FileUtils.createOrExistsDir(filesDir)) {
            return filesDir;
        } else {
            return null;
        }
    }

    /**
     * 获取 App 外部存储目录
     * <p>
     * SDCard/<appName>
     *
     * @param appName app 名称
     */
    public static File getExternalAppDir(String appName) {
        String appDirPath = getRootPath() + File.separator + appName;
        File appDir = FileUtils.getFileByPath(appDirPath);
        if (FileUtils.createOrExistsDir(appDir)) {
            return appDir;
        } else {
            return null;
        }
    }

    /**
     * 获取 App 外部存储目录下特定目录
     * <p>
     * SDCard/<appName>/<uniqueName>
     *
     * @param appName    app 名称
     * @param uniqueName 特定目录名称
     */
    public static File getExternalAppFilesDir(String appName, String uniqueName) {
        String filesDirPath = getExternalAppDir(appName) + File.separator + uniqueName;
        File filesDir = FileUtils.getFileByPath(filesDirPath);
        if (FileUtils.createOrExistsDir(filesDir)) {
            return filesDir;
        } else {
            return null;
        }
    }
}