package com.example.doreamon.ui.topic

import com.example.doreamon.R
import com.example.doreamon.base.BaseFragment
import com.example.doreamon.base.BaseViewModel
import com.example.doreamon.databinding.FragmentScrollViewStateBinding
import com.example.doreamon.ext.toast

/**
 * scrollview定制
 * @author wzh
 * @date 2022/11/23
 */
class ScrollViewStateFragment : BaseFragment<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.fragment_scroll_view_state

    override fun initView() {
        val binding = getViewBinding<FragmentScrollViewStateBinding>()
        binding.scrollView.scrollBottom = {
            "滑动到底部".toast()
        }
    }
}