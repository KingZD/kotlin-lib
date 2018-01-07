package com.zed.example.fragment

import android.graphics.Color
import android.view.View
import android.widget.SeekBar
import com.zed.example.R
import com.zed.example.base.BaseFragment
import com.zed.example.base.BasePresenter
import kotlinx.android.synthetic.main.fragment_second.*
import java.util.*

/**
 * @author zd
 * @package com.zed.example.activity.fragment
 * @fileName HomeFragment
 * @date on 2017/12/15 0015 14:55
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
class SecondFragment : BaseFragment<BasePresenter<*, *>>(), View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.r -> sv.cornerRadiusEnable(true)
            R.id.z -> sv.cornerRadiusEnable(false)
            R.id.color -> {
                sv.mShapeLayerColor(Color.rgb(Random().nextInt(255), Random().nextInt(255), Random().nextInt(255)))
            }
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.fragment_second
    }

    override fun initView() {
        r.setOnClickListener(this)
        z.setOnClickListener(this)
        color.setOnClickListener(this)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                sv.setShadowWidth(p1)
                skSize.text = "组件边距:".plus(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
        seekBar3.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                sv.setCornerRadius(p1)
                skSize3.text = "阴影大小:".plus(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
        seekBar1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                sv.setOffsetX(p1 - 50)
                skSize1.text = "偏移量X:".plus(p1 - 50)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
        seekBar2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                sv.setOffsetY(p1 - 50)
                skSize2.text = "偏移量Y:".plus(p1 - 50)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }

}