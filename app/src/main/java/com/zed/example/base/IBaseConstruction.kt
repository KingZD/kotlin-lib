package com.zed.example.base

import android.app.Activity
import com.trello.rxlifecycle2.LifecycleTransformer
import java.util.*


/**
 * @author zd
 * @package com.zed.example.base
 * @fileName IBaseConstruction
 * @date on 2017/12/1 0001 16:13
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
interface IBaseConstruction {
    fun getActivity(): Activity?
    fun <T>getHttpLifeRecycle(): LifecycleTransformer<T>
}