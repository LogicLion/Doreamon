package com.example.doreamon.entity

import java.io.Serializable

/**
 * @author wzh
 * @date 2022/1/20
 */
/**
 * 登录用户信息
 * @author wzh
 * @date 2021/12/13
 */
data class User(
    /**用户id*/
    val id: String? = null,
    var avatar: String? = null,
    var name: String? = null,
    var studentCode: String? = null,
    var token: String = ""
) : Serializable