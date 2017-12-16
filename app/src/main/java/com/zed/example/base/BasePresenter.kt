package com.zed.example.base

import android.app.Activity
import android.content.Context
import com.zed.example.adapter.BaseAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.zed.example.base.IBaseConstruction
import com.zed.example.base.IBasePresenter
import com.zed.example.net.bean.BaseBean
import com.zed.view.XRecyclerView

/**
 * @author zd
 * @package com.zed.example.base
 * @fileName IBasePresenter
 * @date on 2017/12/1 0001 16:11
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 * @param T : IBaseConstruction 定义布局约束类型 获取activity或者fragment 有用数据
 * @param M : BaseBean 定义当前布局处理的数据类型 以便简化数据处理和分页处理 ex: isClearOrAddDataByPullState
 * @param IBasePresenter 定义数据处理公约束
 */
abstract class BasePresenter<T : IBaseConstruction, M : BaseBean> : IBasePresenter<T>, OnLoadmoreListener, OnRefreshListener {
    protected val TAG: String = javaClass.name
    protected var mContext: Context? = null

    protected var mActivity: Activity? = null

    private var mView: T? = null

    //页码
    protected var mPage: Int = 1
    //每页数据
    protected var mPageSize: Int = 10
    //是否清空
    private var clear: Boolean = false

    constructor(view: T) {
        this.mView = view
        this.mView = view
        this.mActivity = view.getActivity()
        this.mContext = view.getActivity()
        init()
    }

    override fun getView(): T? {
        return mView
    }

    override fun onDestory() {
        mView = null
        mContext = null
        mActivity = null
    }

    override fun onLoadmore(refresh: RefreshLayout?) {
        clear = false
    }

    override fun onRefresh(refresh: RefreshLayout?) {
        mPage = 1
        clear = true
    }

    /**
     * 根据刷新状态来判断是否清空数据(分页)
     * @param data 原始数据源(adapter里面的数据)
     *
     */
    fun isClearOrAddDataByPullState(data: MutableList<M>) {
        isClearOrAddDataByPullState(data, null, null, null, null)
    }

    /**
     * 根据刷新状态来判断是否清空数据(分页)
     *
     * @param data 原始数据源(adapter里面的数据)
     * @param newData 每次加载的数据
     */
    fun isClearOrAddDataByPullState(data: MutableList<M>, newData: MutableList<M>?) {
        isClearOrAddDataByPullState(data, newData, null, null, null)
    }

    /**
     * 根据刷新状态来判断是否清空数据(分页)
     *
     * @param data 原始数据源(adapter里面的数据)
     * @param newData 每次加载的数据
     */
    fun isClearOrAddDataByPullState(recyclerView: XRecyclerView?, refresh: SmartRefreshLayout?, adapter: BaseAdapter<*, *>?) {
        isClearOrAddDataByPullState(null, null, recyclerView, refresh, adapter)
    }

    /**
     * 根据刷新状态来判断是否清空数据(分页)
     * @param data 原始数据源(adapter里面的数据)
     * @param newData 每次加载的数据
     * @param recyclerView
     * @param refresh 刷新组件
     * @param adapter 适配器 用来调用刷新数据
     */
    fun isClearOrAddDataByPullState(data: MutableList<M>?, newData: MutableList<M>?, recyclerView: XRecyclerView?, refresh: SmartRefreshLayout?, adapter: BaseAdapter<*, *>?) {
        if (data == null || newData == null) {
            refreshCompleteNoMore(refresh)
            return
        }
        val defaultStart = recyclerView?.headerViewSize!!.plus(data.size)
        if (clear) data.clear()
        if (newData.size > 0) {
            mPage++
            data.addAll(newData)
            adapter?.notifyItemRangeChanged(defaultStart, data.size)
            if (newData.size < mPageSize) {
                refreshCompleteNoMore(refresh)
                return
            }
        }
        refreshComplete(refresh)
    }

    private fun refreshComplete(refresh: SmartRefreshLayout?) {
        refresh?.finishRefresh()
        refresh?.finishLoadmore()
    }

    private fun refreshCompleteNoMore(refresh: SmartRefreshLayout?) {
        refresh?.finishRefresh()
        refresh?.finishLoadmoreWithNoMoreData()
    }

    protected fun getString(resId: Int): String? {
        return mContext?.getString(resId)
    }
}