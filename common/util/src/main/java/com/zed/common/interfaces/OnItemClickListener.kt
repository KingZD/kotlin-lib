package com.bytc.qudong.interfaces



/**
 * @author zd
 * @package com.bytc.qudong.interfaces
 * @fileName OnItemClickListener
 * @date on 2017/12/6 0006 11:20
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
interface OnItemClickListener<T,M> {
    fun onItemClick(viewHolder: T, data: M?, position: Int)
}