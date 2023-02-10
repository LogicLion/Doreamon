package com.doreamon.treasure.base

import android.app.Application
import android.content.Context

const val MODULE_META_KEY = "ApplicationDelegate"
/**
 * 组件化项目提供Application的抽象
 * 获取Application的实现步骤：
 * 1.新建的module下实现ApplicationDelegate接口的类
 * 2.在该module下的AndroidManifest下的Application节点新建一个meta-data，value是ApplicationDelegate，name是第一步骤实现的类的类名
 * 如
 * @author wzh
 * @date 2022/9/8
 */
interface ApplicationDelegate {

    fun attachBaseContext(application: Application, context: Context)
    fun onCreate(application: Application)
}