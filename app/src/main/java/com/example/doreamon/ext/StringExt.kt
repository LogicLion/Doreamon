package com.example.doreamon.ext

import android.widget.Toast
import com.example.doreamon.base.AppI

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
    val context = AppI.instance
    mToast = Toast.makeText(context,this,Toast.LENGTH_SHORT)
    mToast?.show()
}


