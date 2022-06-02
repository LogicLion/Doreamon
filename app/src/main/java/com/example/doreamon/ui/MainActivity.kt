package com.example.doreamon.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.doreamon.R
import com.example.doreamon.base.BaseActivity
import com.example.doreamon.base.BaseViewModel
import com.example.doreamon.databinding.ActivityMainBinding
import com.example.doreamon.ui.main.HomePageFragment
import com.example.doreamon.ui.main.MineFragment
import com.example.doreamon.ui.main.TopicListFragment

/**
 * @author wzh
 * @date 2022/6/2
 */
class MainActivity : BaseActivity<BaseViewModel>() {
    lateinit var binding: ActivityMainBinding
    override fun setupLayoutId() = R.layout.activity_main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = getViewBinding()

        binding.vp.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 3

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> HomePageFragment()
                    1 -> TopicListFragment()
                    else -> MineFragment()
                }
            }
        }

        binding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_homepage -> {
                    binding.vp.currentItem = 0
                }

                R.id.menu_topic -> {
                    binding.vp.currentItem = 1
                }

                R.id.menu_my -> {
                    binding.vp.currentItem = 2
                }
            }
            return@setOnItemSelectedListener true
        }

        binding.navView.post {
            Log.v("navView","宽："+binding.navView.width)
            Log.v("navView","高："+binding.navView.height)
        }

    }
}