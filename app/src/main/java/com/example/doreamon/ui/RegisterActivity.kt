package com.example.doreamon.ui

import android.os.Bundle
import com.example.doreamon.R
import com.example.doreamon.base.BaseActivity
import com.example.doreamon.viewmodel.RegisterViewModel

/**
 * 注册
 * @author wzh
 * @date 2022/2/23
 */
class RegisterActivity : BaseActivity<RegisterViewModel>() {
    override fun setupLayoutId() = R.layout.activity_register

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}