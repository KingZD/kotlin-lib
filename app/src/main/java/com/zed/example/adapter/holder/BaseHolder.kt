package com.zed.example.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * @author zd
 * @package com.zed.example.adapter.holder
 * @fileName BaseHolder
 * @date on 2017/12/1 0001 15:15
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
open class BaseHolder : RecyclerView.ViewHolder {
    protected var mPosition = 0

    constructor(itemView: View) : super(itemView)

    fun setIndex(mPosition: Int) {
        this.mPosition = mPosition
    }
}