package com.zed.example.base


/**
 * @author zd
 * @package com.zed.example.base
 * @fileName IBasePresenter
 * @date on 2017/12/1 0001 16:11
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
internal interface IBasePresenter<T : IBaseConstruction> {
    fun getView(): T?
    fun onDestory()
    fun init()
}
