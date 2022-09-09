package com.example.doreamon.base

import android.app.Application
import android.content.Context
import com.doreamon.treasure.base.ApplicationDelegate

/**
 * @author wzh
 * @date 2022/9/8
 */
internal class AppI : ApplicationDelegate {
    override fun attachBaseContext(application: Application, context: Context) {
    }

    override fun onCreate(application: Application) {
        instance = application

    }


    companion object {
        @JvmStatic
        lateinit var instance: Application
            private set
    }
}