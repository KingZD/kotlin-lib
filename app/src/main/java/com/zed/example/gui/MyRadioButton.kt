package com.zed.example.gui

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.widget.RadioButton
import com.zed.example.R


/**
 * @author zd
 * @package com.zed.example.gui
 * @fileName MyRadioButton
 * @date on 2017/12/8 0008 10:54
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
class MyRadioButton : RadioButton {
    private var mGradientMatrix: Matrix? = Matrix()//渐变矩阵
    private var mViewWidth: Int = 0//textView的宽
    private var mTranslate: Int = 0//平移量
    private var txt: String? = null
    private val mAnimating = true//是否动画
    private var delta = 15//移动增量
    private var size: Int = 0
    private val r = Rect()
    private var mTextWidth: Float = 0f
    private var s = 0f

    constructor(ctx: Context) : super(ctx)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (mViewWidth == 0) {
            mViewWidth = measuredWidth
            if (mViewWidth > 0) {
                txt = text.toString()

                size = if (text!!.isNotEmpty()) {
                    mViewWidth * 2 / text!!.length
                } else {
                    mViewWidth
                }
                mTextWidth = paint.measureText(txt)//获得文字宽
                paint.getTextBounds(txt, 0, 1, r)
                s = (size.toFloat() - mTextWidth) / 2
            }
        }
        when (isChecked) {
            true -> press()
            false -> normal()
        }
    }

    override fun setChecked(checked: Boolean) {
        super.setChecked(checked)
        when (checked) {
            true -> press()
            false -> normal()
        }
    }

    private fun normal() {
        if (mViewWidth == 0) return
        paint.shader = LinearGradient(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat()
                , ContextCompat.getColor(context, R.color.color_333333)
                , ContextCompat.getColor(context, R.color.color_333333)
                , Shader.TileMode.CLAMP)//设置渐变
        postInvalidate()
    }

    private fun press() {
        if (mViewWidth == 0) return
        paint.shader = LinearGradient(s, 0f, s + mTextWidth, r.height().toFloat() / 2
                , ContextCompat.getColor(context, R.color.color_ff9966)
                , ContextCompat.getColor(context, R.color.color_ff4979)
                , Shader.TileMode.CLAMP) //边缘融合
        postInvalidate()
    }
}