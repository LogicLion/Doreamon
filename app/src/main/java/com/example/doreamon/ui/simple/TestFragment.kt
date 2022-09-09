package com.example.doreamon.ui.simple

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.graphics.toColorInt
import com.alibaba.android.arouter.launcher.ARouter
import com.doreamon.treasure.utils.getStringById
import com.example.doreamon.R
import com.example.doreamon.base.BaseFragment
import com.example.doreamon.databinding.FragmentTestBinding
import com.example.doreamon.entity.User
import com.example.doreamon.viewmodel.TestViewModel
import com.hyy.highlightpro.HighlightPro
import com.hyy.highlightpro.parameter.Constraints
import com.hyy.highlightpro.parameter.HighlightParameter
import com.hyy.highlightpro.parameter.MarginOffset
import com.hyy.highlightpro.shape.RectShape
import com.hyy.highlightpro.util.dp


/**
 * @author wzh
 * @date 2022/5/10
 */
class TestFragment : BaseFragment<TestViewModel>() {
    lateinit var title: String
    var isHide: Boolean = false

    private var height: Float = 0f

    private lateinit var binding: FragmentTestBinding

    override fun setupLayoutId() = R.layout.fragment_test


    override fun initView() {

        binding = getViewBinding()


        lifecycle.addObserver(MyLifeCycleObserver())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.openGuideView.text= getStringById(R.string.app_homepage)
        binding.openGuideView.setOnClickListener {
            ARouter.getInstance().build("/testRouter/activity")
                .withSerializable("testArg", User(nickname = "sgdsgsgsdg")).navigation()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString("title") ?: ""
        Log.v(TAG, "onCreate-${title}")

    }


    override fun onResume() {
        super.onResume()
        Log.v(TAG, "onResume-${title}")
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

    private fun showTipView() {

        val view = TextView(requireActivity())
        view.text = "退出引导"
        view.setTextColor(Color.WHITE)
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.TOP or Gravity.END
        )
        layoutParams.marginEnd = 100
        layoutParams.topMargin = 100
        view.setPadding(20, 20, 20, 20)
        view.layoutParams = layoutParams
        val rootView = requireActivity().window.decorView as ViewGroup

        val highlightPro = HighlightPro.with(this)
            .setHighlightParameter {
                HighlightParameter.Builder()
                    .setHighlightViewId(R.id.tv_high_light_1)
                    .setTipsViewId(R.layout.cl_anchor_view)
                    .setHighlightShape(RectShape())
                    .setHighlightHorizontalPadding(8f.dp)
                    .setHighlightVerticalPadding(8f.dp)
                    .setConstraints(Constraints.StartToEndOfHighlight + Constraints.TopToTopOfHighlight)
                    .setMarginOffset(MarginOffset(start = 8.dp))
                    .build()
            }
            .setHighlightParameter {
                HighlightParameter.Builder()
                    .setHighlightViewId(R.id.tv_high_light_2)
                    .setTipsViewId(R.layout.cl_anchor_view)
                    .setHighlightShape(RectShape(4f.dp, 4f.dp, 6f))
                    .setHighlightHorizontalPadding(8f.dp)
                    .setConstraints(Constraints.StartToEndOfHighlight + Constraints.TopToTopOfHighlight)
                    .setMarginOffset(MarginOffset(start = 8.dp))
                    .build()
            }
            .setBackgroundColor("#cc000000".toColorInt())
            .setOnShowCallback { index ->
                when (index) {
                    0 -> binding.scrollView.scrollY = 0
                    1 -> binding.scrollView.scrollY = 3000
                }
            }
            .setOnDismissCallback {
                rootView.removeView(view)
            }

        highlightPro.show()

        view.setOnClickListener {
            highlightPro.dismiss()
        }


        rootView.addView(view)



    }


}