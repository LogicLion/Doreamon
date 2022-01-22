package com.example.doreamon.ui

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.doreamon.R
import com.example.doreamon.base.BaseActivity
import com.example.doreamon.base.BaseViewModel
import com.example.doreamon.ext.startTargetActivity
import com.example.doreamon.global.UserInfoData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author wzh
 * @date 2022/1/21
 */
class LaunchActivity : BaseActivity<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.activity_launch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            delay(500)
//            if (UserInfoData.value == null) {
                startTargetActivity<LoginActivity>()
//            } else {
//                startTargetActivity<MainActivity>()
//            }

            finish()
        }
    }
}