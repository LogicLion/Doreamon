package com.example.doreamon.viewmodel

import com.example.doreamon.base.BaseViewModel
import com.example.doreamon.ext.toast
import com.example.doreamon.global.UserInfoData
import com.example.doreamon.net.DataRepository

/**
 * @author wzh
 * @date 2022/1/21
 */
class LoginViewModel :BaseViewModel() {

    /** 密码登录*/
    fun loginByPassword(phone: String, password: String, onAction: () -> Unit = {}) =
        launchRequest {
            if (phone.isEmpty()) {
                "请输入账号".toast()
                return@launchRequest
            } else if (password.isEmpty()) {
                "请输入密码".toast()
                return@launchRequest
            }
            val user = DataRepository.loginByPassword(phone, password)
            val userInfo = DataRepository.userInfo(user.token).apply {
                this.token = user.token
            }
            UserInfoData.value = userInfo

            "登录成功".toast()
            onAction.invoke()
        }
}