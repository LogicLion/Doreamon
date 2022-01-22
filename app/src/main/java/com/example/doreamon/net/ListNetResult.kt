package com.example.doreamon.net

import java.io.Serializable

/**
 * @author wzh
 * @date 2021/12/15
 */
data class ListNetResult<T>(
    var count: Int = 0,
    var rows: List<T>? = null
) : Serializable