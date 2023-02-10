package com.doreamon.treasure.export

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author wzh
 * @date 2023/2/9
 */
object ModuleMineApi {

    const val ROUTER_MINE_MINE_FRAGMENT = "/mine/MineFragment"

    fun getMineFragment(): Fragment {
        return ARouter.getInstance().build(ROUTER_MINE_MINE_FRAGMENT).navigation() as Fragment
    }
}