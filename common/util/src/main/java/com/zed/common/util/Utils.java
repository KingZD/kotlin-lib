package com.zed.common.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.StringRes;
import android.view.View;

/**
 * Date  : 2017-05-11
 * <p>
 * Desc  : Utils初始化相关 主要用于获取 app context
 */
public final class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Context sApplicationContext;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        Utils.sApplicationContext = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getApplicationContext() {
        if (sApplicationContext == null) {
            throw new RuntimeException("u should init utils first");
        } else {
            return sApplicationContext;
        }
    }

    /**
     * View获取Activity的工具
     *
     * @param view view
     * @return Activity
     */
    public static Activity getActivity(View view) {
        Context context = view.getContext();

        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }

        throw new IllegalStateException("View " + view + " is not attached to an Activity");
    }

    /**
     * 全局获取String的方法
     *
     * @param id 资源Id
     * @return String
     */
    public static String getString(@StringRes int id) {
        return sApplicationContext.getResources().getString(id);
    }

    /**
     * 判断App是否是Debug版本
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug() {
        return AppUtils.isAppDebug();
    }
}