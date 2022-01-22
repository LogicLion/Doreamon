package com.example.doreamon.base

import android.app.Application
import com.example.doreamon.utils.AppManager

/**
 * @author wzh
 * @date 2021/11/8
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        AppManager.register(this)
    }

    companion object {
        @JvmStatic
        lateinit var instance: App
            private set
    }

}