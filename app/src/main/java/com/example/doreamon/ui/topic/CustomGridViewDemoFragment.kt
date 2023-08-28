package com.example.doreamon.ui.topic

import com.doreamon.treasure.ext.toast
import com.example.doreamon.R
import com.example.doreamon.databinding.FragmentCustomGridViewDemoBinding
import com.example.doreamon.widget.CustomGridView
import com.example.module_base.base.BaseFragment
import com.example.module_base.base.BaseViewModel

/**
 * @author wzh
 * @date 2023/8/9
 */
class CustomGridViewDemoFragment : BaseFragment<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.fragment_custom_grid_view_demo

    override fun initView() {

        val binding = getViewBinding<FragmentCustomGridViewDemoBinding>()
        val textList = mutableListOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K")

        binding.nineGridView.setTextList(textList, 4, 4)

        binding.nineGridView.setGridListener(object : CustomGridView.OnGridListener {
            override fun onClick(position: Int) {
            }

            override fun onFinish() {
                "已完成".toast()
            }
        })

    }
}