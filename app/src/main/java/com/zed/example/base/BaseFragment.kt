package com.zed.example.base

import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.trello.rxlifecycle2.components.support.RxFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jaeger.library.StatusBarUtil
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.FragmentEvent
import com.zed.common.util.UiUtil
import com.zed.example.control.activity.UIActivityConstraint
import com.zed.example.net.bean.BaseBean
import java.util.*

/**
 * @author zd
 * @package com.zed.example.base
 * @fileName BaseFragment
 * @date on 2017/12/1 0001 11:46
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
abstract class BaseFragment<in T : BasePresenter<*, *>> : RxFragment(), UIActivityConstraint {
    private var presenter: T? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UiUtil.inflate(context, setLayoutId(), container, false)
    }

    fun setPresenter(presenter: T) {
        this.presenter = presenter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun clickTitleLeft() {
    }

    override fun clickTitleRight() {
    }

    override fun clickTitle() {
    }

    override fun <T> getHttpLifeRecycle(): LifecycleTransformer<T> {
        return this.bindUntilEvent(FragmentEvent.STOP)
    }


    fun setStatusBarColor(color: Int) {
        StatusBarUtil.setColorNoTranslucent(
                activity, ContextCompat.getColor(context!!, color))
    }

    //沉浸式通知栏
    fun setStatusBarImmersive(viewNeedOffset: View?) {
        StatusBarUtil.setTransparentForImageView(activity, viewNeedOffset)
    }

    fun setStatusBarImmersiveInCoordinatorLayout() {
        StatusBarUtil.setTranslucentForCoordinatorLayout(activity, 0)
    }

    override fun onDestroyView() {
        presenter?.onDestory()
        presenter = null
        super.onDestroyView()
    }
}