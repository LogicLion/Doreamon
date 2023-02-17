package com.example.doreamon.ui.topic

import com.doreamon.treasure.base.BaseFragment
import com.doreamon.treasure.base.BaseViewModel
import com.example.doreamon.R

/**
 * 自定义layoutManager
 * @author wzh
 * @date 2023/2/10
 */
class LayoutManagerDemoFragment :BaseFragment<BaseViewModel>() {
    override fun setupLayoutId()= R.layout.fragment_layout_manager_demo

    override fun initView() {
    }
}