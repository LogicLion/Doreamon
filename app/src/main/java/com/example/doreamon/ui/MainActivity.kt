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
import com.example.doreamon.ui.simple.MyLifeCycleObserver
import java.lang.IllegalArgumentException

/**
 * @author wzh
 * @date 2022/6/2
 */
class MainActivity : BaseActivity<BaseViewModel>() {
    lateinit var binding: ActivityMainBinding
    override fun setupLayoutId() = R.layout.activity_main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setStatusBarTextLight()
        setStatusBarTextDark()
        setStatusBarNotPlaceHolder()
        binding = getViewBinding()

        binding.vp.isUserInputEnabled = false
        binding.vp.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 3

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> {
                        val fragmentTag = StudyTopic.List[0].fragmentTag
                        Class.forName(fragmentTag).newInstance() as Fragment
                    }

                    1 -> TopicListFragment()
                    2 -> MineFragment()

                    else -> throw IllegalArgumentException("fragment in position $position doesn't exit")
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
            Log.v("navView", "??????" + binding.navView.width)
            Log.v("navView", "??????" + binding.navView.height)
        }



    }

    override fun onResume() {
        super.onResume()

    }
}