package com.example.doreamon.ui.topic

import com.example.module_base.base.BaseFragment
import com.example.module_base.base.BaseViewModel
import com.example.doreamon.R
import com.example.doreamon.adapter.ImageSimpleAdapter
import com.example.doreamon.databinding.FragmentLayoutManagerDemoBinding
import com.example.doreamon.widget.LikeLinearLayoutManager

/**
 * 自定义layoutManager
 * @author wzh
 * @date 2023/2/10
 */
class LayoutManagerDemoFragment : com.example.module_base.base.BaseFragment<com.example.module_base.base.BaseViewModel>() {

    override fun setupLayoutId() = R.layout.fragment_layout_manager_demo

    override fun initView() {

        val binding = getViewBinding<FragmentLayoutManagerDemoBinding>()

        val adapter = ImageSimpleAdapter()

        val list = arrayListOf<String>()
        for (i in 0..8) {
            list.add("")
        }
        adapter.setList(list)
        binding.rv.layoutManager = LikeLinearLayoutManager()
        binding.rv.adapter = adapter

        binding.tvJump.setOnClickListener {
            val text = binding.etTargetPosition.text.toString()
            if (text.isEmpty()) return@setOnClickListener
            binding.rv.smoothScrollToPosition(text.toInt())
        }


    }


}