package com.zed.example.fragment

import android.graphics.Color
import android.view.View
import android.widget.SeekBar
import com.zed.example.R
import com.zed.example.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
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
class HomeFragment : BaseFragment(), View.OnClickListener {
    override fun setLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.color -> {
                sv.shapeStartColor(Color.rgb(Random().nextInt(255), Random().nextInt(255), Random().nextInt(255)))
                sv.shapeCenterColor(Color.rgb(Random().nextInt(155), Random().nextInt(155), Random().nextInt(155)))
            }
            R.id.position -> {
                val float = Random().nextFloat()
                sv.shapeCenterPosition(float)
                position.text = "颜色起始百分比:".plus(float)
            }
        }
    }

    override fun initView() {
        color.setOnClickListener(this)
        position.setOnClickListener(this)
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