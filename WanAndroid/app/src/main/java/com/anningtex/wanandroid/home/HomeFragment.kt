package com.anningtex.wanandroid.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.anningtex.wanandroid.R
import com.anningtex.wanandroid.base.mvp.BaseMVPFragment
import com.anningtex.wanandroid.home.adapter.HomeRecyclerAdapter
import com.anningtex.wanandroid.home.bean.Article
import com.anningtex.wanandroid.home.contract.HomeContract
import com.anningtex.wanandroid.home.presenter.HomePresenter
import com.anningtex.wanandroid.main.MainActivity
import com.anningtex.wanandroid.project.ProjectFragment
import com.anningtex.wanandroid.util.*
import com.anningtex.wanandroid.web.WebViewActivity
import com.anningtex.wanandroid.widget.LinearItemDecoration
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.youth.banner.Banner
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.loader.ImageLoader

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseMVPFragment<HomeContract.View, HomePresenter>(), HomeContract.View {
    private lateinit var adapter: HomeRecyclerAdapter
    private lateinit var banner: Banner
    private var recyclerView: RecyclerView? = null
    private var refreshLayout: SmartRefreshLayout? = null
    private lateinit var headerView: View
    private var mCurPage: Int = 0
    private var dataList: List<Article> = ArrayList()

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()

    }

    override fun getLayoutResId(): Int = R.layout.fragment_home

    override fun createPresenter(): HomePresenter = HomePresenter()

    override fun initView(rootView: View?, savedInstanceState: Bundle?) {
        refreshLayout = rootView?.findViewById(R.id.srl_home)
        refreshLayout?.setEnableRefresh(true)
        recyclerView = rootView?.findViewById(R.id.rv_home)

        headerView = layoutInflater.inflate(R.layout.layout_home_header, null, false)
        banner = headerView.findViewById(R.id.banner)
    }

    override fun initData() {
        super.initData()
        val itemDecoration = LinearItemDecoration(mContext).color(
            ContextCompat.getColor(
                mContext,
                R.color.white_eaeaea
            )
        )
            .height(1f)
            .margin(15f, 15f)
            .jumpPositions(arrayOf(0))
        recyclerView?.addItemDecoration(itemDecoration)
        recyclerView?.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        adapter = HomeRecyclerAdapter(R.layout.item_home_recycler)

        adapter.addHeaderView(headerView)
        // recyclerview 点击监听
        adapter.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                val bundle = Bundle()
                val bean = dataList[position]
                bundle.putString(URL, bean.link)
                bundle.putInt(ID, bean.id)
                bundle.putString(AUTHOR, bean.author)
                bundle.putString(LINK, bean.link)
                bundle.putString(TITLE, bean.title)
                gotoActivity(
                    activity!!,
                    WebViewActivity().javaClass,
                    bundle
                )
            }
        recyclerView?.adapter = adapter

        // 获取 banner
        presenter.getBanner()
        // 获取文章
        presenter.getArticles(mCurPage)

        setListener()

    }

    private fun setListener() {
    }

    override fun onBanner(data: List<com.anningtex.wanandroid.home.bean.Banner>?) {
        val urlList = mutableListOf<String>()
        if (data != null) {
            for (banner in data) {
                urlList.add(banner.imagePath)
            }
        }
        banner.setImageLoader(object : ImageLoader() {
            override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                val roundedCorners = RoundedCorners(20)
                val bitmapTransform = RequestOptions.bitmapTransform(roundedCorners)
                Glide.with(context!!).load(path).apply(bitmapTransform).into(imageView!!)
            }
        })

        banner.setImages(urlList).isAutoPlay(true).start()

        banner.setOnBannerListener(object : OnBannerListener {
            override fun OnBannerClick(position: Int) {
                if (data != null) {
                    val bundle = Bundle()
                    val banner = data[position]
                    bundle.putString(URL, banner.url)
                    bundle.putInt(ID, banner.id)
                    gotoActivity(activity!!, WebViewActivity().javaClass, bundle)
                }
            }
        })

    }

    override fun onArticles(page: Int, data: List<Article>?) {
        refreshLayout?.finishRefresh()
        refreshLayout?.finishLoadMore()
        mCurPage = page + 1
        if (data != null) {
            dataList = data
        }
        adapter.setNewData(dataList)
    }

}
