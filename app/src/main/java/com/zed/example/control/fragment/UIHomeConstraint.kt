package com.bytc.qudong.control.fragment

import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zed.example.base.IBaseConstruction
import com.zed.view.XRecyclerView

/**
 * @author zd
 * @package com.bytc.qudong.control.fragment
 * @fileName UIHomeConstraint
 * @date on 2017/12/1 0001 16:49
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
interface UIHomeConstraint : IBaseConstruction {
    fun getRlView(): XRecyclerView?
    fun getSmartPull(): SmartRefreshLayout?
}