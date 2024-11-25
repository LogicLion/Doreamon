package com.example.doreamon.ui.topic

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.doreamon.R
import com.example.doreamon.databinding.FragmentViewpger2DemoBinding
import com.example.doreamon.ui.simple.SimplePagerFragment
import com.example.doreamon.ui.simple.TextSimple
import com.example.module_base.base.BaseFragment
import com.example.module_base.base.BaseViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * tablayout自定义view
 * @author wzh
 * @date 2022/5/9
 */
class TabLayoutCustomViewDemoFragment : BaseFragment<BaseViewModel>() {
    val list = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8)
    lateinit var binding: FragmentViewpger2DemoBinding

    private lateinit var popupWindow: PopupWindow

    override fun setupLayoutId() = R.layout.fragment_viewpger2_demo

    override fun initView() {
        binding = getViewBinding()

        val pagers = TextSimple.POKER

        val viewPager = binding.viewPager


        // 初始化 Tabs
        for (i in 1..list.size) {
            val tab = binding.tabLayout.newTab()

            // 设置自定义布局
            val customView = LayoutInflater.from(requireActivity())
                .inflate(R.layout.item_tab_course_unit_sync_host, null)
            val tabTitle = customView.findViewById<TextView>(R.id.tv_unit_title)
            tabTitle.text = "Tab $i"

            tab.customView = customView
            binding.tabLayout.addTab(tab)
        }


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
//            tab.text = "${list[position]}"
            // 自定义 Tab 布局
            val customView = LayoutInflater.from(requireActivity())
                .inflate(R.layout.item_tab_course_unit_sync_host, null)
            val tabTitle = customView.findViewById<TextView>(R.id.tv_unit_title)
            tabTitle.text = "第${position}单元xxxxx"
            tab.customView = customView
        }.attach()


        // 初始化 PopupWindow
        val popupView =
            LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_unit_title_popup, null)
        popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow.isOutsideTouchable = true

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                tab.customView?.let {
                    val tabTitle = it?.findViewById<TextView>(R.id.tv_unit_title)
                    tabTitle?.setTextColor(Color.parseColor("#F1BC1A"))
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // 可选择是否关闭 PopupWindow
//                popupWindow.dismiss()

                tab.customView?.let {
                    val tabTitle = it?.findViewById<TextView>(R.id.tv_unit_title)
                    tabTitle?.setTextColor(Color.parseColor("#333333"))
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // 如果需要重新显示，重复逻辑
            }
        })

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    val selectedPosition = binding.tabLayout.selectedTabPosition
                    val selectedTabView =
                        binding.tabLayout.getTabAt(selectedPosition)?.view ?: return

                    // 获取 Tab 的位置
                    val location = IntArray(2)
                    selectedTabView.getLocationOnScreen(location)

                    val x = location[0]
                    val y = location[1]

                    // 计算 PopupWindow 的显示位置 (正上方)
                    val popupX = x + selectedTabView.width / 2 - popupWindow.width / 2
                    val popupY = y - selectedTabView.height

                    // 显示 PopupWindow
//                    popupWindow.showAtLocation(binding.tabLayout, 0, popupX, popupY)

//                    popupWindow.showAsDropDown(selectedTabView, 0, 0,)
                    popupWindow.showAsDropDown(selectedTabView)
                }
            }
        })
    }
}