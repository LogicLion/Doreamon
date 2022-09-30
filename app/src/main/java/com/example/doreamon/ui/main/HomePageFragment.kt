package com.example.doreamon.ui.main

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.doreamon.R
import com.example.doreamon.base.BaseFragment
import com.example.doreamon.databinding.FragmentHomePageBinding
import com.example.doreamon.entity.Pager
import com.example.doreamon.ui.simple.CollapseFragment
import com.example.doreamon.ui.topic.*
import com.example.doreamon.viewmodel.HomePageViewModel
import com.google.android.material.tabs.TabLayoutMediator


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

        val viewPager = binding.viewPager
        viewPager.adapter = object : FragmentStateAdapter(mContext) {
            override fun getItemCount() = pagers.size

            override fun createFragment(position: Int): Fragment {
                return pagers[position].fragment
            }
        }

        (viewPager.getChildAt(0) as RecyclerView).layoutManager?.isItemPrefetchEnabled = false

        viewPager.isUserInputEnabled = false

        viewPager.offscreenPageLimit=4

        Log.v(TAG, "offscreenPageLimit:${viewPager.offscreenPageLimit}")


        TabLayoutMediator(
            binding.tabLayout, viewPager,true,false
        ) { tab, position ->
            tab.text = pagers[position].tab
        }.attach()


    }

    private fun getPager(): ArrayList<Pager> {
        val pagers = arrayListOf<Pager>()

        pagers.add(Pager("测试", CollapseFragment()))
//        pagers.add(Pager("WebView使用", WebViewDemoFragment()))
        pagers.add(Pager("刮刮卡效果", ScratchViewDemoFragment()))
        pagers.add(Pager("循环列表", LoopRecyclerViewFragment()))
        pagers.add(Pager("自定义进度条", ProgressViewDemoFragment()))
//        pagers.add(Pager("Banner", BannerFragment()))

        return pagers
    }


}