package com.example.doreamon.ui.topic

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.addListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doreamon.R
import com.example.module_base.base.BaseFragment
import com.example.doreamon.databinding.FragmentCollapseBinding
import com.example.doreamon.ui.simple.MenuAdapter
import com.example.doreamon.ui.simple.MyLifeCycleObserver
import com.example.doreamon.viewmodel.TestViewModel


/**
 * 菜单折叠展开
 * @author wzh
 * @date 2022/5/10
 */
class CollapseFragment : BaseFragment<TestViewModel>() {
    lateinit var title: String
    var isHide: Boolean = false

    private var height: Float = 0f
    private var isFold: Boolean = true

    private lateinit var binding: FragmentCollapseBinding

    override fun setupLayoutId() = R.layout.fragment_collapse


    override fun initView() {

        binding = getViewBinding()


        lifecycle.addObserver(MyLifeCycleObserver())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = listOf("", "", "", "", "", "", "", "", "", "", "", "", "")
        val adapter = MenuAdapter()
        adapter.setList(list)
        binding.rvMenu.adapter = adapter
        binding.rvMenu.layoutManager = object : GridLayoutManager(requireActivity(), 4) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        val list1 = listOf("", "")
        val adapter1 = MenuAdapter()
        adapter1.setList(list1)
        binding.rvList.adapter = adapter1
        binding.rvList.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)



        binding.rvMenu.post {
            val itemHeight = binding.rvMenu.getChildAt(0).height

            val lp = binding.rvMenu.layoutParams
            if (isFold) {
                lp.height = itemHeight * 2
                binding.ivCollapseExpand.rotation = 180f
            } else {
                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
                binding.ivCollapseExpand.rotation = 0f
            }

            binding.rvMenu.layoutParams = lp

            binding.flCollapseExpand.setOnClickListener {
                toggleFoldOrExpand(true)
            }
        }


    }

    /**
     * 切换状态
     * 如果当前是折叠则展开，当前是展开则折叠
     * @param isAnim 是否打开动画
     */
    private fun toggleFoldOrExpand(isAnim: Boolean) {
        setFoldOrExpand(!isFold, isAnim)

        this.isFold = !isFold
    }

    /**
     * 设置折叠或展开
     * @param fold true:折叠，false :展开
     * @param isAnim 是否打开动画
     */
    private fun setFoldOrExpand(fold: Boolean, isAnim: Boolean = false) {
        val itemHeight = binding.rvMenu.getChildAt(0).height

        val lp = binding.rvMenu.layoutParams

        if (isAnim) {
            val matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                (binding.rvMenu.parent as View).width,
                View.MeasureSpec.EXACTLY
            )

            val wrapContentMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(10000, View.MeasureSpec.AT_MOST)
            binding.rvMenu.measure(
                matchParentMeasureSpec,
                wrapContentMeasureSpec
            )

//            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
//            binding.rvMenu.layoutParams = lp

            val measuredHeight = binding.rvMenu.measuredHeight

            if (fold) {
                val endHeight = itemHeight * 2
                val animator = ValueAnimator.ofInt(measuredHeight, endHeight)
                animator.duration = 300
                animator.addUpdateListener {
                    val animatedValue = it.animatedValue as Int
                    lp.height = animatedValue
                    binding.rvMenu.layoutParams = lp

                }
                animator.addListener(onEnd = {
                    binding.ivCollapseExpand.rotation = 180f
                })

                animator.start()
            } else {
                val startHeight = itemHeight * 2
                val animator = ValueAnimator.ofInt(startHeight, measuredHeight)
                animator.duration = 300
                animator.addUpdateListener {
                    val animatedValue = it.animatedValue as Int
                    lp.height = animatedValue
                    binding.rvMenu.layoutParams = lp
                }
                animator.addListener(onEnd = {
                    binding.ivCollapseExpand.rotation = 0f
                })
                animator.start()
            }


        } else {
            if (fold) {
                lp.height = itemHeight * 2
                binding.rvMenu.layoutParams = lp
            } else {
                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
                binding.rvMenu.layoutParams = lp
            }
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
        fun newInstance(title: String): CollapseFragment {
            val fragment = CollapseFragment()
            val bundle = Bundle()
            bundle.putString("title", title)
            fragment.arguments = bundle
            return fragment
        }
    }


}