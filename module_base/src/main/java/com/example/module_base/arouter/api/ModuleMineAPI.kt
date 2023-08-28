package com.example.module_base.arouter.api

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author wzh
 * @date 2023/5/25
 */
object ModuleMineAPI {

    const val ROUTER_MINE_MINE_FRAGMENT = "/mine/MineFragment"

    fun getMineFragment(): Fragment {
        return ARouter.getInstance().build(ROUTER_MINE_MINE_FRAGMENT).navigation() as Fragment
    }
}