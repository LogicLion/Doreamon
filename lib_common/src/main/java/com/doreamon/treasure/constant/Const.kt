package com.example.doreamon.constant

import android.Manifest

/** http读取超时 */
const val HTTP_READ_TIMEOUT = 10000L

/** http连接超时 */
const val HTTP_CONNECT_TIMEOUT = 10000L


/** 微信appId */
const val WX_APPID = "wx5606f7d79ddb7abe"

/** 微信appSecret */
const val WX_APP_SECRET = "5a31e0e0a4d01dfbc67bf63596887532"


/** 友盟Appkey */
const val UMENG_APPKEY = "61de2cf6e0f9bb492bc8647b"

/** 域名 */
var BASE_URL ="https://www.wanandroid.com"

//权限
val PERMISSIONS_LIST_OTHER = arrayOf(
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.CAMERA,
    Manifest.permission.RECORD_AUDIO,
    Manifest.permission.MODIFY_AUDIO_SETTINGS,
    Manifest.permission.READ_PHONE_STATE
) //权限集合


