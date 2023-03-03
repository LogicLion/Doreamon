package com.example.doreamon.ui.topic

import com.example.doreamon.R
import com.doreamon.treasure.base.BaseFragment
import com.doreamon.treasure.base.BaseViewModel
import com.example.doreamon.databinding.FragmentScratchViewBinding

/**
 * 刮刮卡效果
 * @author wzh
 * @date 2022/4/26
 */
class ScratchViewDemoFragment : BaseFragment<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.fragment_scratch_view

    override fun initView() {

        val binding = getViewBinding<FragmentScratchViewBinding>()
        binding.tvRollback.setOnClickListener {
            binding.iv.rollback()
        }
    }
}