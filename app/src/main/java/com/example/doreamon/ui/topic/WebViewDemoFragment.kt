package com.example.doreamon.ui.topic

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.webkit.*
import com.example.doreamon.R
import com.doreamon.treasure.base.BaseFragment
import com.doreamon.treasure.base.BaseViewModel
import com.example.doreamon.databinding.FragmentWebviewDemoBinding

/**
 * webView实践
 * 1.加载错误重试
 * @author wzh
 * @date 2022/4/26
 */
class WebViewDemoFragment : BaseFragment<BaseViewModel>(){

    var isLoadFinish = false
    var isLoadError = false

    var reLoadUrl: String? = null

    override fun setupLayoutId()= R.layout.fragment_webview_demo

    override fun initView() {

        val binding = getViewBinding<FragmentWebviewDemoBinding>()
        val webView = binding.webView
        val settings = webView.settings

        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.cacheMode = WebSettings.LOAD_NO_CACHE   //设置使用缓存
        webView.webViewClient = webViewClient
        webView.webChromeClient = webChromeClient

        webView.loadUrl("https://www.baidu.com")

        val btnReload = binding.btnReload
        btnReload.setOnClickListener {

            val url = reLoadUrl
            if (url != null) {
                Log.v("webview", "loadUrl:" + reLoadUrl)
                webView.loadUrl(url)
            }
        }
    }


    override fun onDestroyView() {
        val binding = getViewBinding<FragmentWebviewDemoBinding>()
        val webView = binding.webView
        val settings = webView.settings

        settings.javaScriptEnabled = false
        super.onDestroyView()
    }


    private val webViewClient: WebViewClient = object : WebViewClient() {
        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
            super.onReceivedError(view, request, error)

            Log.v("webview", "onReceivedError:" + error.errorCode + ",url:" + request.url)

            if (request.isForMainFrame) {
                reLoadUrl = request.url.toString()
                isLoadError = true
            }


        }

        override fun onReceivedHttpError(
            view: WebView,
            request: WebResourceRequest,
            errorResponse: WebResourceResponse
        ) {
            super.onReceivedHttpError(view, request, errorResponse)
            Log.v("httpErrorCode", errorResponse.statusCode.toString() + ",url:" + request.url)


        }

        override fun onPageFinished(view: WebView?, url: String) {
            super.onPageFinished(view, url)

            Log.v("webview", "onPageFinished")
            isLoadFinish = true

            val viewBinding = getViewBinding<FragmentWebviewDemoBinding>()
            val btnReload = viewBinding.btnReload
            val webView = viewBinding.webView
            if (isLoadError) {
                btnReload.visibility = View.VISIBLE
                webView.visibility = View.GONE
            } else {
                btnReload.visibility = View.GONE
                webView.visibility = View.VISIBLE
            }


        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)

            Log.v("webview", "onPageStarted")

            isLoadFinish = false
            isLoadError = false
        }


        override fun shouldInterceptRequest(
            view: WebView,
            request: WebResourceRequest
        ): WebResourceResponse? {
            return super.shouldInterceptRequest(view, request)
        }

    }

    private val webChromeClient: WebChromeClient = object : WebChromeClient() {

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)

//            Log.v("webview", "newProgress" + newProgress)
        }
    }
}