package com.example.doreamon.ui.topic

import android.util.Log
import com.doreamon.treasure.base.BaseFragment
import com.doreamon.treasure.base.BaseViewModel
import com.example.doreamon.R
import com.example.doreamon.databinding.FragmentCustomAnimationViewDemoBinding
import com.example.doreamon.widget.custom_animator.CustomAnimatorListener

/**
 * 定制轨迹动画效果,不依赖ObjectAnimator来实现
 * @author wzh
 * @date 2023/5/10
 */
class CustomAnimationViewDemoFragment : BaseFragment<BaseViewModel>() {

    lateinit var binding: FragmentCustomAnimationViewDemoBinding
    override fun setupLayoutId() = R.layout.fragment_custom_animation_view_demo

    override fun initView() {
        binding = getViewBinding()

        binding.tvStart1.setOnClickListener {

            if (binding.choreographerAnimationView.isAnimationRunning) {
                binding.choreographerAnimationView.pauseAnimation()
            } else {
                binding.choreographerAnimationView.startAnimation()
            }
        }

        binding.tvStop1.setOnClickListener {
            binding.choreographerAnimationView.stopAnimation()
        }


        binding.choreographerAnimationView.animatorListener = object : CustomAnimatorListener {
            override fun onAnimationStart() {
                binding.tvStart1.text = "暂停"
            }

            override fun onAnimationPause() {
                binding.tvStart1.text = "继续"
            }

            override fun onAnimationResume() {
            }

            override fun onAnimationEnd() {
                binding.tvStart1.text = "开始"
                binding.tvStop1.text = "停止"
            }

            override fun onTimeCountDown(second: Int) {
                Log.v("倒计时:", second.toString())
                binding.tvSecond.text = second.toString()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onPause() {
        super.onPause()


    }


    override fun onStop() {
        super.onStop()

        Log.v(TAG, "onPause")
    }

    override fun onStart() {
        super.onStart()
    }


    override fun onResume() {
        super.onResume()
    }


}