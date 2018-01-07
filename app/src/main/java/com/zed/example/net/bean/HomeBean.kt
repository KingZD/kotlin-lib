package com.zed.example.net.bean

import com.zed.image.round.CornerType

/**
 * Created by ZD on 2018/1/7.
 */
data class HomeBean(
        val type: CornerType,//圆角类型
        val url: String//地址
) : BaseBean()