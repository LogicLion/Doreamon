package com.example.doreamon.ui.topic

import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ScreenUtils
import com.example.doreamon.R
import com.doreamon.treasure.base.BaseFragment
import com.doreamon.treasure.base.BaseViewModel
import com.example.doreamon.databinding.FragmentRecyclerviewDemoBinding
import com.example.doreamon.entity.ChatMessage
import com.example.doreamon.ui.simple.TestDemoAdapter

/**
 * @author wzh
 * @date 2023/2/1
 */
class RecyclerViewDemoFragment : BaseFragment<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.fragment_recyclerview_demo
    override fun initView() {

        val binding = getViewBinding<FragmentRecyclerviewDemoBinding>()

        val adapter = TestDemoAdapter()

        val list = arrayListOf<ChatMessage>()
        val aa = "回复内容"
        for (i in 0..10) {
            var msg = StringBuffer().append(aa)
            for (j in 0..i * 20) {
                msg.append(j)
            }
            list.add(ChatMessage(true, 0, msg.toString()))
        }

        adapter.setList(list)
//        adapter.setList(
//            arrayListOf(
//                ChatMessage(),
//                ChatMessage(false),
//                ChatMessage(),
//                ChatMessage(),
//                ChatMessage(false),
//                ChatMessage(),
//                ChatMessage(false, 0, "是读后感"),
//                ChatMessage(),
//                ChatMessage()
//            )
//        )
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(requireActivity())

        //预拉取功能
        binding.rv.layoutManager?.isItemPrefetchEnabled = true


        binding.rv.setItemViewCacheSize(5)

        ScreenUtils.getScreenWidth();

    }
}