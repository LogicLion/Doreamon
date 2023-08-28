package com.example.doreamon.ui.topic

import com.example.module_base.base.BaseFragment
import com.example.module_base.base.BaseViewModel
import com.example.doreamon.R
import com.example.doreamon.databinding.FragmentExpandableTextViewDemoBinding

/**
 * @author wzh
 * @date 2023/3/16
 */
class ExpandableTextViewDemoFragment : com.example.module_base.base.BaseFragment<com.example.module_base.base.BaseViewModel>() {
    override fun setupLayoutId() = R.layout.fragment_expandable_text_view_demo

    override fun initView() {
        val binding = getViewBinding<FragmentExpandableTextViewDemoBinding>()
        val text =
            "哈哈会更好哈哈会更好哈哈会更好哈哈会更好哈哈会更好哈哈会更好哈哈会更好哈哈会更好哈哈会更好哈哈会更好哈哈会更好哈哈会更好哈哈会更好哈哈会更好哈哈会更好哈哈会更好哈哈会更好哈哈会更好哈哈会更好"
        binding.expandableText.setOriginalText(text)

        binding.tvExpandFold.setOnClickListener {
            binding.expandableText.switch()
        }
    }
}