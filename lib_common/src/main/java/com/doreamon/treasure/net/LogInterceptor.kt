package com.doreamon.treasure.net

import android.util.Log
import com.doreamon.treasure.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

/**
 * okhttp 日志拦截器
 * @author LTP  2022/3/21
 */
val logInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        // 使用自己的日志工具接管
        Log.d("Http",message)
    }
}).setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC)