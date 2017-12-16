package com.zed.example.net.model

import java.io.Serializable

/**
 * @author zd
 * @package com.zed.example.net.model
 * @fileName BaseModel
 * @date on 2017/12/1 0001 16:00
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
open class BaseModel : Serializable {
    constructor()
    constructor(type: Int?) {
        this.type = type
    }

    var type: Int? = 0
        set(value) {
            field = (value)
        }
}