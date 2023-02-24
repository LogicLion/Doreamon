package com.example.doreamon.ui.topic

import android.util.TypedValue
import com.doreamon.treasure.base.BaseFragment
import com.doreamon.treasure.base.BaseViewModel
import com.example.doreamon.R
import com.example.doreamon.databinding.FragmentAutoFitWidthTextViewDemoBinding

/**
 * 文本自适应textview宽度
 * @author wzh
 * @date 2023/2/23
 */
class AutoFitWidthTextViewDemoFragment : BaseFragment<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.fragment_auto_fit_width_text_view_demo

    override fun initView() {
        val binding = getViewBinding<FragmentAutoFitWidthTextViewDemoBinding>()


        //通过代码设置自适应宽度
        binding.tvText.setAutoSizeTextTypeUniformWithConfiguration(1,200,1,TypedValue.COMPLEX_UNIT_DIP)

        binding.tvUpdate.setOnClickListener {
            binding.tvText.text = "一行文字完整显示出来"
        }
    }
}