package com.example.doreamon.global

import androidx.lifecycle.MutableLiveData
import com.example.doreamon.constant.DATA_CACHE_KEY_USER_INFO
import com.example.doreamon.entity.User
import com.example.doreamon.utils.getMMKV
import com.google.gson.Gson

/**
 * 用户信息
 * @author wzh
 * @date 2021/12/14
 */
object UserInfoData : MutableLiveData<User?>() {

    private var firstLoad = true

    /**
     * 判断当前登录状态
     */
    var isLogin: Boolean = false
        private set


    /**
     * 每当调用[MutableLiveData.observe]时,[onActive]会调用
     */
    override fun onActive() {
        if (!firstLoad) {
            return
        }

//        val userJson = getMMKV().decodeString(DATA_CACHE_KEY_USER_INFO)
//        value = Gson().fromJson(userJson, User::class.java)
        firstLoad = false
    }

    init {
        val userJson: String? = getMMKV().decodeString(DATA_CACHE_KEY_USER_INFO)
        value = Gson().fromJson(userJson, User::class.java)
        isLogin = value != null
    }


    override fun setValue(value: User?) {
        super.setValue(value)

        if (null == value) {
            //退出登录
            isLogin = false
            getMMKV().removeValueForKey(DATA_CACHE_KEY_USER_INFO)
        } else {
            getMMKV().encode(DATA_CACHE_KEY_USER_INFO, Gson().toJson(value, User::class.java))
            isLogin = true
        }
    }
}