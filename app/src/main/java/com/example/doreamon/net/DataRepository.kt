package com.example.doreamon.net

import com.example.doreamon.base.App
import com.example.doreamon.ext.netRequest

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
        netService.login(account,password)
    }


    /**
     * 注册
     */
    suspend fun register(account: String, password: String,passwordAgain:String) = netRequest {
        val map = HashMap<String, String?>()
        netService.register(account,password,passwordAgain)
    }



    /**
     * 用户信息
     */
    suspend fun userInfo(token: String) = netRequest {
        netService.userInfo(token)
    }

    suspend fun getHomepageArticleList(pageNum: Int) = netRequest{
        netService.getHomepageArticleList(pageNum)
    }




}