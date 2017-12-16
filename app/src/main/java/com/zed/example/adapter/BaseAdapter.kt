package com.zed.example.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.zed.example.adapter.holder.BaseHolder
import com.zed.example.net.bean.BaseBean

/**
 * @author zd
 * @package com.zed.example.adapter
 * @fileName BaseAdapter
 * @date on 2017/12/1 0001 15:52
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
abstract class BaseAdapter<T : BaseHolder, M : BaseBean> : RecyclerView.Adapter<T> {
    protected var mContext: Context? = null
    protected var mData: List<M>? = null

    constructor(mContext: Context?, data: List<M>?) {
        this.mContext = mContext
        this.mData = data
    }

    override fun getItemCount(): Int {
        return mData?.size ?: 0
    }
}