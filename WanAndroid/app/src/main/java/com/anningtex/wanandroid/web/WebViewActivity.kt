package com.anningtex.wanandroid.web

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.anningtex.wanandroid.R
import com.anningtex.wanandroid.base.mvp.BaseMVPActivity
import com.anningtex.wanandroid.util.*
import com.anningtex.wanandroid.web.bean.WebOptBean
import com.anningtex.wanandroid.web.contract.WebContract
import com.anningtex.wanandroid.web.presenter.WebPresenter
import com.anningtex.wanandroid.widget.web.WebDialogFragment
import com.anningtex.wanandroid.widget.web.XWebView

class WebViewActivity : BaseMVPActivity<WebContract.View, WebPresenter>(), WebContract.View {
    private lateinit var toolbar: Toolbar
    private var webView: XWebView? = null
    private var moreMenuItem: MenuItem? = null
    private var title: String? = null
    private var link: String? = null
    private var id: Int? = -1
    private var author: String? = null
    private var dialogFragment: WebDialogFragment? = null
    private var loadUrl: String? = null
    private var favoriteSuccessView: View? = null
    private var appId: String = "wx2c753629bd2e94bd"
    override fun getLayoutResId(): Int = R.layout.activity_web_view

    override fun createPresenter(): WebPresenter = WebPresenter()

    override fun initView() {
        toolbar = findViewById(R.id.tb_web)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar?.elevation = dp2px(mContext, 5f)
        toolbar.setNavigationOnClickListener {
            goBack()
        }
        webView = findViewById(R.id.pwv_webview)
    }

    override fun initData() {
        super.initData()
        val bundle: Bundle? = intent.extras
        loadUrl = bundle?.getString(URL)
        id = bundle?.getInt(ID)
        link = bundle?.getString(LINK)
        title = bundle?.getString(TITLE)
        author = bundle?.getString(AUTHOR)
        webView?.loadUrl(loadUrl)
        webView?.setWebViewCallback(object : XWebView.OnWebViewCallback {
            override fun onPageFinished(view: WebView?, url: String?) {
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                Log.e("debug", "progres = $newProgress")
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                toolbar.title = title
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            }

            override fun onLoadResource(view: WebView, url: String) {
            }

            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
            }

            override fun onPageLoadComplete() {
                moreMenuItem?.isVisible = true
            }
        })
    }

    /**
     * 创建菜单
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_web, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * 获取菜单项
     */
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        moreMenuItem = menu?.findItem(R.id.item_more)
        // 默认是不显示的,页面加载完成才显示
        moreMenuItem?.isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }

    /**
     * 菜单项点击事件
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.item_more) {
            showMoreDialog()
        }
        return true
    }

    private fun showMoreDialog() {
        val dataList = ArrayList<WebOptBean>()
        dataList.add(WebOptBean(R.drawable.ic_favorite_white, "收藏"))
        dataList.add(WebOptBean(R.drawable.ic_share, "朋友圈"))
        dataList.add(WebOptBean(R.drawable.ic_wx_friend, "微信好友"))
        dataList.add(WebOptBean(R.drawable.ic_link, "复制链接"))
        dataList.add(WebOptBean(R.drawable.ic_refresh, "刷新"))
        dataList.add(WebOptBean(R.drawable.ic_browser, "浏览器打开"))
        if (dialogFragment == null) {
            dialogFragment = WebDialogFragment.newInstance(dataList)
        }
        dialogFragment?.setOnItemClickListener(object : WebDialogFragment.OnItemClickListener {
            override fun onItemClick(position: Int) {
                when (position) {
                    0 -> addArticleFavorite()
//                    1 -> shareToWeChat(SendMessageToWX.Req.WXSceneTimeline)
//                    2 -> shareToWeChat(SendMessageToWX.Req.WXSceneSession)
                    3 -> copyLink()
                    4 -> refreshPage()
                    5 -> openByBrowser()
                }
            }
        })
        val dialog = dialogFragment?.dialog
        val isShowing = dialog?.isShowing ?: false
        if (isShowing) {
            return
        }
        dialogFragment?.show(supportFragmentManager, WebDialogFragment().javaClass.name)
    }

    /**
     * 文章收藏
     */
    private fun addArticleFavorite() {
        presenter.addFavorite(id ?: -1, title ?: "", author ?: "", link ?: "")
    }

    /**
     * 浏览器打开
     */
    private fun openByBrowser() {
        val uri = Uri.parse(loadUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    /**
     * 刷新
     */
    private fun refreshPage() {
        webView?.reload()
    }

    /**
     * 拷贝链接
     */
    private fun copyLink() {
        // 获取剪贴板管理器：
        val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        // 创建普通字符型ClipData
//        val clipData = ClipData.newPlainText("Label", "baidu")
        // 创建链接型 clipData
        val clipData = ClipData.newRawUri("Label", Uri.parse(loadUrl))
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(clipData)
        Toast.makeText(mContext, "已复制至剪切板", Toast.LENGTH_LONG).show()
    }


    /**
     * 返回
     */
    private fun goBack() {
        val canGoBack = webView?.canGoBack() ?: false
        if (canGoBack) {
            webView?.goBack()
        } else {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        webView?.removeAllViews()
        webView?.clearHistory()
        webView = null
    }

    override fun onBackPressed() {
        super.onBackPressed()
        goBack()
    }

}
