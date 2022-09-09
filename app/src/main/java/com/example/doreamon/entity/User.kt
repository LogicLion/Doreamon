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
    var admin: Boolean = false,
    var chapterTops: List<String>? = null,
    var coinCount: Int = 0,
    var collectIds: List<String>? = null,
    var email: String? = null,
    var icon: String? = null,
    var id: String? = null,
    var nickname: String? = null,
    var password: String? = null,
    var publicName: String? = null,
    var token: String? = null,
    var type: Int = 0,
    var username: String? = null

) : Serializable