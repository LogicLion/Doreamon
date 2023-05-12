package com.example.doreamon.ui

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.doreamon.R
import com.example.doreamon.StudyTopic
import com.doreamon.treasure.base.BaseActivity
import com.doreamon.treasure.base.BaseViewModel
import com.doreamon.treasure.export.ModuleMineApi
import com.example.doreamon.databinding.ActivityMainBinding
import com.example.doreamon.event.KeyHomeEvent
import com.example.doreamon.ui.main.TopicListFragment
import org.greenrobot.eventbus.EventBus

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
                    2 -> ModuleMineApi.getMineFragment()

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
            Log.v("navView", "宽：" + binding.navView.width)
            Log.v("navView", "高：" + binding.navView.height)
        }

        binding.vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
//                "选择页面${position}".toast()
            }
        })


    }

    override fun onResume() {
        super.onResume()

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            // 在这里处理 Home 按钮的操作逻辑
            Log.v(TAG, "onPressHomeKey")
            EventBus.getDefault().post(KeyHomeEvent())
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()

        // 在这里处理 Home 按钮的操作逻辑
//        Log.v(TAG, "onPressHomeKey")
        EventBus.getDefault().post(KeyHomeEvent())
    }


}