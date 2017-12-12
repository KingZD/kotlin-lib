package com.zed.common.util;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;

/**
 * Date  : 2017-06-13
 * <p>
 * Desc  : 一些系统过时方法的处理
 * <p>
 * https://github.com/Blankj/AndroidUtilCode
 */
public class CompatUtils {

    private CompatUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * setBackgroundDrawable过时方法处理
     */
    public static void setBackground(@NonNull View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    /**
     * getDrawable过时方法处理
     */
    public static Drawable getDrawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(Utils.getApplicationContext(), id);
    }

    /**
     * getDrawable过时方法处理
     *
     * @param id    资源id
     * @param theme 指定主题
     */
    public static Drawable getDrawable(@DrawableRes int id, @Nullable Resources.Theme theme) {
        return ResourcesCompat.getDrawable(Utils.getApplicationContext().getResources(), id, theme);
    }

    /**
     * getColor过时方法处理
     */
    public static int getColor(@ColorRes int id) {
        return ContextCompat.getColor(Utils.getApplicationContext(), id);
    }

    /**
     * getColor过时方法处理
     *
     * @param id    资源id
     * @param theme 指定主题
     */
    public static int getColor(@ColorRes int id, @Nullable Resources.Theme theme) {
        return ResourcesCompat.getColor(Utils.getApplicationContext().getResources(), id, theme);
    }

    /**
     * getColorStateList过时方法处理
     *
     * @param id 资源id
     */
    public static ColorStateList getColorStateList(@ColorRes int id) {
        return ContextCompat.getColorStateList(Utils.getApplicationContext(), id);
    }

    /**
     * getColorStateList过时方法处理
     *
     * @param id    资源id
     * @param theme 指定主题
     */
    public static ColorStateList getColorStateList(@ColorRes int id,
                                                   @Nullable Resources.Theme theme) {
        return ResourcesCompat.getColorStateList(Utils.getApplicationContext().getResources(), id,
                theme);
    }
}
