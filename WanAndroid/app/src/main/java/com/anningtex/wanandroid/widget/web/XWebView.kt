package com.anningtex.wanandroid.widget.web

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.webkit.*
import android.widget.FrameLayout
import com.anningtex.wanandroid.util.dp2px

/**
 *
 * @ClassName:      XWebView
 * @Description:    封装 WebView， https://developer.android.google.cn/reference/android/webkit/WebView.html
 * @Author:         alvis
 * @CreateDate:     2020/7/6 8:33
 */
class XWebView : WebView {
    private lateinit var webProgressBar: WebProgressBar
    private var onWebViewCallback: OnWebViewCallback? = null
    private var isProgressBarAdded: Boolean = true    // 当前 ProgressBar 是否已经添加
    private var progressHandler: Handler = Handler(Looper.getMainLooper())
    private var isStarted = false

    companion object {
        fun getFixedContext(context: Context): Context {
            return if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT < 23) context.createConfigurationContext(
                Configuration()
            ) else context
        }
    }

    constructor(context: Context) : super(
        getFixedContext(
            context
        )
    )
    constructor(context: Context, attrs: AttributeSet) : super(
        getFixedContext(
            context
        ), attrs) {
        initWebView(context, attrs)
        initProgressBar()
        webViewClient = XWebViewClient()
        webChromeClient = XWebViewChromeClient()
    }

    fun initProgressBar() {
        webProgressBar =
            WebProgressBar(context)
        webProgressBar.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            dp2px(context, 6f).toInt()
        )
        addView(webProgressBar)
    }

    fun initWebView(context: Context, attrs: AttributeSet) {
        val settings = settings
        settings.javaScriptEnabled = true
        settings.setSupportZoom(true)
        settings.displayZoomControls = false
        settings.builtInZoomControls = true
        settings.allowFileAccess = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.domStorageEnabled = true
        settings.databaseEnabled = true
        settings.setAppCacheEnabled(true)
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.loadsImagesAutomatically = true
        settings.blockNetworkImage = false
        settings.blockNetworkLoads = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
    }

    /**
     * 设置 ProgressBar 是否可见
     */
    fun setProgressBarVisible(visible: Boolean) {
        if (visible) {
            // 没有添加，则执行添加
            if (!isProgressBarAdded) {
                addView(webProgressBar)
            }
        } else {   // 设置不显示 ProgressBar, 如果当前已经添加，则执行移除
            if (isProgressBarAdded) {
                removeView(webProgressBar)
            }
        }
        isProgressBarAdded = visible
    }

    inner class XWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            view?.loadUrl(url)
            return true
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            onWebViewCallback?.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            onWebViewCallback?.onPageFinished(view, url)
        }
    }

    inner class XWebViewChromeClient : WebChromeClient() {
        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            onWebViewCallback?.onReceivedTitle(view, title)
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (isProgressBarAdded) {
                val url = view?.url ?: ""
                webProgressBar.visibility = View.VISIBLE
                val curProgress = webProgressBar.getProgress()
                if (!TextUtils.isEmpty(url)) {
                    Log.e("webView url", url)
                    if (url.startsWith("file")) {
                        startProgressAnimation(newProgress, curProgress)
                        if (newProgress == 100) {
                            progressHandler?.postDelayed(object : Runnable {
                                override fun run() {
                                    webProgressBar.visibility = View.GONE
                                }
                            }, 100)
                        }
                    } else {
                        // 加载网络 html , 进度是反复增加
                        if (newProgress >= 100 && !isStarted) {
                            isStarted = true
                        } else {
                            if (newProgress < curProgress) {
                                return
                            }
                            startProgressAnimation(newProgress, curProgress)
                            if (newProgress == 100) {
                                // 判断页面是否加载完成,不使用 onPageFinished
                                onWebViewCallback?.onPageLoadComplete()
                                progressHandler?.postDelayed(object : Runnable {
                                    override fun run() {
                                        webProgressBar.visibility = View.GONE
                                    }
                                }, 100)
                            }
                        }
                    }
                }
            }
            onWebViewCallback?.onProgressChanged(view, newProgress)
        }
    }

    fun startProgressAnimation(newProgress: Int, curProgress: Int) {
        val animator = ObjectAnimator.ofInt(webProgressBar, "progress", curProgress, newProgress)
        animator.duration = 300
        animator.interpolator = DecelerateInterpolator()
        animator.start()
    }

    fun setWebViewCallback(onWebViewCallback: OnWebViewCallback) {
        this.onWebViewCallback = onWebViewCallback
    }

    interface OnWebViewCallback {
        fun onProgressChanged(view: WebView?, newProgress: Int)

        fun onReceivedTitle(view: WebView?, title: String?)

        fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?)

        fun onPageFinished(view: WebView?, url: String?)

        fun onLoadResource(view: WebView, url: String)

        fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String)

        /**
         * 页面加载完成,不使用 onPageFinish() 因为 onPageFinish() 会被回调两次
         */
        fun onPageLoadComplete()
    }
}