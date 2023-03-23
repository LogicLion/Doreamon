package com.example.doreamon.ui.topic

import com.doreamon.treasure.base.BaseFragment
import com.doreamon.treasure.base.BaseViewModel
import com.example.doreamon.R
import com.example.doreamon.base.AppI
import com.example.doreamon.databinding.FragmentExpandableTextViewDemoBinding
import com.fzd.module_mine.utils.DisplayUtil

/**
 * @author wzh
 * @date 2023/3/16
 */
class ExpandableTextViewDemoFragment : BaseFragment<BaseViewModel>() {
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