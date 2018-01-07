package com.zed.example.fragment

import com.bytc.qudong.control.fragment.UIHomeConstraint
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zed.example.R
import com.zed.example.base.BaseFragment
import com.zed.example.presenter.HomePresenter
import com.zed.view.XRecyclerView
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
class HomeFragment : BaseFragment<HomePresenter>(), UIHomeConstraint {
    override fun getRlView(): XRecyclerView? {
        return rv
    }

    override fun getSmartPull(): SmartRefreshLayout? {
        return smartPull
    }

    override fun setLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        setPresenter(HomePresenter(this))
    }

}