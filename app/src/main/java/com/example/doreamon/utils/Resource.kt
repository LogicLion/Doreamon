package com.example.doreamon.utils

import android.content.Context
import androidx.annotation.StringRes
import com.example.doreamon.base.App

/**
 * @author wzh
 * @date 2021/12/27
 */
/**
 * 根据资源id[resId] 获取字符串[String]
 */
@JvmOverloads
fun getStringById(@StringRes resId: Int, context: Context = App.instance): String {
    return context.getString(resId)
}