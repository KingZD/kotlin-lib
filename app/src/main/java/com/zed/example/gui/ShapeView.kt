package com.zed.example.gui

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.Shader.TileMode.CLAMP
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.widget.ImageView
import com.zed.common.util.LogUtils
import com.zed.common.util.SizeUtils
import com.zed.example.R


/**
 * @author zd
 * @package com.zed.example.gui
 * @fileName ShapeView
 * @date on 2017/12/15 0015 09:45
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
class ShapeView : ImageView {
    private val rect = RectF()
    private val paint = Paint(Paint.FILTER_BITMAP_FLAG or Paint.ANTI_ALIAS_FLAG)
    private var mBitmap: Bitmap? = null
    private var mDefaultShadowWidth = 0f
    private var mShadowWidth = 0f
    private var mCornerRadius = 0f
    private var startColor = ContextCompat.getColor(context, R.color.color_edeaeb)
    private var centerColor = ContextCompat.getColor(context, R.color.color_f2eff0)
    private var endColor = ContextCompat.getColor(context, R.color.transparent)
    private var typeArray: TypedArray? = null
    private var mCornerRadiusEnable = true

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

    /**设置背景**/
    fun setBitmap(bitmap: Bitmap?) {
        if (mBitmap != null && !mBitmap!!.isRecycled)
            mBitmap!!.recycle()
        this.mBitmap = bitmap
        invalidate()
    }

    /**设置圆角**/
    fun setCornerRadius(mCornerRadius: Float) {
        this.mCornerRadius = mCornerRadius
    }

    /**设置圆角**/
    fun cornerRadiusEnable(mCornerRadiusEnable: Boolean) {
        this.mCornerRadiusEnable = mCornerRadiusEnable
        invalidate()
    }

    /**设置阴影宽度**/
    fun setShadowWidth(mShadowWidth: Float) {
        this.mShadowWidth = mShadowWidth
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        invalidate()
    }

    private fun initView() {
        mDefaultShadowWidth = SizeUtils.dip2px(context, 5)
        mShadowWidth = mDefaultShadowWidth
        mCornerRadius = mDefaultShadowWidth * 2
        if (typeArray != null) {
            mShadowWidth = typeArray!!.getDimension(R.styleable.ShapeView_mShadowWidth, mDefaultShadowWidth)
            mCornerRadius = mShadowWidth * 2

            startColor = typeArray!!.getColor(R.styleable.ShapeView_startColor, startColor)
            centerColor = typeArray!!.getColor(R.styleable.ShapeView_centerColor, centerColor)
            endColor = typeArray!!.getColor(R.styleable.ShapeView_endColor, endColor)
            mCornerRadiusEnable = typeArray!!.getBoolean(R.styleable.ShapeView_mCornerRadiusEnable, mCornerRadiusEnable)
        }
        paint.isAntiAlias = true
        paint.color = Color.GREEN
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rect.set(0f, 0f, w.toFloat(), h.toFloat())
        LogUtils.e("ShapeView", "ShapeView->".plus(w).plus("-").plus(h))
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        if (mCornerRadius >= (rect.height() - mShadowWidth * 2) / 2) {
            mCornerRadius = (rect.height() - mShadowWidth * 2) / 2
            if (rect.width() < rect.height())
                mCornerRadius = (rect.width() - mShadowWidth * 2) / 2
        }
        if (mBitmap != null) {
            var src = Rect()
            src.set(
                    rect.left.toInt(),
                    rect.top.toInt(),
                    rect.right.toInt(),
                    rect.bottom.toInt()
            )
            canvas?.drawBitmap(mBitmap, src, rect, paint)
        }

        /**--------------------------------------通用四边区域-----------------------------------------**/
        generalCanvas(canvas)
        if (mCornerRadiusEnable) {
            /**--------------------------------------圆角公用-----------------------------------------**/
            enableCorner(canvas)
        } else {
            /**--------------------------------------直角公用-----------------------------------------**/
            rectangle(canvas)
        }
    }

    private fun rectangle(canvas: Canvas?) {
        /**--------------------------------------右下直角------------------------------------------------**/
        paint.shader = RadialGradient(rect.width() - mShadowWidth,
                rect.height() - mShadowWidth,
                mShadowWidth,
                intArrayOf(startColor,
                        centerColor,
                        endColor),
                floatArrayOf(0f, 0.1f, 1f),
                CLAMP
        )
        canvas?.drawRect(RectF(rect.width() - mShadowWidth, rect.height() - mShadowWidth, rect.width(), rect.height()), paint)

        /**--------------------------------------右上直角------------------------------------------------**/

        paint.shader = RadialGradient(rect.width() - mShadowWidth,
                mShadowWidth,
                mShadowWidth,
                intArrayOf(startColor,
                        centerColor,
                        endColor),
                floatArrayOf(0f, 0.1f, 1f),
                CLAMP
        )
        canvas?.drawRect(RectF(rect.width() - mShadowWidth, 0f, rect.width(), mShadowWidth), paint)

        /**--------------------------------------左上直角------------------------------------------------**/

        paint.shader = RadialGradient(mShadowWidth,
                mShadowWidth,
                mShadowWidth,
                intArrayOf(startColor,
                        centerColor,
                        endColor),
                floatArrayOf(0f, 0.1f, 1f),
                CLAMP
        )
        canvas?.drawRect(RectF(0f, 0f, mShadowWidth, mShadowWidth), paint)

        /**-------------------------------------左下直角-------------------------------------------------**/

        paint.shader = RadialGradient(mShadowWidth,
                rect.height() - mShadowWidth,
                mShadowWidth,
                intArrayOf(startColor,
                        centerColor,
                        endColor),
                floatArrayOf(0f, 0.1f, 1f),
                CLAMP
        )
        canvas?.drawRect(RectF(0f, rect.height() - mShadowWidth, mShadowWidth, rect.height()), paint)
    }

    private fun generalCanvas(canvas: Canvas?) {
        /**---------------------------------------左-----------------------------------------------**/
        paint.shader = LinearGradient(mShadowWidth,
                0f,
                0f,
                0f,
                intArrayOf(startColor,
                        centerColor,
                        endColor),
                floatArrayOf(0f, 0.1f, 1f),
                CLAMP
        )
        if (mCornerRadiusEnable)
            canvas?.drawRect(RectF(0f, mCornerRadius, mShadowWidth, rect.height() - mCornerRadius), paint)
        else
            canvas?.drawRect(RectF(0f, mShadowWidth, mShadowWidth, rect.height() - mShadowWidth), paint)


        /**---------------------------------------右-----------------------------------------------**/
        paint.shader = LinearGradient(rect.width() - mShadowWidth,
                0f,
                rect.width(),
                0f,
                intArrayOf(startColor,
                        centerColor,
                        endColor),
                floatArrayOf(0f, 0.1f, 1f),
                CLAMP
        )
        if (mCornerRadiusEnable)
            canvas?.drawRect(RectF(rect.width() - mShadowWidth, mCornerRadius, rect.width(), rect.height() - mCornerRadius), paint)
        else
            canvas?.drawRect(RectF(rect.width() - mShadowWidth, mShadowWidth, rect.width(), rect.height() - mShadowWidth), paint)

        /**---------------------------------------上-----------------------------------------------**/
        paint.shader = LinearGradient(0f,
                mShadowWidth,
                0f,
                0f,
                intArrayOf(startColor,
                        centerColor,
                        endColor),
                floatArrayOf(0f, 0.1f, 1f),
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
                intArrayOf(startColor,
                        centerColor,
                        endColor),
                floatArrayOf(0f, 0.1f, 1f),
                CLAMP
        )
        if (mCornerRadiusEnable)
            canvas?.drawRect(RectF(mCornerRadius, rect.height() - mShadowWidth, rect.width() - mCornerRadius, rect.height()), paint)
        else
            canvas?.drawRect(RectF(mShadowWidth, rect.height() - mShadowWidth, rect.width() - mShadowWidth, rect.height()), paint)
    }

    private fun enableCorner(canvas: Canvas?) {
        /**--------------------------------------圆角公用-----------------------------------------**/
        paint.strokeWidth = mShadowWidth * 2
        paint.style = Paint.Style.STROKE
        /**--------------------------------------左上圆角------------------------------------------------**/
        paint.shader = RadialGradient(mCornerRadius,
                mCornerRadius,
                mCornerRadius,
                intArrayOf(startColor,
                        centerColor,
                        endColor),
                floatArrayOf(0f, 1 - mShadowWidth / mCornerRadius, 1f),
                CLAMP
        )
        canvas?.drawArc(RectF(0f, 0f, mCornerRadius * 2, mCornerRadius * 2), 180f, 90f, false, paint)

        /**--------------------------------------右上圆角------------------------------------------------**/
        paint.shader = RadialGradient(rect.width() - mCornerRadius,
                mCornerRadius,
                mCornerRadius,
                intArrayOf(startColor,
                        centerColor,
                        endColor),
                floatArrayOf(0f, 1 - mShadowWidth / mCornerRadius, 1f),
                CLAMP
        )
        canvas?.drawArc(RectF(rect.width() - mCornerRadius * 2, 0f, rect.width(), mCornerRadius * 2), 270f, 90f, false, paint)

        /**--------------------------------------左下圆角------------------------------------------------**/
        paint.shader = RadialGradient(mCornerRadius,
                rect.height() - mCornerRadius,
                mCornerRadius,
                intArrayOf(startColor,
                        centerColor,
                        endColor),
                floatArrayOf(0f, 1 - mShadowWidth / mCornerRadius, 1f),
                CLAMP
        )
        canvas?.drawArc(RectF(0f, rect.height() - mCornerRadius * 2, mCornerRadius * 2, rect.height()), 90f, 90f, false, paint)

        /**--------------------------------------右下圆角------------------------------------------------**/
        paint.shader = RadialGradient(rect.width() - mCornerRadius,
                rect.height() - mCornerRadius,
                mCornerRadius,
                intArrayOf(startColor,
                        centerColor,
                        endColor),
                floatArrayOf(0f, 1 - mShadowWidth / mCornerRadius, 1f),
                CLAMP
        )
        canvas?.drawArc(RectF(rect.width() - mCornerRadius * 2, rect.height() - mCornerRadius * 2, rect.width(), rect.height()), 0f, 90f, false, paint)
    }
}