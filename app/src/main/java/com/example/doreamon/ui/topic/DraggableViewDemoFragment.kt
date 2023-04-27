package com.example.doreamon.ui.topic

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.doreamon.treasure.base.BaseFragment
import com.doreamon.treasure.base.BaseViewModel
import com.doreamon.treasure.ext.toast
import com.example.doreamon.R
import com.example.doreamon.databinding.FragmentDraggableViewDemoBinding
import com.example.doreamon.ui.simple.SimpleAdapter
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @author wzh
 * @date 2022/10/25
 */
class DraggableViewDemoFragment : BaseFragment<BaseViewModel>() {

    override fun setupLayoutId() = R.layout.fragment_draggable_view_demo

    override fun initView() {
        val binding = getViewBinding<FragmentDraggableViewDemoBinding>()

        val tabList = listOf("1", "2", "3", "4", "5")

        val viewPager = binding.viewPager

        val pagerAdapter = PagerAdapter()
        pagerAdapter.setList(tabList)
        viewPager.adapter = pagerAdapter

        viewPager.isUserInputEnabled = true
        viewPager.offscreenPageLimit = 1
        TabLayoutMediator(
            binding.tabLayout, viewPager, true, true
        ) { tab, position ->
            tab.text = tabList[position]
        }.attach()


    }

    class PagerAdapter :
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_draggable_view_demo) {
        override fun convert(holder: BaseViewHolder, item: String) {
            val rvSimple = holder.getView<RecyclerView>(R.id.rv_simple)
            val ivImage = holder.getView<ImageView>(R.id.iv_image)

            val list = arrayListOf<String>()
            for (i in 0..30) {
                list.add("示例")
            }
            val adapter = SimpleAdapter()
            adapter.setList(list)
            rvSimple.adapter = adapter

            ivImage.setOnClickListener {
                "点击了...".toast()
            }
        }
    }
}