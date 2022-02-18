package com.example.doreamon.ext

import com.example.doreamon.entity.BusinessException
import com.example.doreamon.net.NetResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 协程相关
 */

/**
 * 在 [Dispatchers.IO] 线程执行 [block] 进行网络请求
 * 预处理返回结果
 */
suspend fun <T> netRequest(block: suspend CoroutineScope.() -> NetResult<T>): T {
    val result = withContext(Dispatchers.IO) {
        block.invoke(this)
    }

    var code = result.errorCode
    val data = result.data
    val msg = result.errorMsg
    if (data != null && "0" == code) {
        return data
    } else {
        throw BusinessException(code, msg)
    }
}

/**
 * 在 [Dispatchers.IO] 线程执行 [block] 进行网络请求
 * 返回结果只关注请求码，不关注请求数据
 */
suspend fun <T> netRequestIgnoreData(block: suspend CoroutineScope.() -> NetResult<T>) {
    val result = withContext(Dispatchers.IO) {
        block.invoke(this)
    }

    val code = result.errorCode
    val msg = result.errorMsg

    if ("0" != code) {
        throw BusinessException(code, msg)
    }
}
