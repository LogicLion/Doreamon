package com.example.doreamon.ui.topic

import com.example.doreamon.R
import com.example.module_base.base.BaseFragment
import com.example.module_base.base.BaseViewModel
import com.example.doreamon.databinding.FragmentScrollViewStateBinding
import com.doreamon.treasure.ext.toast

/**
 * scrollview定制
 * @author wzh
 * @date 2022/11/23
 */
class ScrollViewStateFragment : com.example.module_base.base.BaseFragment<com.example.module_base.base.BaseViewModel>() {
    override fun setupLayoutId() = R.layout.fragment_scroll_view_state

    override fun initView() {
        val binding = getViewBinding<FragmentScrollViewStateBinding>()
        binding.scrollView.scrollBottom = {
            "滑动到底部".toast()
        }
    }
}