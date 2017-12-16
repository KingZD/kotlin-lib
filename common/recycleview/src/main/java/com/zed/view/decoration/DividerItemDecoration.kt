package com.zed.view.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View

/**
 * @author zd
 * @package com.zed.view.decoration
 * @fileName DividerItemDecoration
 * @date on 2017/12/14 0014 09:26
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
class DividerItemDecoration : RecyclerView.ItemDecoration {
    /*
    * RecyclerView的布局方向，默认先赋值
    * 为纵向布局
    * RecyclerView 布局可横向，也可纵向
    * 横向和纵向对应的分割想画法不一样
    * */
    private var mOrientation = LinearLayoutManager.VERTICAL

    /**
     * item之间分割线的size，1---5
     */
    private var mSize: Int = 0

    /**
     * 绘制item分割线的画笔，和设置其属性
     * 来绘制个性分割线
     */
    private var mPaint: Paint

    /**
     * 构造方法传入布局方向，不可不传
     *
     * @param context context
     * @param orientation 布局方向
     * @param color 颜色
     * @param mItemSize item之间分割线的size
     */
    constructor(context: Context, orientation: Int, color: Int, mItemSize: Int) : super() {
        this.mOrientation = orientation
        /*
     item之间分割线的颜色
     */
        this.mSize = mItemSize
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw IllegalArgumentException("LinearLayoutManager error")
        }
        mSize = TypedValue.applyDimension(mItemSize, TypedValue.COMPLEX_UNIT_DIP.toFloat(),
                context.resources.displayMetrics).toInt()
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.color = color
        /*设置填充*/
        mPaint.style = Paint.Style.FILL
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    /**
     * 绘制纵向 item 分割线
     *
     * @param canvas canvas
     * @param parent parent
     */
    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.measuredWidth - parent.paddingRight
        val childSize = parent.childCount
        for (i in 0 until childSize) {
            val child = parent.getChildAt(i)
            val layoutParams = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + layoutParams.bottomMargin
            val bottom = top + mSize
            canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
        }
    }

    /**
     * 绘制横向 item 分割线
     *
     * @param canvas canvas
     * @param parent parent
     */
    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.measuredHeight - parent.paddingBottom
        val childSize = parent.childCount
        for (i in 0 until childSize) {
            val child = parent.getChildAt(i)
            val layoutParams = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + layoutParams.rightMargin
            val right = left + mSize
            canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
        }
    }

    /**
     * 设置item分割线的size
     *
     * @param outRect outRect
     * @param view view
     * @param parent parent
     * @param state state
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State?) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, mSize)
        } else {
            outRect.set(0, 0, mSize, 0)
        }
    }
}