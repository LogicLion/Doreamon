package com.doreamon.treasure.net

import java.io.Serializable

/**
 * 网络请求返回数据基本模型
 *
 * @param errorCode 返回码
 * @param errorMsg 返回信息
 * @param data 请求返回数据
 */
data class NetResult<T>(
    val errorCode: String? = null,
    val errorMsg: String? = "",
    val data: T? = null
):Serializable