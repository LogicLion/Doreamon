package com.example.doreamon.ext

import android.util.Log
import com.example.doreamon.R
import com.example.doreamon.base.BaseActivity
import com.example.doreamon.entity.AlertDialogModel
import com.example.doreamon.entity.BusinessException
import com.example.doreamon.global.UserInfoData
import com.example.doreamon.ui.login.LoginActivity
import com.example.doreamon.utils.AppManager
import com.example.doreamon.utils.getStringById
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.io.EOFException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketException
import java.net.UnknownHostException
import java.text.ParseException
import kotlin.coroutines.cancellation.CancellationException

/**
 * 网络异常处理
 * @author wzh
 * @date 2021/12/27
 */
fun Throwable.handleNetException():String? {
    printStackTrace()
    Log.e("网络异常", message ?: "")
    return when (this) {
        is BusinessException -> {
            if (errCode == "1000") {
                (AppManager.peekActivity() as? BaseActivity<*>)?.run {
                    if (UserInfoData.isLogin) {
                        UserInfoData.value = null
                        getViewModel().alertDialogModel.value = AlertDialogModel(
                            title = "登录失效",
                            content = "当前登录状态已失效，请重新登录",
                            actionText = "重新登录",
                            cancelBtnGone = true,
                            onAction = {
                                // 登录失败，需要重新登录
                                LoginActivity.actionStart(this)
                            }
                        )
                    }
                    return null
                }

            }else if(errCode == "1001"){
                (AppManager.peekActivity() as? BaseActivity<*>)?.run {
                    if (UserInfoData.isLogin) {
                        UserInfoData.value = null
                        getViewModel().alertDialogModel.value = AlertDialogModel(
                            title = "下线通知",
                            content = "您的账号已在别的手机登录，如非本人操作，则密码可能已泄漏，建议立即修改密码",
                            actionText = "重新登录",
                            cancelBtnGone = true,
                            onAction = {
                                // 登录失败，需要重新登录
                                LoginActivity.actionStart(this)
                            }
                        )
                    }
                    return null
                }
            }else{
                if (errMsg.isNullOrBlank()) {
                    "服务器错误"
                } else {
                    errMsg
                }
            }

        }
        is HttpException -> getStringById(R.string.bad_network)
        is ConnectException -> getStringById(R.string.connect_error)
        is UnknownHostException -> getStringById(R.string.connect_error)
        is InterruptedIOException -> getStringById(R.string.connect_timeout)
        is JsonParseException -> getStringById(R.string.parse_error)
        is JSONException -> getStringById(R.string.parse_error)
        is ParseException -> getStringById(R.string.parse_error)
        is EOFException -> getStringById(R.string.connect_error)
        is SocketException -> getStringById(R.string.bad_network)

        //协程取消
        is CancellationException -> ""
        //其他错误
        else -> getStringById(R.string.unknown_error)

    }
}
