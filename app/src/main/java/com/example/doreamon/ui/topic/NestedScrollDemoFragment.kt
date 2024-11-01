package com.example.doreamon.ui.topic

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.example.doreamon.R
import com.example.doreamon.databinding.FragmentNestScrollDemoBinding
import com.example.module_base.base.BaseFragment
import com.example.module_base.base.BaseViewModel

/**
 * @author wzh
 * @date 2024/7/17
 */
class NestedScrollDemoFragment : BaseFragment<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.fragment_nest_scroll_demo

    override fun initView() {
        val binding = getViewBinding<FragmentNestScrollDemoBinding>()
        binding.tvContent.text =
            "文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n文本内容\n" +
                    "文本内容\n" +
                    "文本内容\n" +
                    "文本内容\n" +
                    "文本内容\n" +
                    "文本内容\n" +
                    "文本内容\n" +
                    "文本内容\n" +
                    "文本内容\n" +
                    "文本内容\n" +
                    "文本内容\n" +
                    "文本内容\n" +
                    "文本内容\n" +
                    "文本内容\n" +
                    "文本内容\n" +
                    "文本内容\n" +
                    "文本内容\n" +
                    "文本内容\n" +
                    "文本内容\n" +
                    "文本内容\n" +
                    "文本内容\n" +
                    "文本内容\n" +
                    "文本内容\n"

    }
}