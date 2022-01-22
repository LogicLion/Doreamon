package com.example.doreamon.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doreamon.entity.AlertDialogModel
import com.example.doreamon.ext.handleNetException
import com.example.doreamon.ext.toast
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


    val alertDialogModel=MutableLiveData<AlertDialogModel>()

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.handleNetException().toast()
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

        e.handleNetException().toast()
    }


}