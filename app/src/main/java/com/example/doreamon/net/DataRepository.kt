package com.example.doreamon.net

import com.example.doreamon.base.App
import com.example.doreamon.entity.User
import com.example.doreamon.ext.netRequest
import com.example.doreamon.ext.netRequestIgnoreData

/**
 * @author wzh
 * @date 2021/12/13
 */
object DataRepository {
    var context = App.instance
    private val netService: NetService
        get() {
            return RetrofitFactory.createRetrofit()
        }


    /**
     * 登录--密码登录
     */
    suspend fun loginByPassword(account: String, password: String) = netRequest {
        val map = HashMap<String, String?>()
        map["account"] = account
        map["passWord"] = password
        netService.loginClassroom("sagsdx", map)
    }


    /**
     * 短信验证码--登录
     */
    suspend fun sendSms4Login(phone: String) = netRequestIgnoreData {
        val map = HashMap<String, String>()
        map["phone"] = phone
        map["code"] = "LoginConfirmation"
        netService.sendSms(map)
    }

    /**
     * 用户信息
     */
    suspend fun userInfo(token: String) = netRequest {
        netService.userInfo(token)
    }


    /**
     * 短信验证码-修改密码
     */
    suspend fun sendSms4ResetPW(phone: String) = netRequestIgnoreData {
        val map = HashMap<String, String>()
        map["phone"] = phone
        map["code"] = "ChangePassword"
        netService.sendSms(map)
    }

    /**
     * 验证码登录
     */
    suspend fun loginByCode(phone: String, code: String): User =
        netRequest {
            val map = HashMap<String, String>()
            map["account"] = phone
            map["valCode"] = code

            netService.loginByCode(map)
        }


    /**
     * 密码修改
     */
    suspend fun resetPW(phone: String, code: String, newPassword: String, confirmPassword: String) =
        netRequestIgnoreData {
            val map = HashMap<String, String>()
            map["phone"] = phone
            map["valCode"] = code
            map["newPassword"] = newPassword
            map["studyPassword"] = confirmPassword
            netService.resetPW("sagsdx",map)
        }



}