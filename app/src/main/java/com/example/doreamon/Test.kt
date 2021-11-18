package com.example.doreamon

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
            val origin = "wanandroid"
            val md5Encode = AESUtil.encrypt("f74ebd4a388111ec", "a47d0242ac13016f","gdgsfd")
            println(md5Encode)
        }
    }
}