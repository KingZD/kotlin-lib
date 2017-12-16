package com.zed.example.base

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.PopupWindow

/**
 * @author zd
 * @package com.zed.example.popwindow
 * @fileName BasePopWIndow
 * @date on 2017/12/11 0011 15:02
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
abstract class BasePopWIndow : PopupWindow {
    internal var mWindowManager: WindowManager? = null

    internal var mView: ViewGroup? = null
    internal var mContext: Context? = null

    fun getView(): View? {
        return mView
    }

    fun getContext(): Context? {
        return mContext
    }

    constructor(context: Context) : super(context) {
        this.mContext = context
        this.mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    protected fun findViewById(@IdRes id: Int): View? {
        return mView?.findViewById(id)
    }

    protected fun setContentView(@LayoutRes resource: Int) {
        //制作容器
        mView = FrameLayout(mContext)
        //设置容器获得点击事件 否则容器下层视图会被触发
        mView?.isClickable = true
        mView?.isFocusable = true
        //装载视图
        val inflate = LayoutInflater.from(mContext).inflate(resource, null)
        mView?.addView(inflate)

        inflate.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        inflate.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        //设置SelectPicPopupWindow的View
        contentView = mView
        //        //设置SelectPicPopupWindow弹出窗体的宽
        this.width = ViewGroup.LayoutParams.MATCH_PARENT
        //设置SelectPicPopupWindow弹出窗体的高
        this.height = ViewGroup.LayoutParams.MATCH_PARENT
        //设置SelectPicPopupWindow弹出窗体可点击
        this.isFocusable = true
        //设置SelectPicPopupWindow弹出窗体动画效果
        //        this.setAnimationStyle(R.style.AnimBottom);
        val dw = if (allowBackgroundTranslucent()) {
            //实例化一个ColorDrawable颜色为半透明
            ColorDrawable(-0x70000000)
        } else {
            ColorDrawable(0x00000000)
        }
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw)
        //虚拟按键
        softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        mView?.setOnClickListener { dismiss() }
    }


    //true有背景色 false无背景色
    protected abstract fun allowBackgroundTranslucent(): Boolean
}