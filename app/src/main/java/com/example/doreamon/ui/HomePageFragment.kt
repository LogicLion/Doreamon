package com.example.doreamon.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.TextView
import android.widget.Toast
import com.example.doreamon.R
import com.example.doreamon.base.BaseFragment
import com.example.doreamon.databinding.FragmentHomePageBinding
import com.example.doreamon.entity.VideoLearnData
import com.example.doreamon.ext.toast
import com.example.doreamon.utils.dip2px
import com.example.doreamon.viewmodel.HomePageViewModel
import com.haibin.calendarview.Calendar

/**
 * @author wzh
 * @date 2022/2/23
 */
class HomePageFragment : BaseFragment<HomePageViewModel>() {

    override fun setupLayoutId() = R.layout.fragment_home_page

    override fun initView() {
        val binding = getViewBinding<FragmentHomePageBinding>()

        getViewModel()

        val planView = binding.planView

        planView.setProgressColor(Color.parseColor("#45C4ED"))
        planView.setBgColor(Color.parseColor("#A7E3FF"))

        planView.setProgressAndTotal(20, 30)


//        val group = binding.group
//        group.visibility = View.GONE

        val iv = binding.iv
//

        val webView = binding.webView
        val settings = webView.settings

        settings.javaScriptEnabled=true
        settings.domStorageEnabled = true
        settings.cacheMode = WebSettings.LOAD_NO_CACHE   //设置使用缓存
        webView.webViewClient=webViewClient

        webView.loadUrl("https://www.baidu.com")
    }


    override fun onDestroyView() {
        val binding = getViewBinding<FragmentHomePageBinding>()
        val webView = binding.webView
        val settings = webView.settings

        settings.javaScriptEnabled=false
        super.onDestroyView()
    }

    private val webViewClient: WebViewClient = object : WebViewClient(){
        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
            super.onReceivedError(view, request, error)

            "加载错误".toast()
//            error.errorCode.toString().toast()
            Log.v("errorCode",error.errorCode.toString())
        }

        override fun onReceivedHttpError(
            view: WebView?,
            request: WebResourceRequest?,
            errorResponse: WebResourceResponse?
        ) {
            super.onReceivedHttpError(view, request, errorResponse)
            Log.v("httpErrorCode",errorResponse?.statusCode.toString())

        }
    }


}