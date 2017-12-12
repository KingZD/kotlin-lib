package com.zed.common.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author zd
 * @package com.bytc.qudong.util
 * @fileName UiUtil
 * @date on 2017/12/1 0001 09:26
 * @org 湖北博娱天成科技有限公司
 * @describe 静态类所有方法都为静态方法，
 *           如工具类、常量池、等，直接把；
 *           类名前的class替换成object。
 * @email 1053834336@qq.com
 */
object UiUtil {

    fun inflate(context: Context?, layoutId: Int, viewGroup: ViewGroup?, attachToRoot: Boolean): View {
        val factory = LayoutInflater.from(context)
        return factory.inflate(layoutId, viewGroup, attachToRoot)
    }

    /**
     * StaggeredGridLayoutManager时，查找position最大的列
     *
     * @param lastVisiblePositions 最后显示的地址
     */
    fun findMax(lastVisiblePositions: IntArray): Int {
        var max = lastVisiblePositions[0]
        for (value in lastVisiblePositions) {
            if (value > max) {
                max = value
            }
        }
        return max
    }

    fun inflate(context: Context?, layoutId: Int): View? {
        return if (layoutId <= 0) {
            null
        } else LayoutInflater.from(context).inflate(layoutId, null)
    }
}