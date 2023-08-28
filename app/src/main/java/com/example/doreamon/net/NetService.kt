package com.example.doreamon.net

import com.example.doreamon.entity.ArticleListEntity
import com.example.doreamon.entity.User
import com.example.module_base.entity.NetResult
import retrofit2.http.*

/**
 * @author wzh
 * @date 2021/12/13
 */
interface NetService {


    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(@Field("username") username: String,
                      @Field("password") password: String): NetResult<User>


    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(@Field("username") username: String,
                         @Field("password") password: String,
    @Field("repassword") repassword:String): NetResult<User>


    /**
     * 获取用户信息
     * @return
     */
    @GET("/user/lg/userinfo/json")
    suspend fun userInfo(@Header("token") token:String): NetResult<User>


    /** 根据页码[pageNum]获取并返回首页文章列表 */
    @GET("/article/list/{pageNum}/json")
    suspend fun getHomepageArticleList(@Path("pageNum") pageNum: Int): NetResult<ArticleListEntity>







}