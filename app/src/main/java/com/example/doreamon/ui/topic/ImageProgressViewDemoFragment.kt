package com.example.doreamon.ui.topic

import com.example.doreamon.R
import com.example.doreamon.databinding.FragmentImageProgressViewDemoBinding
import com.example.module_base.base.BaseFragment
import com.example.module_base.base.BaseViewModel
import kotlin.random.Random

/**
 * 图片进度条自定义view实现
 * @author wzh
 * @date 2023/8/2
 */
class ImageProgressViewDemoFragment : BaseFragment<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.fragment_image_progress_view_demo

    override fun initView() {
        val binding = getViewBinding<FragmentImageProgressViewDemoBinding>()
        binding.tvUpdateProgress.setOnClickListener {
            binding.progressView.progress = getRandomData()
        }


    }

    private fun getRandomData(): Int {
        return Random.nextInt(0, 100)
    }
}