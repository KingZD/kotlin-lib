package com.zed.example.fragment

import android.graphics.drawable.BitmapDrawable
import android.support.v4.content.ContextCompat
import com.zed.common.util.SizeUtils
import com.zed.example.R
import com.zed.example.base.BaseFragment
import com.zed.image.round.CornerType
import com.zed.image.round.RoundedBitmapDrawableFactory
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author zd
 * @package com.zed.example.activity.fragment
 * @fileName HomeFragment
 * @date on 2017/12/15 0015 14:55
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
class HomeFragment : BaseFragment() {
    override fun setLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        val bd = ContextCompat.getDrawable(activity!!.applicationContext, R.mipmap.ic_login_head_bg) as BitmapDrawable
        val rb = RoundedBitmapDrawableFactory.create(resources, bd.bitmap, CornerType.ALL)
        rb.cornerRadius = SizeUtils.dip2px(10)
        svBg.setImageDrawable(rb)
    }

}