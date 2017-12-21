package com.zed.example.fragment

import android.widget.SeekBar
import com.zed.example.R
import com.zed.example.base.BaseFragment
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
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                sv.setShadowWidth(p1)
                skSize.text = "阴影大小:".plus(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }

}