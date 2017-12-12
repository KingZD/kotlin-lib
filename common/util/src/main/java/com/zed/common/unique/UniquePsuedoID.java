package com.zed.common.unique;

import android.os.Build;
import android.text.TextUtils;
import java.util.UUID;

/**
 *
 * Date  : 2017-05-06
 *
 * Desc  : 设备唯一标示(无需权限)
 *
 * 《通过读取设备的ROM版本号、厂商名、CPU型号和其他硬件信息来组合出一串15位的号码》和《设备硬件序列号》作为种子生成UUID。
 *
 * 一串15位的号码（批量生产的设备每项信息基本相同，所以这一段相同的可能性特别高）
 * 硬件设备序列号（在一些没有电话功能的设备会提供，某些手机上也可能提供，但经常会返回unknown）
 */
public final class UniquePsuedoID {

    public static String getUniquePsuedoID() {
        // 主板
        String devIDShort = "35" + (Build.BOARD.length() % 10);
        // android 系统定制商
        devIDShort += Build.BRAND.length() % 10;
        // cpu 指令集
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // com.zed.http.api-level >= 21
            devIDShort += (Build.SUPPORTED_ABIS[0].length() % 10);
        } else {
            // com.zed.http.api-level < 21
            devIDShort += (Build.CPU_ABI.length() % 10);
        }

        devIDShort += Build.DEVICE.length() % 10 // 设备参数
            // 显示屏参数
            + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10
            // 修订版本列表
            + Build.ID.length() % 10
            // 硬件制造商
            + Build.MANUFACTURER.length() % 10
            // 版本
            + Build.MODEL.length() % 10
            // 手机制造商
            + Build.PRODUCT.length() % 10
            // 描述build的标签
            + Build.TAGS.length() % 10
            // builder类型
            + Build.TYPE.length() % 10 + Build.USER.length() % 10;// 13位

        String serial = Build.SERIAL;
        if (TextUtils.isEmpty(serial) || TextUtils.equals(serial, Build.UNKNOWN)) {
            serial = "serial";
        }

        return new UUID(devIDShort.hashCode(), serial.hashCode()).toString();
    }
}
