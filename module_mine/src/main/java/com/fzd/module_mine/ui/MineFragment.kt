package com.fzd.module_mine.ui

import com.alibaba.android.arouter.facade.annotation.Route
import com.doreamon.module_mine.R
import com.doreamon.module_mine.databinding.MineFragmentMineBinding
import com.doreamon.treasure.base.BaseFragment
import com.doreamon.treasure.export.ModuleMineApi
import com.doreamon.treasure.ext.dp
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
        binding.tvText.text="dp系数：${AppManager.getContext().resources.displayMetrics.density}" +
                "\ndpi：${AppManager.getContext().resources.displayMetrics.densityDpi}" +
                "\n" +
                "屏幕宽度（像素）：${AppManager.getContext().resources.displayMetrics.widthPixels}" +"\n" +
        "屏幕高度（像素）：${AppManager.getContext().resources.displayMetrics.heightPixels}"

        binding.tvWidthTest.post {
            binding.tvText.append("\n360dp换算的像素是：${360.dp}")
            binding.tvText.append("\n360dp宽度的view实际像素是：${binding.tvWidthTest.width}")
        }
    }
}
