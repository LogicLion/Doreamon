package com.example.doreamon.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.doreamon.R
import com.doreamon.treasure.base.BaseActivity
import com.doreamon.treasure.base.BaseViewModel
import com.example.doreamon.databinding.ActivityTestBinding
import com.example.doreamon.entity.User

/**
 * @author wzh
 * @date 2022/8/26
 */
@Route(path = "/app/testActivity")
class TestActivity : BaseActivity<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.activity_test

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extra = intent.getSerializableExtra("testArg") as User?
        val binding = getViewBinding<ActivityTestBinding>()
        binding.tvTest.text=extra.toString()
    }
}