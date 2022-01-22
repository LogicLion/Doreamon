package com.example.doreamon.ext

import android.view.Gravity
import android.widget.Toast
import com.example.doreamon.base.App

/**
 * @author wzh
 * @date 2021/12/28
 */
private var mToast: Toast? = null

/**
 * 弹出toast
 */
fun String?.toast(){
    if (isNullOrEmpty()) {
        return
    }
    mToast?.cancel()
    val context = App.instance
    mToast = Toast(context)
    mToast?.setText(this)
    mToast?.duration = Toast.LENGTH_SHORT
    mToast?.setGravity(Gravity.CENTER, 0, 0)
    mToast?.show()
}


