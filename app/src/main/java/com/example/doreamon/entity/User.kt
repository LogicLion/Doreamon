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
    var admin: Boolean,
    var chapterTops: List<String>?,
    var coinCount: Int,
    var collectIds: List<String>?,
    var email: String?,
    var icon: String?,
    var id: String?,
    var nickname: String?,
    var password: String?,
    var publicName: String?,
    var token: String?,
    var type: Int,
    var username: String?

) : Serializable