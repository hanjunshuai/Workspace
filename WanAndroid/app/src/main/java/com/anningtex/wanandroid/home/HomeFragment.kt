package com.anningtex.wanandroid.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.anningtex.wanandroid.R
import com.anningtex.wanandroid.base.BaseLazyFragment
import com.anningtex.wanandroid.home.adapter.HomeRecyclerAdapter
import com.anningtex.wanandroid.home.bean.Article
import com.anningtex.wanandroid.home.contract.HomeContract
import com.anningtex.wanandroid.home.presenter.HomePresenter
import com.anningtex.wanandroid.project.ProjectFragment
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.youth.banner.Banner

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseLazyFragment<HomeContract.View, HomePresenter>(), HomeContract.Presenter {
    private lateinit var adapter: HomeRecyclerAdapter
    private lateinit var banner: Banner
    private var recyclerView: RecyclerView? = null
    private var refreshLayout: SmartRefreshLayout? = null
    private lateinit var headerView: View
    private var mCurPage: Int = 0
    private var dataList: List<Article> = ArrayList()

    companion object {
        @JvmStatic
        fun newInstance() = ProjectFragment()

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

    override fun loadData() {
    }


}
