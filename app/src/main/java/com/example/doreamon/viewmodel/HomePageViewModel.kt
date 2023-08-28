package com.example.doreamon.viewmodel

import com.example.module_base.base.BaseViewModel
import com.example.doreamon.net.DataRepository

/**
 * @author wzh
 * @date 2022/2/25
 */
class HomePageViewModel : com.example.module_base.base.BaseViewModel() {



    fun getArtcleList(pageNum: Int) = launchRequest {
        DataRepository.getHomepageArticleList(pageNum)
    }

}