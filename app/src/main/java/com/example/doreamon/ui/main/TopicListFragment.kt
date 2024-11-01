package com.example.doreamon.ui.main

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.module_base.base.BaseFragment
import com.example.module_base.base.BaseViewModel
import com.example.doreamon.R
import com.example.doreamon.StudyTopic
import com.example.doreamon.adapter.TopicAdapter
import com.example.doreamon.databinding.CommonRecyclerviewBinding

/**
 * 专题列表
 * @author wzh
 * @date 2022/5/9
 */
class TopicListFragment : BaseFragment<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.common_recyclerview

    override fun initView() {
        val binding = getViewBinding<CommonRecyclerviewBinding>()
        val rv = binding.rv
        rv.layoutManager = LinearLayoutManager(requireActivity())
        val topicList = StudyTopic.List
        val topicAdapter = TopicAdapter()
        topicAdapter.setList(topicList)
        rv.adapter = topicAdapter


    }

}