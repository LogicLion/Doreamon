package com.example.doreamon.ui.topic

import com.example.doreamon.R
import com.example.module_base.base.BaseFragment
import com.example.module_base.base.BaseViewModel
import com.example.doreamon.databinding.FragmentBannerBinding

/**
 * banner实现
 * @author wzh
 * @date 2022/4/26
 */
class BannerFragment : BaseFragment<BaseViewModel>(){
    override fun setupLayoutId()= R.layout.fragment_banner

    override fun initView() {
        val binding = getViewBinding<FragmentBannerBinding>()
//        val bannerAdapter = BannerAdapter(listOf("", "", "", "", ""))
//        binding.vpBanner.adapter=bannerAdapter

    }
}