package com.example.doreamon

import com.example.doreamon.entity.User
import com.example.doreamon.utils.AESUtil
import com.example.doreamon.utils.MD5Util

/**
 * @author wzh
 * @date 2021/11/18
 */
class Test {
    companion object{
        @JvmStatic
        fun main(args: Array<String>) {
            println(User().id)
        }
    }
}