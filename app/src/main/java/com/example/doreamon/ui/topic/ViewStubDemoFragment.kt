package com.example.doreamon.ui.topic

import android.graphics.drawable.TransitionDrawable
import android.view.View
import com.example.doreamon.BR
import com.example.doreamon.R
import com.example.doreamon.base.BaseFragment
import com.example.doreamon.base.BaseViewModel
import com.example.doreamon.databinding.FragmentViewStubDemoBinding
import com.example.doreamon.databinding.LayoutViewStubTestBinding
import com.example.doreamon.entity.User
import com.example.doreamon.ext.toast
import com.example.doreamon.viewmodel.LoginViewModel

/**
 * @author wzh
 * @date 2022/9/22
 */
class ViewStubDemoFragment : BaseFragment<LoginViewModel>() {

    var viewInflate: View? = null
    override fun setupLayoutId() = R.layout.fragment_view_stub_demo

    override fun initView() {
        val binding = getViewBinding<FragmentViewStubDemoBinding>()


        val viewStub = binding.vStub.viewStub


        binding.btnViewShow.setOnClickListener {

            (binding.btnViewShow.background as TransitionDrawable).startTransition(500)
            if (viewInflate == null) {
                //只能调用一次，调用之后viewStub会从视图树中移除
                viewInflate = viewStub?.inflate()
            }

        }

        binding.btnViewHide.setOnClickListener {

            (binding.btnViewHide.background as TransitionDrawable).reverseTransition(500)
        }

        binding.vStub.setOnInflateListener { stub, inflated ->

            //因为延迟初始化了
            //延迟加载的那个布局的dataBinding可以在这里绑定
            binding.vStub.binding.let {
                "绑定测试".toast()
                val testBinding = it as LayoutViewStubTestBinding
                testBinding.lifecycleOwner=this
                testBinding.setVariable(BR.vm, LoginViewModel())
            }
        }

    }
}