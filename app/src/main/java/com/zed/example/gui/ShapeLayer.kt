package com.zed.example.gui

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.Shader.TileMode.CLAMP
import android.os.Build
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.ViewGroup
import com.zed.common.util.LogUtils
import com.zed.common.util.SizeUtils
import com.zed.example.R
import android.graphics.PorterDuffXfermode


/**
 * @author zd
 * @package com.zed.example.gui
 * @fileName ShapeView
 * @date on 2017/12/15 0015 09:45
 * @org 湖北博娱天成科技有限公司
 * @describe 支持垂直排列 如需其他排列方式 可以扩展
 * @email 1053834336@qq.com
 */
class ShapeLayer : ViewGroup {

    private var maxChildWidth = 0
    private val rect = RectF()
    private val shapeRect = RectF()
    private var paint: Paint = Paint(Paint.FILTER_BITMAP_FLAG or Paint.ANTI_ALIAS_FLAG)
    /**默认阴影大小**/
    private var mDefaultShadowWidth = 0f
    /**阴影大小**/
    private var mShadowWidth = 0f
    /**圆角大小**/
    private var mCornerRadius = 0f
    /**偏移量**/
    private var mOffsetX = 0f
    private var mOffsetY = 0f
    //阴影颜色
    private var mShapeLayerColor = ContextCompat.getColor(context, R.color.color_4c4c4c)
    private var mShapeLayerBackground = ContextCompat.getColor(context, R.color.color_ffffff)
    private var typeArray: TypedArray? = null
    private var mCornerRadiusEnable = true

    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        typeArray = context?.obtainStyledAttributes(attrs, R.styleable.ShapeLayer)
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        typeArray = context?.obtainStyledAttributes(attrs, R.styleable.ShapeLayer)
        initView()
    }

    /**设置阴影圆角(阴影大小)**/
    fun setCornerRadius(mCornerRadius: Int) {
        this.mCornerRadius = SizeUtils.dip2px(if (mCornerRadius == 0) 2 else mCornerRadius)
        postInvalidate()
    }

    /**设置圆角开启**/
    fun cornerRadiusEnable(mCornerRadiusEnable: Boolean) {
        this.mCornerRadiusEnable = mCornerRadiusEnable
        postInvalidate()
    }

    fun mShapeLayerColor(mShapeLayerColor: Int) {
        this.mShapeLayerColor = mShapeLayerColor
        postInvalidate()
    }

    /**设置view边距**/
    fun setShadowWidth(mShadowWidth: Int) {
        this.mShadowWidth = SizeUtils.dip2px(if (mShadowWidth == 0) 1 else mShadowWidth)
        postInvalidate()
    }

    fun setOffsetX(mOffsetX: Int) {
        this.mOffsetX = SizeUtils.dip2px(if (mOffsetX == 0) 1 else mOffsetX)
        postInvalidate()
    }

    fun setOffsetY(mOffsetY: Int) {
        this.mOffsetY = SizeUtils.dip2px(if (mOffsetY == 0) 1 else mOffsetY)
        postInvalidate()
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        val parentPaddingLeft = paddingLeft
        val parentPaddingTop = paddingTop

        if (childCount > 0) {
            var mHeight = 0
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                //获取 LayoutParams
                val lp = child.layoutParams as MarginLayoutParams
                //childView的四个顶点
                val left = parentPaddingLeft + lp.leftMargin
                val top = mHeight + parentPaddingTop + lp.topMargin
                val right = child.measuredWidth + parentPaddingLeft + lp.leftMargin
                val bottom = child.measuredHeight + mHeight + parentPaddingTop + lp.topMargin

                child.layout(left, top, right, bottom)
                // 累加已经绘制的childView的高
                mHeight += child.measuredHeight + lp.topMargin + lp.bottomMargin
            }
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val count = childCount
        // 临时ViewGroup大小值
        var viewGroupWidth = 0
        var viewGroupHeight = 0
        val defaultMargin = mShadowWidth.toInt()
        if (count > 0) {
            // 遍历childView
            for (i in 0 until count) {
                // childView
                val child = getChildAt(i)
                val lp = MarginLayoutParams(child.layoutParams)
                lp.setMargins(defaultMargin, defaultMargin, defaultMargin, defaultMargin)
                child.layoutParams = lp
                //测量childView包含外边距
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)
                // 计算父容器的期望值
                viewGroupWidth += child.measuredWidth + lp.leftMargin + lp.rightMargin
                viewGroupHeight += child.measuredHeight + lp.topMargin + lp.bottomMargin
                if (maxChildWidth < child.measuredWidth)
                    maxChildWidth = child.measuredWidth
            }

            // ViewGroup内边距
            viewGroupWidth += paddingLeft + paddingRight
            viewGroupHeight += paddingTop + paddingBottom

            //和建议最小值进行比较
            viewGroupWidth = Math.max(viewGroupWidth, suggestedMinimumWidth)
            viewGroupHeight = Math.max(viewGroupHeight, suggestedMinimumHeight)
        }
        setMeasuredDimension(resolveSize(viewGroupWidth, widthMeasureSpec), resolveSize(viewGroupHeight, heightMeasureSpec))
    }

    private fun initView() {
        setWillNotDraw(false)
        mDefaultShadowWidth = SizeUtils.dip2px(context, 5)
        mShadowWidth = mDefaultShadowWidth
        mCornerRadius = mDefaultShadowWidth
        mOffsetX = mDefaultShadowWidth
        mOffsetY = mDefaultShadowWidth
        if (typeArray != null) {
            mShadowWidth = typeArray!!.getDimension(R.styleable.ShapeLayer_mShapeLayerWidth, mDefaultShadowWidth)
            mCornerRadius = typeArray!!.getDimension(R.styleable.ShapeLayer_mShapeLayerCornerRadius, mCornerRadius)

            mOffsetX = typeArray!!.getDimension(R.styleable.ShapeLayer_mOffsetX, mOffsetX)
            mOffsetY = typeArray!!.getDimension(R.styleable.ShapeLayer_mOffsetY, mOffsetY)

            mShapeLayerBackground = typeArray!!.getColor(R.styleable.ShapeLayer_mShapeLayerBackground, mShapeLayerBackground)
            mShapeLayerColor = typeArray!!.getColor(R.styleable.ShapeLayer_mShapeLayerColor, mShapeLayerColor)
            mCornerRadiusEnable = typeArray!!.getBoolean(R.styleable.ShapeLayer_mShapeLayerCornerRadiusEnable, mCornerRadiusEnable)
        }
        paint = Paint(Paint.FILTER_BITMAP_FLAG or Paint.ANTI_ALIAS_FLAG)
        paint.isAntiAlias = true
        paint.color = mShapeLayerBackground
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rect.set(0f, 0f, w.toFloat(), h.toFloat())
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.clearShadowLayer()
        paint.setShadowLayer(mCornerRadius, mOffsetX, mOffsetY, mShapeLayerColor)
        shapeRect.set(mShadowWidth, mShadowWidth, rect.width() - mShadowWidth, rect.height() - mShadowWidth)
        if (mCornerRadiusEnable)
            canvas?.drawRoundRect(shapeRect, mShadowWidth, mShadowWidth, paint)
        else
            canvas?.drawRect(shapeRect, paint)
    }

}