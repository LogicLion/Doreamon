package com.example.doreamon.ui.topic

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doreamon.R
import com.example.doreamon.base.BaseFragment
import com.example.doreamon.base.BaseViewModel
import com.example.doreamon.databinding.FragmentRecyclerviewDemoBinding
import com.example.doreamon.ui.simple.SimpleRecyclerAdapter

/**
 * @author wzh
 * @date 2023/2/1
 */
class RecyclerViewDemoFragment : BaseFragment<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.fragment_recyclerview_demo
    override fun initView() {

        val binding = getViewBinding<FragmentRecyclerviewDemoBinding>()
        binding.rv.adapter = SimpleRecyclerAdapter()
        binding.rv.layoutManager = LinearLayoutManager(requireActivity())

        //预拉取功能
        binding.rv.layoutManager?.isItemPrefetchEnabled = true


        binding.rv.setItemViewCacheSize(5)




    }
}