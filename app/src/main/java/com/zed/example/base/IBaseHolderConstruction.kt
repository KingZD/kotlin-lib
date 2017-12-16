package com.zed.example.base

import com.zed.example.net.bean.BaseBean


/**
 * @author zd
 * @package com.zed.example.base
 * @fileName IBaseConstruction
 * @date on 2017/12/1 0001 16:13
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
interface IBaseHolderConstruction<T : BaseBean> {
    fun init(bean: T?)
}