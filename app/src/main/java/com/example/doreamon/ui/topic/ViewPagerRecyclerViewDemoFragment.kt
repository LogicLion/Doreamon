package com.example.doreamon.ui.topic

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.doreamon.R
import com.example.doreamon.databinding.FragmentViewpagerRecyclerviewBinding
import com.example.module_base.base.BaseFragment
import com.example.module_base.base.BaseViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


/**
 * @author wzh
 * @date 2024/7/9
 */
class ViewPagerRecyclerViewDemoFragment : BaseFragment<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.fragment_viewpager_recyclerview

    override fun initView() {

        val binding = getViewBinding<FragmentViewpagerRecyclerviewBinding>()
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager
        viewPager.adapter = object : FragmentStateAdapter(mContext) {
            override fun getItemCount() = 3

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> {
                        RecyclerViewTestFragment()
                    }
                    1 -> {
                        RecyclerViewTestFragment()
                    }
                    else -> {
                        RecyclerViewTestFragment()
                    }
                }
            }
        }


        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.getTabAt(position)?.select()
            }
        })
        for (i in 0..2) {
            tabLayout.addTab(tabLayout.newTab().setText("Tab " + (i + 1)))
        }
    }

}