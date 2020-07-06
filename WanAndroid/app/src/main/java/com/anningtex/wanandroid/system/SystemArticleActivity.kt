package com.anningtex.wanandroid.system

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anningtex.wanandroid.R
import com.anningtex.wanandroid.base.mvp.BaseMVPActivity
import com.anningtex.wanandroid.home.adapter.HomeRecyclerAdapter
import com.anningtex.wanandroid.home.bean.Article
import com.anningtex.wanandroid.system.contract.SystemArticleContract
import com.anningtex.wanandroid.system.presenter.SystemArticlePresenter
import com.anningtex.wanandroid.util.*
import com.anningtex.wanandroid.web.WebViewActivity
import com.anningtex.wanandroid.widget.LinearItemDecoration
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener

class SystemArticleActivity : BaseMVPActivity<SystemArticleContract.View, SystemArticlePresenter>(),
    SystemArticleContract.View {
    private lateinit var refreshLayout: SmartRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: HomeRecyclerAdapter
    private lateinit var toolbar: Toolbar
    private var dataList = ArrayList<Article>()
    private var mCurPage: Int = 0
    private var cid: Int = 0

    override fun getLayoutResId(): Int = R.layout.activity_system_article

    override fun createPresenter(): SystemArticlePresenter = SystemArticlePresenter()

    override fun initView() {
        toolbar = findViewById(R.id.tb_system_article)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
        refreshLayout = findViewById(R.id.srl_system_article)
        refreshLayout.setEnableRefresh(false)
        recyclerView = findViewById(R.id.rv_system_article)
    }

    override fun initData() {
        super.initData()
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false)
        val itemDecoration =
            LinearItemDecoration(mContext).color(mContext.resources.getColor(R.color.white_eaeaea))
                .height(1f)
                .margin(15f, 15f)
        recyclerView.addItemDecoration(itemDecoration)
        recyclerView.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        mAdapter = HomeRecyclerAdapter(R.layout.item_home_recycler)
        recyclerView.adapter = mAdapter

        val bundle: Bundle? = intent.extras
        cid = bundle?.getInt(CID) ?: 0
        val title = bundle?.getString(TITLE)
        supportActionBar?.title = title
        presenter.getSystemArticles(0, cid)
        setListener()
    }

    private fun setListener() {
        mAdapter.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val bundle = Bundle()
                val bean = dataList[position]
                bundle.putString(URL, bean.link)
                bundle.putInt(ID, bean.id)
                bundle.putString(AUTHOR, bean.author)
                bundle.putString(LINK, bean.link)
                bundle.putString(TITLE, bean.title)
                gotoActivity(
                    mContext as Activity,
                    WebViewActivity().javaClass,
                    bundle
                )
            }
        }
        refreshLayout.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                presenter.getSystemArticles(mCurPage, cid)
            }
        })
    }

    override fun onSystemArticles(page: Int, list: List<Article>?) {
        refreshLayout.finishLoadMore()
        mCurPage = page + 1
        if (list != null) {
            dataList.addAll(list)
        }
        mAdapter.setNewData(dataList)
    }
}
