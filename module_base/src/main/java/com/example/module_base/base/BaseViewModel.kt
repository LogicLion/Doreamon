package com.example.module_base.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doreamon.treasure.entity.AlertDialogModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @author wzh
 * @date 2021/12/9
 */
open class BaseViewModel : ViewModel() {

    protected val TAG = this.javaClass.simpleName

    /** 加载框显示控制 */
    val isLoading = MutableLiveData<Boolean>()


    /** 请求异常（服务器请求失败，譬如：服务器连接超时等） */
    val netException = MutableLiveData<Throwable>()


    val alertDialogModel = MutableLiveData<AlertDialogModel>()

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
//        e.handleNetException().toast()
        netException.value = throwable
    }

    inline fun launchRequestWithoutLoading(
        crossinline block: suspend CoroutineScope.() -> Unit,
    ) {
        viewModelScope.launch(exceptionHandler) {
            try {
                block.invoke(this)
            } catch (e: Exception) {
                defaultFailedBlock(e)
            } finally {
            }
        }
    }

    inline fun launchRequestWithoutLoading(
        crossinline block: suspend CoroutineScope.() -> Unit,
        crossinline onFail: (Exception) -> Unit = { defaultFailedBlock(it) },
        crossinline onComplete: () -> Unit = {}
    ) {
        viewModelScope.launch(exceptionHandler) {
            try {
                block.invoke(this)
            } catch (e: Exception) {
                onFail.invoke(e)
            } finally {
                onComplete.invoke()
            }
        }
    }


    inline fun launchRequest(
        crossinline block: suspend CoroutineScope.() -> Unit,
    ) {
        viewModelScope.launch(exceptionHandler) {
            try {
                isLoading.value = true
                block.invoke(this)
            } catch (e: Exception) {
                defaultFailedBlock(e)
            } finally {
                isLoading.value = false
            }
        }
    }

    /**
     * 默认异常处理
     */
    fun defaultFailedBlock(e: Exception) {

//        e.handleNetException().toast()
    }


}