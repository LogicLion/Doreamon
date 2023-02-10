package com.fzd.module_mine.ui.alone

import android.os.Bundle
import com.doreamon.module_mine.R
import com.doreamon.treasure.base.BaseActivity
import com.doreamon.treasure.base.BaseViewModel
import com.doreamon.treasure.export.ModuleMineApi

/**
 * @author wzh
 * @date 2023/2/9
 */
class AloneActivity :BaseActivity<BaseViewModel>() {
    override fun setupLayoutId()= R.layout.common_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .add(R.id.fl_container, ModuleMineApi.getMineFragment())
            .commit()
    }
}