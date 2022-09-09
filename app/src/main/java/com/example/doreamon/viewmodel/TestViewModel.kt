package com.example.doreamon.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.doreamon.base.BaseViewModel

/**
 * @author wzh
 * @date 2022/6/30
 */
class TestViewModel :BaseViewModel() {
    val userMessage= MutableLiveData<String>()
}