package com.example.doreamon.ui.topic

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.doreamon.R
import com.example.doreamon.databinding.FragmentViewpger2DemoBinding
import com.example.doreamon.ui.simple.SimplePagerFragment
import com.example.doreamon.ui.simple.TextSimple
import com.example.module_base.base.BaseFragment
import com.example.module_base.base.BaseViewModel
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @author wzh
 * @date 2022/5/9
 */
class ViewPager2DemoFragment : BaseFragment<BaseViewModel>() {
    val list = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8)
    lateinit var binding: FragmentViewpger2DemoBinding
    override fun setupLayoutId() = R.layout.fragment_viewpger2_demo

    override fun initView() {
        binding = getViewBinding()

        val pagers = TextSimple.POKER

        val viewPager = binding.viewPager


        //设置adapter
        //如果是view就是用的recyclerview的adapter
        //如果是fragment就是用FragmentStateAdapter
        viewPager.adapter = object : FragmentStateAdapter(mContext) {
            override fun getItemCount() = list.size

            override fun createFragment(position: Int): Fragment {
                return SimplePagerFragment.newInstance("${list[position]}")
            }
        }

        //设置viewpager能否滑动，false表示不能滑
        viewPager.isUserInputEnabled = true


        //设置离屏显示数量,默认值为-1，即默认就是懒加载
        //因viewpager2用的recyclerview的ViewCache,设置offscreenPageLimit同时会影响ViewCache
        //假如设置为1，默认加载左右两边的fragment，可理解为画布变为3倍
        //同时影响生命周期onCreate,onStart,但onResume不影响
        //即使设置了offscreenPageLimit>1,依然可以在onResume生命周期中设置懒加载
        viewPager.offscreenPageLimit = 5

        //TabLayoutMediator专门用来绑定ViewPager2和tabLayout
        // ①设置tabLayout标题
        // ②viewpager是否smoothScroll,即平滑移动,
        //  这个平滑移动可能会影响切换途经的Fragment的生命周期onCreate,onStart,但onResume不影响
        //  如果不希望影响，改为false
        TabLayoutMediator(
            binding.tabLayout, viewPager, true, true
        ) { tab, position ->

            //在这里初始化tab，如果是设置customView也是
            tab.text = "${list[position]}"
        }.attach()
    }
}