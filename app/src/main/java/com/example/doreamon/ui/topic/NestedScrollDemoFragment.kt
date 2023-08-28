package com.example.doreamon.ui.topic

import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doreamon.R
import com.example.doreamon.adapter.RvAdapter
import com.example.module_base.base.BaseFragment
import com.example.module_base.base.BaseViewModel
import com.example.doreamon.databinding.FragmentNestedscrollDemoBinding
import com.example.doreamon.entity.InfoBean

/**
 * @author wzh
 * @date 2022/5/11
 */
class NestedScrollDemoFragment : com.example.module_base.base.BaseFragment<com.example.module_base.base.BaseViewModel>() {

    private lateinit var mainBinding:FragmentNestedscrollDemoBinding
    override fun setupLayoutId() = R.layout.fragment_nestedscroll_demo

    override fun initView() {
        mainBinding = getViewBinding()
        initWebView()
        initRecyclerView()
        initToolBarView()
    }


    private fun initToolBarView() {

//        mainBinding.vToolBar.setOnClickListener(View.OnClickListener {
//            mainBinding.nestedContainer.scrollToTarget(
//                mainBinding.rvList
//            )
//        })
    }

    private fun initWebView() {
        mainBinding.webContainer.getSettings().setJavaScriptEnabled(true)
        mainBinding.webContainer.setWebViewClient(WebViewClient())
        mainBinding.webContainer.setWebChromeClient(WebChromeClient())
        mainBinding.webContainer.loadUrl("https://github.com/wangzhengyi/Android-NestedDetail")
//        if (false) {
//            // 测试JS通知内容高度回调
//            mainBinding.webContainer.post(Runnable {
//                val contentHeight = TypedValue.applyDimension(
//                    TypedValue.COMPLEX_UNIT_DIP,
//                    200f,
//                    resources.displayMetrics
//                ).toInt()
//                mainBinding.webContainer.setJsCallWebViewContentHeight(contentHeight)
//            })
//        }
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        mainBinding.rvList.setLayoutManager(layoutManager)
        val data: List<InfoBean> = getCommentData()
        val rvAdapter = RvAdapter(requireActivity(), data)
        mainBinding.rvList.setAdapter(rvAdapter)
    }

    private fun getCommentData(): List<InfoBean> {
        val commentList: MutableList<InfoBean> = ArrayList<InfoBean>()
        val titleBean = InfoBean()
        titleBean.type = InfoBean.TYPE_TITLE
        titleBean.title = "评论列表"
        commentList.add(titleBean)
        for (i in 0..39) {
            val contentBean = InfoBean()
            contentBean.type = InfoBean.TYPE_ITEM
            contentBean.title = "评论标题$i"
            contentBean.content = "评论内容$i"
            commentList.add(contentBean)
        }
        return commentList
    }
}