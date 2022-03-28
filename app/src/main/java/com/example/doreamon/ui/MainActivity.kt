package com.example.doreamon.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.doreamon.R
import com.example.doreamon.base.BaseActivity
import com.example.doreamon.databinding.ActivityMainBinding
import com.example.doreamon.ui.main.MineFragment
import com.example.doreamon.viewmodel.MainViewModel
import com.google.android.material.navigation.NavigationBarView

class MainActivity : BaseActivity<MainViewModel>() {
    private val currentFragmentTagKey = "CURR_FRAGMENT_KEY"
    private lateinit var currentFragment: Fragment

    private lateinit var mHomePageFragment: HomePageFragment
    private lateinit var mMineFragment: MineFragment
    override fun setupLayoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mHomePageFragment =
            supportFragmentManager.findFragmentByTag(HomePageFragment::class.java.simpleName) as HomePageFragment?
                ?: HomePageFragment()

        mMineFragment =
            supportFragmentManager.findFragmentByTag(MineFragment::class.java.simpleName) as MineFragment?
                ?: MineFragment()


        val binding = getViewBinding<ActivityMainBinding>()
        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_homepage -> showFragment(mHomePageFragment)
                R.id.menu_my -> showFragment(mMineFragment)
            }
            true
        }

        if (savedInstanceState != null) {
            when (savedInstanceState.getString(currentFragmentTagKey, "")) {
                HomePageFragment::class.java.simpleName -> showFragment(mHomePageFragment)
                MineFragment::class.java.simpleName -> showFragment(mMineFragment)
            }
        } else {
            showFragment(mHomePageFragment)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(currentFragmentTagKey, currentFragment.javaClass.simpleName)
    }

    private fun showFragment(targetFragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        if (!targetFragment.isAdded) {
            transaction.add(R.id.fl_content, targetFragment, targetFragment::class.java.name)
        }

        if (::currentFragment.isInitialized) {
            transaction.hide(currentFragment)
        }

        transaction.show(targetFragment).commit()
        currentFragment = targetFragment
    }
}