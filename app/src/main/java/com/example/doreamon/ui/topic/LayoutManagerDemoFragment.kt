package com.example.doreamon.ui.topic

import com.doreamon.treasure.base.BaseFragment
import com.doreamon.treasure.base.BaseViewModel
import com.example.doreamon.R
import com.example.doreamon.adapter.ImageSimpleAdapter
import com.example.doreamon.databinding.FragmentLayoutManagerDemoBinding
import com.example.doreamon.widget.LoopLayoutManager

/**
 * 自定义layoutManager
 * @author wzh
 * @date 2023/2/10
 */
class LayoutManagerDemoFragment : BaseFragment<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.fragment_layout_manager_demo

    override fun initView() {

        val binding = getViewBinding<FragmentLayoutManagerDemoBinding>()
        binding.rv.layoutManager = LoopLayoutManager()
        binding.rv.adapter = ImageSimpleAdapter()
    }
}