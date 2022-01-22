package com.example.doreamon.net

import java.io.Serializable

/**
 * 网络请求返回数据基本模型
 *
 * @param code 返回码
 * @param msg 返回信息
 * @param data 请求返回数据
 */
data class NetResult<T>(
    val code: String? = null,
    val msg: String? = "",
    val data: T? = null
):Serializable