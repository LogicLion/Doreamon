package com.doreamon.treasure.net

import com.example.doreamon.constant.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.internal.cache.CacheInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit管理类
 *
 */
object RetrofitManager {
    /** 请求超时时间 */
    private const val TIME_OUT_SECONDS = 10

    /** 请求cookie */
//    val cookieJar: PersistentCookieJar by lazy {
//        PersistentCookieJar(
//            SetCookieCache(),
//            SharedPrefsCookiePersistor(appContext)
//        )
//    }

    /** 请求根地址 */
//    val BASE_URL = IpManager.getDefaultIP()

    /** OkHttpClient相关配置 */
    private val client: OkHttpClient
        get() = OkHttpClient.Builder()
            // 请求过滤器
            .addInterceptor(logInterceptor)
            //设置缓存配置,缓存最大10M,设置了缓存之后可缓存请求的数据到data/data/包名/cache/net_cache目录中
//            .cache(Cache(File(appContext.cacheDir, "net_cache"), 10 * 1024 * 1024))
            //添加缓存拦截器 可传入缓存天数
//            .addInterceptor(CacheInterceptor(30))
            // 请求超时时间
            .connectTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
//            .cookieJar(cookieJar)
            .build()

    /**
     * Retrofit相关配置
     */
    fun <T> getService(serviceClass: Class<T>, baseUrl: String? = null): T {
//        LogUtil.d(BASE_URL)
        return Retrofit.Builder()
            .client(client)
            // 使用Moshi更适合Kotlin
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl ?: BASE_URL)
            .build()
            .create(serviceClass)
    }
}