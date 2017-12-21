package com.zed.example.base

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import com.jaeger.library.StatusBarUtil
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.android.ActivityEvent
import com.zed.common.util.UiUtil
import com.zed.example.R
import com.zed.example.control.activity.UIActivityConstraint
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.view_empty_re.*

/**
 * @author zd
 * @package com.zed.example.base
 * @fileName BaseActivity
 * @date on 2017/11/28 15:57
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */

abstract class BaseActivity : RxAppCompatActivity(), UIActivityConstraint {
    protected var TAG: String = javaClass.name
    private var fm: FragmentManager? = null
    private var mCurrentFragment: BaseFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置公用title
        setContentView(R.layout.activity_base)
        //设置监听
        appTitle.uiTitleController = this
        //添加主体布局
        val layoutId = setLayoutId()
        if (layoutId > 0)
            UiUtil.inflate(this, layoutId, flBody, true)
        //初始化布局
        initView()
    }

    fun setWarpBg(resId: Int) {
        ivWarpBg.setImageResource(resId)
    }

    fun setFillBg(resId: Int) {
        ivFillBg.setImageResource(resId)
    }

    //替换fragment
    fun repalce(fragment: BaseFragment?) {
        val layoutId = setLayoutId()
        val id = if (layoutId > 0) R.id.viewBody else R.id.flBody
        //?: 表达式就相当于一个简写的 if 语句。如果 ?: 左边的表达式不是 null，则返回左边的结果，否则返回 ?: 右边的表达式。
        fm = fm ?: supportFragmentManager
        val ft = fm?.beginTransaction()
        //ft?.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        if (!fragment!!.isAdded)
            if (mCurrentFragment == null)
                ft?.add(id, fragment)?.show(fragment)
            else
                ft?.add(id, fragment)?.hide(mCurrentFragment)?.show(fragment)
        else
            ft?.hide(mCurrentFragment)?.show(fragment)
        mCurrentFragment = fragment
        ft?.commit()
    }

    override fun getActivity(): BaseActivity? {
        return this
    }

    fun getCurrentFragment(): BaseFragment? {
        return mCurrentFragment
    }

    override fun clickTitleLeft() {
        finish()
    }

    override fun clickTitleRight() {
    }

    override fun clickTitle() {
    }

    override fun <T> getHttpLifeRecycle(): LifecycleTransformer<T> {
        return this.bindUntilEvent(ActivityEvent.STOP)
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    fun setStatusBarColor(color: Int) {
        fake_status_bar.setImageResource(color)
        fake_status_bar.visibility = View.VISIBLE
        StatusBarUtil.setTransparentForImageView(this, null)
    }

    //沉浸式通知栏
    fun setStatusBarImmersive() {
        hideStatusBar()
        StatusBarUtil.setTransparentForImageView(this, null)
    }

    //沉浸式通知栏
    fun setStatusBarImmersive(viewNeedOffset: View?) {
        hideStatusBar()
        StatusBarUtil.setTransparentForImageView(this, viewNeedOffset)
    }

    fun hideStatusBar() {
        fake_status_bar.visibility = View.GONE
    }

    fun setStatusBarImmersiveInCoordinatorLayout() {
        StatusBarUtil.setTranslucentForCoordinatorLayout(this, 0)
    }
}