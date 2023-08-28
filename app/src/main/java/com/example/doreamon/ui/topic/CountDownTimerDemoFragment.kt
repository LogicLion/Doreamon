package com.example.doreamon.ui.topic

import com.example.doreamon.R
import com.example.module_base.base.BaseFragment
import com.example.module_base.base.BaseViewModel
import com.example.doreamon.databinding.FragmentCountDownTimerDemoBinding
import com.example.doreamon.utils.CountDownTimer

/**
 * 倒计时封装
 * @author wzh
 * @date 2023/2/6
 */
class CountDownTimerDemoFragment : com.example.module_base.base.BaseFragment<com.example.module_base.base.BaseViewModel>() {

    var timer: CountDownTimer? = null
    override fun setupLayoutId() = R.layout.fragment_count_down_timer_demo

    override fun initView() {
        val binding = getViewBinding<FragmentCountDownTimerDemoBinding>()

        binding.tvCountDownTimer.text = "60"
        binding.tvTimerControl.setOnClickListener {
            val state = binding.tvTimerControl.text
            if (state == "开始" || state == "完成") {
                timer = CountDownTimer(60, 1000)
                timer?.start(object : CountDownTimer.OnTimerCallBack {
                    override fun onStart() {
                        binding.tvTimerControl.text = "取消"
                    }

                    override fun onTick(times: Int) {
                        binding.tvCountDownTimer.text = times.toString()
                    }

                    override fun onFinish() {
                        binding.tvTimerControl.text = "完成"
                    }
                })
            } else if (state == "取消") {
                timer?.cancel()
                binding.tvTimerControl.text = "开始"
                binding.tvCountDownTimer.text = "60"
            }

        }

    }
}