package com.example.doreamon.viewmodel

import com.doreamon.treasure.base.BaseViewModel
import com.example.doreamon.net.DataRepository

/**
 * @author wzh
 * @date 2022/2/25
 */
class HomePageViewModel : BaseViewModel() {



    fun getArtcleList(pageNum: Int) = launchRequest {
        DataRepository.getHomepageArticleList(pageNum)
    }

}