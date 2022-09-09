package com.example.doreamon.ui.simple

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 * @author wzh
 * @date 2022/7/19
 */
class TestService :Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        Log.v("TestService","服务启动")
    }


}