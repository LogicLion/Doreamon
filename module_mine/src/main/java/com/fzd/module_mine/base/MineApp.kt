package com.fzd.module_mine.base

import android.app.Application
import android.content.Context
import com.doreamon.treasure.base.ApplicationDelegate
import com.fzd.module_mine.test.KoinTest
import com.fzd.module_mine.test.SingletonTest
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

/**
 * @author wzh
 * @date 2023/3/14
 */
class MineApp : ApplicationDelegate {
    val TAG = "MineApp"
    override fun attachBaseContext(application: Application, context: Context) {
        instance = application
    }

    override fun onCreate(application: Application) {

        //初始化koin
        startKoin {
            androidLogger()
            androidContext(application)
            modules(moduleList)
        }

    }

    companion object {
        @JvmStatic
        lateinit var instance: Application
            private set
    }


    // normalMoudle就是来管理常规的对象注入
    val normalModule = module {

        factory { KoinTest() }
    }

    // singleModule则是用来单例对象注入
    val singleModule = module {

        single { SingletonTest() }
    }
    val moduleList = listOf(normalModule, singleModule)

}