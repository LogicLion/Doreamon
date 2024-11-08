package com.example.doreamon.entity

import androidx.annotation.ColorInt
import java.io.Serializable

/**
 * @author wzh
 * @date 2024/11/7
 */
class PieData(
    var value: Int,

    @ColorInt
    var color: Int,
    var des: String
) : Serializable {

}