package com.example.doreamon.ui.topic

import com.doreamon.treasure.base.BaseFragment
import com.doreamon.treasure.base.BaseViewModel
import com.example.doreamon.R
import com.example.doreamon.adapter.ImageSimpleAdapter
import com.example.doreamon.databinding.FragmentLayoutManagerDemoBinding
import com.example.doreamon.widget.LikeLinearLayoutManager
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

        val adapter = ImageSimpleAdapter()

        val list = arrayListOf<String>()
        for (i in 0..8) {
            list.add("")
        }
        adapter.setList(list)
        binding.rv.layoutManager = LoopLayoutManager()
        binding.rv.adapter = adapter

        binding.tvJump.setOnClickListener {
            val text = binding.etTargetPosition.text.toString()
            if(text.isEmpty()) return@setOnClickListener
            binding.rv.smoothScrollToPosition(text.toInt())
        }

    }
}