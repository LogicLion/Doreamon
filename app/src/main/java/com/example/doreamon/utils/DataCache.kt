package com.example.doreamon.utils

import com.example.doreamon.base.App
import com.tencent.mmkv.MMKV

/**
 * @author wzh
 * @date 2021/12/14
 */
/** 默认 [MMKV] 对象 */
private val defaultMMKV: MMKV by lazy {
    MMKV.initialize(App.instance)
    MMKV.defaultMMKV()!!
}

fun getMMKV(): MMKV {
    return defaultMMKV
}