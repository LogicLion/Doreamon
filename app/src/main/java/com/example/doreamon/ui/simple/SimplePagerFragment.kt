package com.example.doreamon.ui.simple

import android.os.Bundle
import com.example.doreamon.R
import com.example.doreamon.databinding.FragmentSimplePagerBinding
import com.example.module_base.base.BaseFragment
import com.example.module_base.base.BaseViewModel

/**
 * @author wzh
 * @date 2023/12/7
 */
class SimplePagerFragment : BaseFragment<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.fragment_simple_pager

    override fun initView() {
        val title = arguments?.getString("title")
        val binding = getViewBinding<FragmentSimplePagerBinding>()
        binding.tvTitle.text = title
    }

    companion object {
        fun newInstance(title: String) =
            SimplePagerFragment().apply {
                arguments = Bundle().apply {
                    putString("title", title)
                }
            }
    }
}