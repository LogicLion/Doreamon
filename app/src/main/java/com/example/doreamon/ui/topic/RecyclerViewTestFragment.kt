package com.example.doreamon.ui.topic

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doreamon.R
import com.example.doreamon.databinding.FragmentRecycerviewTestBinding
import com.example.doreamon.ui.simple.SimpleAdapter
import com.example.module_base.base.BaseFragment
import com.example.module_base.base.BaseViewModel

/**
 * @author wzh
 * @date 2024/7/9
 */
class RecyclerViewTestFragment : BaseFragment<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.fragment_recycerview_test

    override fun initView() {
        val binding = getViewBinding<FragmentRecycerviewTestBinding>()

        val adapter = SimpleAdapter()
        adapter.setList(
            arrayListOf(
                "测试"
            )
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.adapter = adapter
    }
}