package com.example.doreamon.ui

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.doreamon.R
import com.example.doreamon.base.BaseFragment
import com.example.doreamon.databinding.FragmentHomePageBinding
import com.example.doreamon.entity.Pager
import com.example.doreamon.ui.main.ProgressViewDemoFragment
import com.example.doreamon.ui.main.ScratchViewDemoFragment
import com.example.doreamon.ui.main.TestFragment
import com.example.doreamon.ui.main.WebViewDemoFragment
import com.example.doreamon.viewmodel.HomePageViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.tencent.smtt.utils.f


/**
 * @author wzh
 * @date 2022/2/23
 */
class HomePageFragment : BaseFragment<HomePageViewModel>() {

    lateinit var binding: FragmentHomePageBinding
    override fun setupLayoutId() = R.layout.fragment_home_page

    override fun initView() {
        binding = getViewBinding()

        val pagers = getPager()

        binding.viewPager.adapter = object : FragmentStateAdapter(mContext) {
            override fun getItemCount() = pagers.size

            override fun createFragment(position: Int): Fragment {
                return pagers[position].fragment
            }
        }

        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, position ->
            tab.text = pagers[position].tab
        }.attach()


    }

    private fun getPager(): ArrayList<Pager> {
        val pagers = arrayListOf<Pager>()

        pagers.add(Pager("测试", TestFragment()))
        pagers.add(Pager("WebView使用", WebViewDemoFragment()))
        pagers.add(Pager("刮刮卡效果", ScratchViewDemoFragment()))
        pagers.add(Pager("自定义进度条", ProgressViewDemoFragment()))

        return pagers
    }





}