package com.example.doreamon.ui.simple

import android.os.Bundle
import android.util.Log
import com.example.doreamon.R
import com.example.doreamon.base.BaseFragment
import com.example.doreamon.base.BaseViewModel
import com.example.doreamon.databinding.FragmentTestBinding

/**
 * @author wzh
 * @date 2022/5/10
 */
class TestFragment : BaseFragment<BaseViewModel>() {
    lateinit var title:String
    override fun setupLayoutId() = R.layout.fragment_test

    override fun initView() {

        val binding = getViewBinding<FragmentTestBinding>()
        binding.tvTitle.text = title

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString("title") ?:""
        Log.v(TAG,"onCreate-${title}")


    }

    override fun onResume() {
        super.onResume()
        Log.v(TAG,"onResume-${title}")
    }

    override fun onStart() {
        super.onStart()
        Log.v(TAG,"onStart-${title}")
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG,"onStop-${title}")
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG,"onDestroy-${title}")
    }


    companion object {
        fun newInstance(title: String): TestFragment {
            val fragment = TestFragment()
            val bundle = Bundle()
            bundle.putString("title", title)
            fragment.arguments=bundle
            return fragment
        }
    }
}