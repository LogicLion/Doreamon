package com.example.doreamon.ui.main

import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.DeviceUtils
import com.example.doreamon.R
import com.example.doreamon.base.BaseFragment
import com.example.doreamon.base.BaseViewModel
import com.example.doreamon.databinding.FragmentTestBinding

/**
 * @author wzh
 * @date 2022/4/29
 */
class TestFragment :BaseFragment<BaseViewModel>() {
    override fun setupLayoutId()= R.layout.fragment_test

    override fun initView() {

        val isTablet = DeviceUtils.isTablet()

        val binding = getViewBinding<FragmentTestBinding>()

        if (isTablet) {
            binding.tvTitle.text="这是平板"
        }else{
            binding.tvTitle.text="这是手机"
        }

        BarUtils.setStatusBarLightMode(requireActivity(),true)
    }
}