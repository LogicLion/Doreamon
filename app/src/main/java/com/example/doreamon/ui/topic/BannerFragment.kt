package com.example.doreamon.ui.topic

import androidx.recyclerview.widget.RecyclerView
import com.example.doreamon.R
import com.example.doreamon.adapter.BannerAdapter
import com.example.doreamon.base.BaseFragment
import com.example.doreamon.base.BaseViewModel
import com.example.doreamon.databinding.FragmentBannerBinding

/**
 * banner实现
 * @author wzh
 * @date 2022/4/26
 */
class BannerFragment :BaseFragment<BaseViewModel>(){
    override fun setupLayoutId()= R.layout.fragment_banner

    override fun initView() {
        val binding = getViewBinding<FragmentBannerBinding>()
        val bannerAdapter = BannerAdapter(listOf("", "", "", "", ""))
        binding.vpBanner.adapter=bannerAdapter

    }
}