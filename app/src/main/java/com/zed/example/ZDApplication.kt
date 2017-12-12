package com.zed.example

import android.support.multidex.MultiDexApplication
import com.zed.common.util.Utils

/**
 * @author zd
 * @package
 * @fileName com.zed.example.ZDApplication
 * @date on 2017/12/12 0012 10:57
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
class ZDApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
    }
}