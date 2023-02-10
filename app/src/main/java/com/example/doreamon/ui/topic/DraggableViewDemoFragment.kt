package com.example.doreamon.ui.topic

import com.example.doreamon.R
import com.doreamon.treasure.base.BaseFragment
import com.doreamon.treasure.base.BaseViewModel
import com.example.doreamon.databinding.FragmentDraggableViewDemoBinding
import com.example.doreamon.ui.simple.SimpleAdapter

/**
 * @author wzh
 * @date 2022/10/25
 */
class DraggableViewDemoFragment : BaseFragment<BaseViewModel>() {

    override fun setupLayoutId() = R.layout.fragment_draggable_view_demo

    override fun initView() {

        val binding = getViewBinding<FragmentDraggableViewDemoBinding>()

        val list = arrayListOf<String>()
        for (i in 0..30) {
            list.add("示例")
        }
        val adapter = SimpleAdapter()
        adapter.setList(list)
        binding.rvSimple.adapter = adapter
    }
}