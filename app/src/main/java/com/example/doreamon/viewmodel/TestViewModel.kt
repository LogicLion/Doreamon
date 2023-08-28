package com.example.doreamon.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.module_base.base.BaseViewModel

/**
 * @author wzh
 * @date 2022/6/30
 */
class TestViewModel : com.example.module_base.base.BaseViewModel() {
    val userMessage= MutableLiveData<String>()
}