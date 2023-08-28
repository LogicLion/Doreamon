package com.example.doreamon.ui.topic

import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.widget.Button
import com.example.doreamon.R
import com.example.module_base.base.BaseFragment
import com.example.module_base.base.BaseViewModel
import com.example.doreamon.databinding.FragmentLayoutTransitionDemoBinding
import com.doreamon.treasure.ext.toast

/**
 * 演示用LayoutTransition实现在添加view和移除view时的动画效果
 * @author wzh
 * @date 2022/10/9
 */
class LayoutTransitionDemoFragment : com.example.module_base.base.BaseFragment<com.example.module_base.base.BaseViewModel>() {
    override fun setupLayoutId() = R.layout.fragment_layout_transition_demo

    override fun initView() {


        val binding = getViewBinding<FragmentLayoutTransitionDemoBinding>()

        //把animateLayoutChanges设置为true即打开view添加移除动画
        //除了系统默认设置，可以自定义一个效果
        val transition = LayoutTransition()
        binding.llDividerTest.layoutTransition = transition


        //缩放
        val holder1 = PropertyValuesHolder.ofFloat("scaleX", 0.5F, 1f)
        //y位移
        val holder2 = PropertyValuesHolder.ofFloat("translationY", -300F, 0F)
        //透明度
        val holder3 = PropertyValuesHolder.ofFloat("alpha", 0f, 1f)
        val addViewAnimator =
            ObjectAnimator.ofPropertyValuesHolder(binding.llDividerTest, holder1, holder2, holder3)
        //添加view的动画
        transition.setAnimator(LayoutTransition.APPEARING, addViewAnimator)


        val holder4 = PropertyValuesHolder.ofFloat("rotation", 1f, 0.5f)
        val holder5 = PropertyValuesHolder.ofFloat("alpha", 0.8f, 0f)
        val removeViewAnimator =
            ObjectAnimator.ofPropertyValuesHolder(binding.llDividerTest, holder4, holder5)

        //移除view的动画
        transition.setAnimator(LayoutTransition.DISAPPEARING, removeViewAnimator)

        binding.btnAddView.setOnClickListener {
            val button = Button(requireActivity())

            button.text = "一个客户"

            binding.llDividerTest.addView(button)
        }

        binding.btnRemoveView.setOnClickListener {

            if (binding.llDividerTest.childCount <= 0) {
                "至少添加一个子View".toast()
                return@setOnClickListener
            }
            binding.llDividerTest.removeViewAt(0)
        }
    }
}