package com.example.doreamon.base

import android.os.Bundle
import android.util.Log

/**
 * @author wzh
 * @date 2022/2/25
 */
abstract class BaseFragment<VM : BaseViewModel> :DataBindingFragment<VM>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.v(TAG,"初始化")
    }
}