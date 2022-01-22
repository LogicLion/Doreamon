package com.example.doreamon.net

import com.example.doreamon.entity.User
import retrofit2.http.*

/**
 * @author wzh
 * @date 2021/12/13
 */
interface NetService {


    /**
     * 登录课堂
     * @return
     */
    @POST("/student/fzdStudent/loginByPhoneOrStudentCode")
    suspend fun loginClassroom(@Header("ntk") ntk:String,@Body params: Map<String, String?>): NetResult<User>


    /**
     * 获取用户信息（动态带token）
     * @return
     */
    @GET("/student/fzdStudent/info")
    suspend fun userInfo(@Header("token") token:String): NetResult<User>


    /**
     *发送短信验证码
     */
    @POST("/student/sms/sendSms")
    suspend fun sendSms(@Body params: Map<String, String>): NetResult<Any>


    /**
     *验证码登录
     */
    @POST("/student/fzdStudent/loginBySms")
    suspend fun loginByCode(@Body params: Map<String, String>): NetResult<User>

    /**
     * 修改登录密码
     */
    @POST("/student/fzdStudent/updatePassword")
    suspend fun resetPW(@Header("ntk") ntk:String,@Body params: Map<String, String>): NetResult<Any>




}