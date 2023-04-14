package com.example.doreamon.ui.simple

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.doreamon.R
import com.doreamon.treasure.base.BaseActivity
import com.doreamon.treasure.base.BaseViewModel
import com.example.doreamon.databinding.ActivityTestBinding

/**
 * @author wzh
 * @date 2022/9/13
 */
@Route(path = "/app/SimpleActivity")
class SimpleActivity : BaseActivity<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.activity_test

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = getViewBinding<ActivityTestBinding>()
    }
}