package com.doreamon.treasure.entity

/**
 * 业务异常
 * @author wzh
 * @date 2021/12/28
 */
class BusinessException(
    val errCode: String? = null,
    val errMsg: String? = null
) : Exception()