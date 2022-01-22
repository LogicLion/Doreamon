package com.example.doreamon.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.doreamon.R
import com.example.doreamon.base.BaseActivity
import com.example.doreamon.databinding.ActivityLoginBinding
import com.example.doreamon.ext.startTargetActivity
import com.example.doreamon.global.UserInfoData
import com.example.doreamon.utils.AppManager
import com.example.doreamon.viewmodel.LoginViewModel

/**
 * @author wzh
 * @date 2022/1/21
 */
class LoginActivity : BaseActivity<LoginViewModel>() {
    override fun setupLayoutId() = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 关闭除登录页外所有界面
        AppManager.finishAllWithout(LoginActivity::class.java)

        //清除登录信息
        UserInfoData.value = null
        val viewModel = getViewModel()

        val binding = getViewBinding<ActivityLoginBinding>()
        binding.btnLogin.setOnClickListener {
            val account = binding.etAccount.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            viewModel.loginByPassword(account, password){
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    companion object {

        /**
         * 使用 [context] 打开 [LoginActivity] 界面
         * > 栈堆已有登录页时不会重复打开
         */
        fun actionStart(context: Context) {
            if (AppManager.contains(LoginActivity::class.java)) {
                // 堆栈中已有登录页，返回
                return
            }
            context.startTargetActivity<LoginActivity>()
        }
    }
}