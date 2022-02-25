package com.example.doreamon.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.doreamon.base.BaseViewModel
import com.example.doreamon.ext.toast
import com.example.doreamon.net.DataRepository

/**
 * @author wzh
 * @date 2022/2/23
 */
class RegisterViewModel : BaseViewModel() {

    val account = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordAgain = MutableLiveData<String>()

    fun register() = launchRequest {

        val account = account.value
        val pw = password.value
        val pwAgain = passwordAgain.value
        if (account.isNullOrBlank()) {
            "请输入账号".toast()
            return@launchRequest
        } else if (pw.isNullOrBlank()) {
            "请输入密码".toast()
            return@launchRequest
        } else if (pwAgain.isNullOrBlank()) {
            "请再次输入密码".toast()
            return@launchRequest
        }
        DataRepository.register(account, pw, pwAgain)
        "注册成功".toast()
    }
}