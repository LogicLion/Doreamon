package com.example.doreamon.ui.simple

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

/**
 * @author wzh
 * @date 2022/8/5
 */
class MyFragmentFactory : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return super.instantiate(classLoader, className)
    }
}