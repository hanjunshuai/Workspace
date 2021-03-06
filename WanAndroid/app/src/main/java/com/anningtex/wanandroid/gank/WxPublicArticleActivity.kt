package com.anningtex.wanandroid.gank

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anningtex.wanandroid.R
import com.anningtex.wanandroid.base.mvp.BaseMVPActivity
import com.anningtex.wanandroid.gank.adapter.WxPublicArticleAdapter
import com.anningtex.wanandroid.gank.contract.WxPublicArticleContract
import com.anningtex.wanandroid.gank.presenter.WxPublicArticlePresenter
import com.anningtex.wanandroid.home.bean.Article
import com.anningtex.wanandroid.util.*
import com.anningtex.wanandroid.web.WebViewActivity
import com.anningtex.wanandroid.widget.LinearItemDecoration
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener

class WxPublicArticleActivity :
    BaseMVPActivity<WxPublicArticleContract.View, WxPublicArticlePresenter>(),
    WxPublicArticleContract.View {
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var refreshLayout: SmartRefreshLayout
    private var mCurPage: Int = 1
    private lateinit var wxPublicArticleAdapter: WxPublicArticleAdapter
    private var dataList = arrayListOf<Article>()
    private var id = -1

    companion object {
        const val WXPUBLIC_ID = "wxpublic_id"
        const val WXPUBLIC_TITLE = "wxpublic_title"
    }

    override fun getLayoutResId(): Int = R.layout.activity_wx_public_article

    override fun createPresenter(): WxPublicArticlePresenter = WxPublicArticlePresenter()

    override fun initView() {
        toolbar = findViewById(R.id.tb_wxpublic)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = dp2px(mContext, 5f)
        toolbar.setNavigationOnClickListener { finish() }
        refreshLayout = findViewById(R.id.srl_wxpublic_article)
        recyclerView = findViewById(R.id.rv_wxpublic_article)
        refreshLayout.setEnableRefresh(false)
        refreshLayout.setEnableLoadMore(true)
    }

    override fun initData() {
        super.initData()
        recyclerView.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        val itemDecoration =
            LinearItemDecoration(mContext).color(mContext.resources.getColor(R.color.white_eaeaea))
                .height(1f)
                .margin(15f, 15f)
                .jumpPositions(arrayOf(0))
        recyclerView.addItemDecoration(itemDecoration)
        wxPublicArticleAdapter = WxPublicArticleAdapter(R.layout.item_home_recycler, dataList)
        wxPublicArticleAdapter.setOnItemClickListener(object :
            BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val bundle = Bundle()
                val bean = dataList[position]
                bundle.putString(URL, bean.link)
                bundle.putInt(ID, bean.id)
                bundle.putString(AUTHOR, bean.author)
                bundle.putString(LINK, bean.link)
                bundle.putString(TITLE, bean.title)
                gotoActivity(mContext as Activity, WebViewActivity().javaClass, bundle)
            }
        })
        recyclerView.adapter = wxPublicArticleAdapter
        val bundle = intent.extras
        if (bundle != null) {
            id = bundle.getInt(WXPUBLIC_ID)
            val title = bundle.getString(WXPUBLIC_TITLE)
            supportActionBar?.title = title
            presenter.getWxPublicArticle(id, mCurPage)
        }
        refreshLayout.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                presenter.getWxPublicArticle(id, mCurPage)
            }
        })
    }

    override fun onWxPublicArticle(page: Int, list: List<Article>?) {
        refreshLayout.finishLoadMore()
        mCurPage = page + 1
        if (list == null) {
            return
        }
        dataList.addAll(list)
        wxPublicArticleAdapter.setNewData(dataList)
    }
}
