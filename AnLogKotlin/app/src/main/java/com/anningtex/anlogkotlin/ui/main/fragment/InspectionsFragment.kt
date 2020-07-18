package com.anningtex.anlogkotlin.ui.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anningtex.anlogkotlin.R
import com.anningtex.anlogkotlin.base.BaseLazyFragment
import com.anningtex.anlogkotlin.entity.qc.QcOrderResponse
import com.anningtex.anlogkotlin.listener.OnItemClickListener
import com.anningtex.anlogkotlin.listener.OnLongItemClickListener
import com.anningtex.anlogkotlin.ui.main.adapter.InspectionAdapter
import com.anningtex.anlogkotlin.ui.main.contract.InspectionsContract
import com.anningtex.anlogkotlin.ui.main.presenter.InspectionsPresenter
import com.anningtex.anlogkotlin.weight.actionbar.TitleBar
import com.google.android.material.navigation.NavigationView

/**
 * A simple [Fragment] subclass.
 */
class InspectionsFragment(
    drawerLayout: DrawerLayout?,
    navigationView: NavigationView?
) : BaseLazyFragment<InspectionsContract.View, InspectionsPresenter>(),
    InspectionsContract.View {
    var drawerLayout: DrawerLayout? = drawerLayout
    var navigationView: NavigationView? = navigationView
    var titleBar: TitleBar? = null
    var recyclerView: RecyclerView? = null
    var inspectionAdapter: InspectionAdapter? = null
    var presenter: InspectionsPresenter? = null

    override val layoutResId: Int
        get() = R.layout.fragment_inspection_list

    override fun createPresenter(): InspectionsPresenter = InspectionsPresenter()

    override fun initView(rootView: View?, savedInstanceState: Bundle?) {
        super.initView(rootView, savedInstanceState)
        initTitle(rootView)
        initRecycler(rootView)
        onViewListener()
    }

    private fun onViewListener() {
        titleBar?.setLeftListener {
            drawerLayout!!.openDrawer(navigationView!!)
        }
        titleBar?.setRightListener {
            toast("筛选")
        }
    }

    private fun initTitle(view: View?) {
        titleBar = view?.findViewById(R.id.title_bar)
    }

    private fun initRecycler(view: View?) {
        recyclerView = view?.findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = GridLayoutManager(context, 2)
        inspectionAdapter = InspectionAdapter()
        recyclerView?.adapter = inspectionAdapter
        inspectionAdapter?.mOnItemClickListener = object : OnItemClickListener {
            override fun onClickItem(position: Int) {
                toast("onClickItem :  ${inspectionAdapter?.data?.get(position)?.OrderNo}")
            }
        }
        inspectionAdapter?.mOnLongItemClickListener = object : OnLongItemClickListener {
            override fun onLongClickItem(position: Int) {
                toast("onLongClickItem :  ${inspectionAdapter?.data?.get(position)?.OrderNo}")
            }
        }
    }

    override fun loadData() {
        mPresenter.attachView(this)
        mPresenter.getQcOrder()
    }

    override fun setQcOrders(data: MutableList<QcOrderResponse>) {
        inspectionAdapter?.setData(data)
    }

}
