package com.example.doreamon.ui.topic

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.text.*
import android.text.style.*
import com.doreamon.treasure.base.BaseFragment
import com.doreamon.treasure.base.BaseViewModel
import com.example.doreamon.R
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

        val scratchCardView = binding.scratchCardView
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



        binding.timeTextView.setTime(107562)



    }



}