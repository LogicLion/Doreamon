package com.example.doreamon.ui.topic

import android.graphics.drawable.TransitionDrawable
import android.view.View
import com.example.doreamon.BR
import com.example.doreamon.R
import com.example.doreamon.databinding.FragmentViewStubDemoBinding
import com.example.doreamon.databinding.LayoutViewStubTestBinding
import com.doreamon.treasure.ext.toast
import com.example.module_base.base.BaseViewModel

/**
 * @author wzh
 * @date 2022/9/22
 */
class ViewStubDemoFragment : com.example.module_base.base.BaseFragment<BaseViewModel>() {

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


    }
}