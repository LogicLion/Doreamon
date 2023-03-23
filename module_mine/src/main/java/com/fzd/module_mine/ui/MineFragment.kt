package com.fzd.module_mine.ui

import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.DeviceUtils
import com.doreamon.module_mine.R
import com.doreamon.module_mine.databinding.MineFragmentMineBinding
import com.doreamon.treasure.base.BaseFragment
import com.doreamon.treasure.export.ModuleMineApi
import com.doreamon.treasure.utils.AppManager

/**
 * @author wzh
 * @date 2022/2/28
 */
@Route(path = ModuleMineApi.ROUTER_MINE_MINE_FRAGMENT)
class MineFragment : BaseFragment<MineViewModel>() {
    override fun setupLayoutId() = R.layout.mine_fragment_mine

    override fun initView() {

        val binding = getViewBinding<MineFragmentMineBinding>()

        binding.tvText.append("\n屏幕宽度：${AppManager.getContext().resources.displayMetrics.widthPixels}px")
        binding.tvText.append("\n屏幕高度：${AppManager.getContext().resources.displayMetrics.heightPixels}px")
        binding.tvText.append("\n屏幕dpi：${AppManager.getContext().resources.displayMetrics.densityDpi}")
        binding.tvText.append("\ndensity：${AppManager.getContext().resources.displayMetrics.density}")

        binding.tvText.append("\nAndroidID:${DeviceUtils.getAndroidID()}")
        binding.tvText.append("\n获取唯一设备 ID:${DeviceUtils.getUniqueDeviceId()}")



        binding.tvText.post{
            Log.i(TAG,"tvtext行数：${binding.tvText.lineCount}")
        }



    }
}
