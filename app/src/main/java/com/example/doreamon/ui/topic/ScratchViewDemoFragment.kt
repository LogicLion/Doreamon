package com.example.doreamon.ui.topic

import com.doreamon.treasure.base.BaseFragment
import com.doreamon.treasure.base.BaseViewModel
import com.doreamon.treasure.ext.toast
import com.example.doreamon.R
import com.example.doreamon.databinding.FragmentScratchViewBinding
import com.example.doreamon.ext.buildSpannableString
import com.example.doreamon.widget.ScratchCardView

/**
 * 刮刮卡效果
 * @author wzh
 * @date 2022/4/26
 */
class ScratchViewDemoFragment : BaseFragment<BaseViewModel>() {


    lateinit var binding: FragmentScratchViewBinding
    override fun setupLayoutId() = R.layout.fragment_scratch_view

    override fun initView() {

        binding = getViewBinding()

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



        binding.tvLearnDay.buildSpannableString {
            addText("累计学习")
            addText("20") {
                setColor("#f55a5a")
            }
            addText("天")
            addText("点击") {
                setColor("#f55121")
                onClick {
                    "触发点击".toast()
                }
            }
        }

//        binding.customAnimationView.startAnimation(3000)
//        binding.choreographerAnimationView.startAnimation()




    }




}