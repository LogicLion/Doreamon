package com.example.doreamon.ui.login

import android.os.Bundle
import com.example.doreamon.R
import com.example.module_base.base.BaseActivity
import com.example.doreamon.viewmodel.RegisterViewModel

/**
 * 注册
 * @author wzh
 * @date 2022/2/23
 */
class RegisterActivity : com.example.module_base.base.BaseActivity<RegisterViewModel>() {
    override fun setupLayoutId() = R.layout.activity_register

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}