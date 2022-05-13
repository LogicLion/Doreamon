package com.example.doreamon.ui.topic

import com.example.doreamon.R
import com.example.doreamon.base.BaseFragment
import com.example.doreamon.databinding.FragmentMineBinding
import com.example.doreamon.utils.AppManager
import com.example.doreamon.utils.AppUtil
import com.example.doreamon.viewmodel.MineViewModel

/**
 * @author wzh
 * @date 2022/2/28
 */
class MineFragment : BaseFragment<MineViewModel>() {
    override fun setupLayoutId() = R.layout.fragment_mine

    override fun initView() {

        val binding = getViewBinding<FragmentMineBinding>()
        binding.tvText.text="dp系数：${AppManager.getContext().resources.displayMetrics.density}" +
                "\ndpi：${AppManager.getContext().resources.displayMetrics.densityDpi}" +
                "\n" +
                "宽度（像素）：${AppManager.getContext().resources.displayMetrics.widthPixels}" +"\n" +
        "高度（像素）：${AppManager.getContext().resources.displayMetrics.heightPixels}"

    }
}
