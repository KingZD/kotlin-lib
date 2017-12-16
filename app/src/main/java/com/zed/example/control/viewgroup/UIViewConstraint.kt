package com.zed.example.control.viewgroup

/**
 * @author zd
 * @package com.zed.example.base
 * @fileName UIViewConstraint
 * @date on 2017/11/29 0029 17:34
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
interface UIViewConstraint<T> {
    fun setLayoutId()
    fun initView()
    fun updateUI(data: T)
    fun onDestory()
}