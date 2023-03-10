package com.example.doreamon.entity

import java.io.Serializable

/**
 * @author wzh
 */
class ChartData(
    var x: String = "",
    var y: Int = 0,
    var yUnit: String? = "",
    var xDes: String? = ""
) : Serializable {

}