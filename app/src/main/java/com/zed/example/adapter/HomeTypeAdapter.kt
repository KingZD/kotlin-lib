package com.zed.example.adapter

import android.content.Context
import android.view.ViewGroup
import com.zed.common.util.UiUtil
import com.zed.example.R
import com.zed.example.adapter.holder.HomeTypeHolder
import com.zed.example.net.bean.HomeBean

/**
 * @author zd
 * @package com.bytc.qudong.adapter.home
 * @fileName HomeTypeAdapter
 * @date on 2017/12/1 0001 15:12
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
class HomeTypeAdapter : BaseAdapter<HomeTypeHolder, HomeBean> {

    constructor(mContext: Context?, mList: MutableList<HomeBean>?) : super(mContext, mList)


    override fun onBindViewHolder(holder: HomeTypeHolder, position: Int) {
        holder.setIndex(position)
        holder.init(mData?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): HomeTypeHolder {
        return HomeTypeHolder(UiUtil.inflate(mContext, R.layout.item_home, parent, false))
    }
}