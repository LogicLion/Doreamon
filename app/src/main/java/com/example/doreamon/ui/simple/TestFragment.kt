package com.example.doreamon.ui.simple

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.TextView
import com.blankj.utilcode.util.BarUtils
import com.example.doreamon.R
import com.example.doreamon.base.BaseFragment
import com.example.doreamon.base.BaseViewModel
import com.example.doreamon.databinding.FragmentTestBinding

/**
 * @author wzh
 * @date 2022/5/10
 */
class TestFragment : BaseFragment<BaseViewModel>() {
    lateinit var title: String
    override fun setupLayoutId() = R.layout.fragment_test

    override fun initView() {

        val binding = getViewBinding<FragmentTestBinding>()


        binding.tvTest.post {

            val ints = IntArray(2)
            binding.tvTest.getLocationOnScreen(ints)


//            binding.tvTest.text = "x:${ints[0]}，y:${ints[1]}"

            val width = binding.tvTest.width
            val height = binding.tvTest.height
            val textView = TextView(requireActivity())
            textView.setBackgroundColor(Color.parseColor("#99999999"))
            textView.text="示例"
//            textView.width = width
//            textView.height = height
//            textView.x = ints[0].toFloat()
//            textView.y = ints[1].toFloat()

            val params = FrameLayout.LayoutParams(width, height)

            params.setMargins(ints[0],ints[1],0,0)
            textView.layoutParams=params


            binding.flContent.addView(textView,params)

        }
//        lifecycle.addObserver(MyLifeCycleObserver())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString("title") ?: ""
        Log.v(TAG, "onCreate-${title}")


    }

    override fun onResume() {
        super.onResume()
        Log.v(TAG, "onResume-${title}")

        lifecycle.addObserver(MyLifeCycleObserver())
    }

    override fun onStart() {
        super.onStart()
        Log.v(TAG, "onStart-${title}")
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG, "onStop-${title}")
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "onDestroy-${title}")
    }


    companion object {
        fun newInstance(title: String): TestFragment {
            val fragment = TestFragment()
            val bundle = Bundle()
            bundle.putString("title", title)
            fragment.arguments = bundle
            return fragment
        }
    }


}