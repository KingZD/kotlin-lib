package com.zed.example.adapter.holder

import android.view.View
import com.zed.example.R
import com.zed.example.base.IBaseHolderConstruction
import com.zed.example.net.bean.BaseBean
import com.zed.example.net.bean.HomeBean
import com.zed.image.GlideUtils
import kotlinx.android.synthetic.main.item_home.view.*

/**
 * @author zd
 * @package com.bytc.qudong.adapter.holder
 * @fileName HomeTypeHolder
 * @date on 2017/12/1 0001 15:50
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
class HomeTypeHolder : BaseHolder, IBaseHolderConstruction<HomeBean> {

    constructor(view: View) : super(view)

    override fun init(bean: HomeBean?) {
        GlideUtils.Builder
                .with(itemView)
                .asBitmap()
                .load(bean?.url)
                .placeholder(R.mipmap.ic_user_default)
                .round(60)
                .into(itemView.ivIcon)
        GlideUtils.Builder
                .with(itemView)
                .asBitmap()
                .load(bean?.url)
                .placeholder(R.mipmap.ic_user_default)
                .round(10)
                .cornerType(bean?.type)
                .dontCenterCrop()
                .into(itemView.ivImg)
        itemView.tvContent.text = "圆角类型->".plus(bean?.type?.name)
        itemView.tvName.text = "Name-".plus(mPosition)
    }
}