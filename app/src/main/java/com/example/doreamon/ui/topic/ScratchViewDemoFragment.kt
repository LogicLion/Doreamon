package com.example.doreamon.ui.topic

import com.example.doreamon.R
import com.doreamon.treasure.base.BaseFragment
import com.doreamon.treasure.base.BaseViewModel
import com.example.doreamon.databinding.FragmentScratchViewBinding
import com.example.doreamon.widget.ScratchCardView

/**
 * 刮刮卡效果
 * @author wzh
 * @date 2022/4/26
 */
class ScratchViewDemoFragment : BaseFragment<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.fragment_scratch_view

    override fun initView() {

        val binding = getViewBinding<FragmentScratchViewBinding>()

        val scratchCardView = binding.iv
        binding.tvRollback.setOnClickListener {
            scratchCardView.rollback()
            binding.tvForward.isEnabled = true
        }

        binding.tvForward.setOnClickListener {
            scratchCardView.forward()
            binding.tvRollback.isEnabled = true
        }

        binding.tvRollback.isEnabled = false
        binding.tvForward.isEnabled = false
        scratchCardView.setStackStateChangeListener(object :
            ScratchCardView.StackStateListener {
            override fun onRollBackEnable(enable: Boolean) {
                binding.tvRollback.isEnabled = enable
            }

            override fun onForwardEnable(enable: Boolean) {
                binding.tvForward.isEnabled = enable
            }

        })
    }
}