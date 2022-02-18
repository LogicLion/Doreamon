package com.example.doreamon.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.doreamon.base.BaseViewModel
import com.example.doreamon.ext.toast
import com.example.doreamon.global.UserInfoData
import com.example.doreamon.net.DataRepository

/**
 * @author wzh
 * @date 2022/1/21
 */
class LoginViewModel : BaseViewModel() {
    val account = MutableLiveData("甘雨小可爱")

    val password = MutableLiveData("123456")

    /** 密码登录*/
    fun loginByPassword(onAction: () -> Unit = {}) =
        launchRequest {
            val account = account.value
            val pw = password.value
            if (account.isNullOrBlank()) {
                "请输入账号".toast()
                return@launchRequest
            } else if (pw.isNullOrBlank()) {
                "请输入密码".toast()
                return@launchRequest
            }
            val user = DataRepository.loginByPassword(account, pw)
//            val userInfo = DataRepository.userInfo(user.token).apply {
//                this.token = user.token
//            }
//            UserInfoData.value = userInfo

            "登录成功".toast()
            onAction.invoke()
        }
}