package com.fzd.module_mine.base

import android.app.Application
import android.content.Context
import com.doreamon.treasure.base.ApplicationDelegate

/**
 * @author wzh
 * @date 2023/3/14
 */
class MineApp : ApplicationDelegate {
    override fun attachBaseContext(application: Application, context: Context) {
        instance = application
    }

    override fun onCreate(application: Application) {

    }

    companion object {
        @JvmStatic
        lateinit var instance: Application
            private set
    }
}