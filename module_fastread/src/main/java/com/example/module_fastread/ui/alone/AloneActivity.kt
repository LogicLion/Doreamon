package com.example.module_fastread.ui.alone

import android.os.Bundle
import com.example.module_base.base.BaseActivity
import com.example.module_base.base.BaseViewModel
import com.example.module_base.R
import com.example.module_fastread.ui.FastReadListFragment

/**
 * @author wzh
 * @date 2023/2/9
 */
class AloneActivity : BaseActivity<BaseViewModel>() {
    override fun setupLayoutId()= R.layout.common_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fl_container, FastReadListFragment())
                .commit()
        }

    }
}