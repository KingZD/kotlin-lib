package com.bytc.qudong.interfaces

/**
 * @author zd
 * @package com.bytc.qudong.interfaces
 * @fileName OnLoadMoreListener
 * @date on 2017/12/6 0006 11:38
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
interface OnLoadMoreListener {
    /**
     * 加载更多的回调方法
     *
     * @param isReload 是否是重新加载，只有加载失败后，点击重新加载时为true
     */
    abstract fun onLoadMore(isReload: Boolean)
}