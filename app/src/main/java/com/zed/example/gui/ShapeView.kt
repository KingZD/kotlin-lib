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
class ShapeView : ViewGroup {

    private var maxChildWidth = 0
    private val rect = RectF()
    private var paint: Paint = Paint(Paint.FILTER_BITMAP_FLAG or Paint.ANTI_ALIAS_FLAG)
    /**默认阴影大小**/
    private var mDefaultShadowWidth = 0f
    /**阴影大小**/
    private var mShadowWidth = 0f
    /**圆角大小**/
    private var mCornerRadius = 0f
    /**组件背景色**/
    private var shapeBackground = ContextCompat.getColor(context, R.color.color_ffffff)
    /**默认阴影起始颜色**/
    private var startColor = ContextCompat.getColor(context, R.color.color_ffffff)
    /**默认阴影中间颜色**/
    private var centerColor = ContextCompat.getColor(context, R.color.color_f2eff0)
    /**默认阴影结束颜色**/
    private var endColor = ContextCompat.getColor(context, R.color.transparent)
    private var typeArray: TypedArray? = null
    private var mCornerRadiusEnable = true
    /**默认阴影起始颜色位置比例**/
    private var startPosition = 0f
    /**默认阴影中间颜色位置比例**/
    private var centerPosition = 0.1f
    /**默认阴影结束颜色位置比例**/
    private var endPosition = 1f
    /**默认填充的背景色**/
    private var bitmap: Bitmap? = null
    private var colors = intArrayOf()
    private var stops = floatArrayOf()

    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        typeArray = context?.obtainStyledAttributes(attrs, R.styleable.ShapeView)
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        typeArray = context?.obtainStyledAttributes(attrs, R.styleable.ShapeView)
        initView()
    }

    /**设置圆角**/
    @Deprecated("默认为阴影宽度2倍 设置无效 可以自己更改")
    fun setCornerRadius(mCornerRadius: Int) {
        this.mCornerRadius = SizeUtils.dip2px(if (mCornerRadius == 0) 2 else mCornerRadius)
        this.mShadowWidth = this.mCornerRadius / 2
        postInvalidate()
    }

    /**设置中间颜色起始百分比位置**/
    fun shapeCenterPosition(centerPosition: Float) {
        this.centerPosition = centerPosition
        stops[1] = centerPosition
        postInvalidate()
    }

    fun shapeCenterColor(centerColor: Int) {
        this.centerColor = centerColor
        colors[1] = centerColor
        postInvalidate()
    }

    fun shapeStartColor(startColor: Int) {
        this.startColor = startColor
        colors[0] = startColor
        postInvalidate()
    }

    fun shapeEndColor(endColor: Int) {
        this.endColor = endColor
        colors[2] = endColor
        postInvalidate()
    }

    /**设置圆角**/
    fun cornerRadiusEnable(mCornerRadiusEnable: Boolean) {
        this.mCornerRadiusEnable = mCornerRadiusEnable
        invalidate()
    }

    /**设置阴影宽度**/
    fun setShadowWidth(mShadowWidth: Int) {
        this.mShadowWidth = SizeUtils.dip2px(if (mShadowWidth == 0) 1 else mShadowWidth)
        mCornerRadius = this.mShadowWidth * 2
        postInvalidate()
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
//        if (childCount > 1)
//            throw RuntimeException("只允许有一个子布局")
        // ViewGroup的内边距
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
        mCornerRadius = mDefaultShadowWidth * 2
        if (typeArray != null) {
            mShadowWidth = typeArray!!.getDimension(R.styleable.ShapeView_mShadowWidth, mDefaultShadowWidth)
            mCornerRadius = mShadowWidth * 2

            startPosition = typeArray!!.getFloat(R.styleable.ShapeView_startPosition, startPosition)
            centerPosition = typeArray!!.getFloat(R.styleable.ShapeView_centerPosition, centerPosition)
            endPosition = typeArray!!.getFloat(R.styleable.ShapeView_endPosition, endPosition)

            shapeBackground = typeArray!!.getColor(R.styleable.ShapeView_shapeBackground, shapeBackground)
            startColor = typeArray!!.getColor(R.styleable.ShapeView_startColor, startColor)
            centerColor = typeArray!!.getColor(R.styleable.ShapeView_centerColor, centerColor)
            endColor = typeArray!!.getColor(R.styleable.ShapeView_endColor, endColor)
            mCornerRadiusEnable = typeArray!!.getBoolean(R.styleable.ShapeView_mCornerRadiusEnable, mCornerRadiusEnable)
        }
        paint = Paint(Paint.FILTER_BITMAP_FLAG or Paint.ANTI_ALIAS_FLAG)
        colors = intArrayOf(startColor, centerColor, endColor)
        stops = floatArrayOf(startPosition, centerPosition, endPosition)
        paint.isAntiAlias = true
        paint.color = Color.GREEN
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rect.set(0f, 0f, w.toFloat(), h.toFloat())
        bitmap = Bitmap.createBitmap(rect.width().toInt(), rect.height().toInt(), Bitmap.Config.RGB_565)
        bitmap?.eraseColor(shapeBackground)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (mCornerRadius >= (rect.height() - mShadowWidth * 2) / 2) {
            mCornerRadius = (rect.height() - mShadowWidth * 2) / 2
            if (rect.width() < rect.height())
                mCornerRadius = (rect.width() - mShadowWidth * 2) / 2

            this.mCornerRadius = if (mCornerRadius == 0f) 2f else mCornerRadius
            this.mShadowWidth = this.mCornerRadius / 2
        }
        var saveCount =
                if (Build.VERSION_CODES.KITKAT_WATCH <= Build.VERSION.SDK_INT)
                    canvas?.saveLayer(rect, paint)
                else
                    canvas?.saveLayer(rect, paint, Canvas.ALL_SAVE_FLAG)
        if (mCornerRadiusEnable) {
            /**--------------------------------------圆角公用-----------------------------------------**/
            enableCorner(canvas)
        } else {
            /**--------------------------------------直角公用-----------------------------------------**/
            rectangle(canvas)
        }
        paint.reset()
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_ATOP)
        /**--------------------------------------通用四边区域-----------------------------------------**/
        generalCanvas(canvas)
        paint.reset()
        /**--------------------------------------背景-----------------------------------------**/

        paint.shader = BitmapShader(bitmap, CLAMP, CLAMP)
        paint.isAntiAlias = true
        if (mCornerRadiusEnable)
            canvas?.drawRoundRect(RectF(mShadowWidth, mShadowWidth, rect.width() - mShadowWidth, rect.height() - mShadowWidth), mShadowWidth * (1 - centerPosition), mShadowWidth * (1 - centerPosition), paint)
        else
            canvas?.drawRect(RectF(mShadowWidth, mShadowWidth, rect.width() - mShadowWidth, rect.height() - mShadowWidth), paint)
        paint.reset()
        //清除混合模式
        paint.xfermode = null
        //还原画布
        canvas?.restoreToCount(saveCount!!)
    }

    private fun rectangle(canvas: Canvas?) {
        paint.isAntiAlias = true
        /**--------------------------------------右下直角------------------------------------------------**/

        paint.shader = RadialGradient(rect.width() - mShadowWidth,
                rect.height() - mShadowWidth,
                mShadowWidth,
                colors,
                stops,
                CLAMP
        )
        canvas?.drawRect(RectF(rect.width() - mShadowWidth, rect.height() - mShadowWidth, rect.width(), rect.height()), paint)

        /**--------------------------------------右上直角------------------------------------------------**/

        paint.shader = RadialGradient(rect.width() - mShadowWidth,
                mShadowWidth,
                mShadowWidth,
                colors,
                stops,
                CLAMP
        )
        canvas?.drawRect(RectF(rect.width() - mShadowWidth, 0f, rect.width(), mShadowWidth), paint)

        /**--------------------------------------左上直角------------------------------------------------**/

        paint.shader = RadialGradient(mShadowWidth,
                mShadowWidth,
                mShadowWidth,
                colors,
                stops,
                CLAMP
        )
        canvas?.drawRect(RectF(0f, 0f, mShadowWidth, mShadowWidth), paint)

        /**-------------------------------------左下直角-------------------------------------------------**/

        paint.shader = RadialGradient(mShadowWidth,
                rect.height() - mShadowWidth,
                mShadowWidth,
                colors,
                stops,
                CLAMP
        )
        canvas?.drawRect(RectF(0f, rect.height() - mShadowWidth, mShadowWidth, rect.height()), paint)
    }

    private fun generalCanvas(canvas: Canvas?) {
        val offset = 0f//避免圆角和四边重合
        paint.isAntiAlias = true
        /**---------------------------------------左-----------------------------------------------**/

        paint.shader = LinearGradient(mShadowWidth,
                0f,
                0f,
                0f,
                colors,
                stops,
                CLAMP
        )
        if (mCornerRadiusEnable)
            canvas?.drawRect(RectF(0f, mCornerRadius + offset, mShadowWidth, rect.height() - mCornerRadius), paint)
        else
            canvas?.drawRect(RectF(0f, mShadowWidth, mShadowWidth, rect.height() - mShadowWidth), paint)


        /**---------------------------------------右-----------------------------------------------**/

        paint.shader = LinearGradient(rect.width() - mShadowWidth,
                0f,
                rect.width(),
                0f,
                colors,
                stops,
                CLAMP
        )
        if (mCornerRadiusEnable)
            canvas?.drawRect(RectF(rect.width() - mShadowWidth, mCornerRadius + offset, rect.width(), rect.height() - mCornerRadius), paint)
        else
            canvas?.drawRect(RectF(rect.width() - mShadowWidth, mShadowWidth, rect.width(), rect.height() - mShadowWidth), paint)

        /**---------------------------------------上-----------------------------------------------**/

        paint.shader = LinearGradient(0f,
                mShadowWidth,
                0f,
                0f,
                colors,
                stops,
                CLAMP
        )
        if (mCornerRadiusEnable)
            canvas?.drawRect(RectF(mCornerRadius, 0f, rect.width() - mCornerRadius, mShadowWidth), paint)
        else
            canvas?.drawRect(RectF(mShadowWidth, 0f, rect.width() - mShadowWidth, mShadowWidth), paint)

        /**---------------------------------------下-----------------------------------------------**/

        paint.shader = LinearGradient(0f,
                rect.height() - mShadowWidth,
                0f,
                rect.height(),
                colors,
                stops,
                CLAMP
        )
        if (mCornerRadiusEnable)
            canvas?.drawRect(RectF(mCornerRadius, rect.height() - mShadowWidth, rect.width() - mCornerRadius, rect.height()), paint)
        else
            canvas?.drawRect(RectF(mShadowWidth, rect.height() - mShadowWidth, rect.width() - mShadowWidth, rect.height()), paint)
    }

    private fun enableCorner(canvas: Canvas?) {
        val sp = 1 - mShadowWidth / mCornerRadius
        val cp = sp + centerPosition * mShadowWidth / mCornerRadius
        val stops = floatArrayOf(sp, cp, endPosition)
        val offset = -5f//避免圆角和四边重合

        /**--------------------------------------圆角公用-----------------------------------------**/
        paint.strokeWidth = mShadowWidth * 2
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        /**--------------------------------------左上圆角------------------------------------------------**/

        paint.shader = RadialGradient(mCornerRadius,
                mCornerRadius,
                mCornerRadius,
                colors,
                stops,
                CLAMP
        )
//        canvas?.drawCircle(mCornerRadius - offset, mCornerRadius - offset, mCornerRadius, paint)
        canvas?.drawArc(RectF(mShadowWidth, mShadowWidth, mCornerRadius + mShadowWidth - offset, mCornerRadius + mShadowWidth - offset), 180f, 90f, false, paint)

        /**--------------------------------------右上圆角------------------------------------------------**/

        paint.shader = RadialGradient(rect.width() - mCornerRadius,
                mCornerRadius,
                mCornerRadius,
                colors,
                stops,
                CLAMP
        )
        canvas?.drawArc(RectF(rect.width() - mCornerRadius - mShadowWidth + offset, mShadowWidth, rect.width() - mShadowWidth, mCornerRadius + mShadowWidth - offset), 270f, 90f, false, paint)

        /**--------------------------------------左下圆角------------------------------------------------**/

        paint.shader = RadialGradient(mCornerRadius,
                rect.height() - mCornerRadius,
                mCornerRadius,
                colors,
                stops,
                CLAMP
        )
        canvas?.drawArc(RectF(mShadowWidth, rect.height() - mCornerRadius - mShadowWidth + offset, mCornerRadius + mShadowWidth - offset, rect.height() - mShadowWidth), 90f, 90f, false, paint)

        /**--------------------------------------右下圆角------------------------------------------------**/

        paint.shader = RadialGradient(rect.width() - mCornerRadius,
                rect.height() - mCornerRadius,
                mCornerRadius,
                colors,
                stops,
                CLAMP
        )
        canvas?.drawArc(RectF(rect.width() - mCornerRadius - mShadowWidth + offset, rect.height() - mCornerRadius - mShadowWidth + offset, rect.width() - mShadowWidth, rect.height() - mShadowWidth), 0f, 90f, false, paint)
    }
}