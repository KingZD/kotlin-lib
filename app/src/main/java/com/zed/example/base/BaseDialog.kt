package com.zed.example.base

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.support.annotation.LayoutRes
import android.view.*
import com.zed.common.util.ScreenUtils
import com.zed.example.R

/**
 * @author zd
 * @package com.zed.example.base
 * @fileName BaseDialog
 * @date on 2017/12/11 0011 15:09
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
abstract class BaseDialog : Dialog {
    internal var mView: View? = null
    internal var mContext: Context? = null

    fun getView(): View? {
        return mView
    }

    constructor(context: Context) : super(context, R.style.dialog) {
        this.mContext = context
    }

    constructor(context: Context, fromButton: Boolean) : super(context, R.style.Dialog_NoTitle) {
        if (fromButton) {
            fromBottom()
        }
        this.mContext = context
    }

    constructor(context: Context, themeResId: Int)
            : super(context, themeResId) {
        this.mContext = context
    }

    constructor(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener)
            : super(context, cancelable, cancelListener) {
        this.mContext = context
    }


    override fun setContentView(@LayoutRes resource: Int) {
        mView = LayoutInflater.from(mContext).inflate(resource, null)
        //设置SelectPicPopupWindow的View
        setContentView(mView)
        //设置全屏
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    fun fromBottom() {
        val localLayoutParams = window!!.attributes
        localLayoutParams.gravity = Gravity.BOTTOM or Gravity.LEFT
        localLayoutParams.width = ScreenUtils.getScreenWidth()
        localLayoutParams.y = 0
        localLayoutParams.x = 0
        onWindowAttributesChanged(localLayoutParams)
    }
}