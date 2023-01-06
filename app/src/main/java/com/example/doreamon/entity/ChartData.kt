package com.example.doreamon.entity

import java.io.Serializable

/**
 * @author wzh
 * @date 2022/3/24
 */
class ChartData(
    var x: String = "",
    var y: Int = 0,
    var yUnit: String? = "",
) : Serializable {

}