package com.zed.example.control.activity

import com.zed.example.base.IBaseConstruction


/**
 * @author zd
 * @package com.zed.example.base
 * @fileName UIViewConstraint
 * @date on 2017/11/29 0029 17:34
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
interface UIActivityConstraint : IBaseConstruction {
    //返回主要视图
    fun setLayoutId(): Int

    //返回主要视图
    fun initView()

    //点击标题
    fun clickTitleLeft()

    fun clickTitleRight()
    fun clickTitle()
}