package com.example.doreamon.ui.topic

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doreamon.R
import com.example.doreamon.adapter.LoopAdapter
import com.example.doreamon.base.BaseFragment
import com.example.doreamon.base.BaseViewModel
import com.example.doreamon.databinding.FragmentLoopRecyclerviewBinding

/**
 * @author wzh
 * @date 2022/5/6
 */
class LoopRecyclerViewFragment : BaseFragment<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.fragment_loop_recyclerview

    override fun initView() {

        val binding = getViewBinding<FragmentLoopRecyclerviewBinding>()
        binding.rv.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = LoopAdapter()
        binding.rv.adapter = adapter
        val phoneTypes = resources.getStringArray(android.R.array.phoneTypes).toMutableList()

        phoneTypes.addAll(phoneTypes)
        phoneTypes.addAll(phoneTypes)
        adapter.setList(phoneTypes)
    }
}