package com.example.doreamon.ui.topic

import android.os.Bundle
import android.util.Log
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.example.doreamon.R
import com.example.module_base.base.BaseActivity
import com.example.doreamon.databinding.ActivityFragmentToggleBinding
import com.example.doreamon.ui.main.HomePageFragment
import com.example.doreamon.ui.main.TopicListFragment
import com.example.doreamon.viewmodel.MainViewModel


/**
 * 演示fragment切换
 */
class FragmentToggleActivity : com.example.module_base.base.BaseActivity<MainViewModel>() {
    private val currentFragmentTagKey = "CURR_FRAGMENT_KEY"
    private lateinit var currentFragment: Fragment

    private lateinit var mHomePageFragment: HomePageFragment
    private lateinit var mTopicListFragment: TopicListFragment
    override fun setupLayoutId() = R.layout.activity_fragment_toggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.v(TAG, "onCreate")


        //关闭根布局的fitSystemWindow偏移
        WindowCompat.setDecorFitsSystemWindows(window, false)
        mHomePageFragment =
            supportFragmentManager.findFragmentByTag(HomePageFragment::class.java.simpleName) as HomePageFragment?
                ?: HomePageFragment()

        mTopicListFragment =
            supportFragmentManager.findFragmentByTag(TopicListFragment::class.java.simpleName) as TopicListFragment?
                ?: TopicListFragment()


        val binding = getViewBinding<ActivityFragmentToggleBinding>()
        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_homepage -> showFragment(mHomePageFragment)
                R.id.menu_topic -> showFragment(mTopicListFragment)
            }
            true
        }

        if (savedInstanceState != null) {

            when (savedInstanceState.getString(currentFragmentTagKey, "")) {
                HomePageFragment::class.java.simpleName -> currentFragment = mHomePageFragment
                TopicListFragment::class.java.simpleName -> currentFragment = mTopicListFragment
            }
        } else {
            showFragment(mHomePageFragment)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(currentFragmentTagKey, currentFragment.javaClass.simpleName)
    }

    override fun onResume() {
        super.onResume()

    }

    private fun showFragment(targetFragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        if (!targetFragment.isAdded) {
            transaction.add(R.id.fl_content, targetFragment, targetFragment::class.java.simpleName)
        }

        if (::currentFragment.isInitialized) {
            transaction.hide(currentFragment)
        }

        transaction.show(targetFragment).commit()
        currentFragment = targetFragment
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "onDestroy")
    }
}