package com.anningtex.wanandroid.system

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.anningtex.wanandroid.R
import com.anningtex.wanandroid.base.mvp.BaseMVPFragment
import com.anningtex.wanandroid.system.adapter.SystemCategoryAdapter
import com.anningtex.wanandroid.system.adapter.SystemContentAdapter
import com.anningtex.wanandroid.system.bean.SystemCategory
import com.anningtex.wanandroid.system.contract.SystemContract
import com.anningtex.wanandroid.system.presenter.SystemPresenter
import com.anningtex.wanandroid.util.CID
import com.anningtex.wanandroid.util.TITLE
import com.anningtex.wanandroid.util.gotoActivity
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * A simple [Fragment] subclass.
 */
class SystemFragment : BaseMVPFragment<SystemContract.View, SystemPresenter>(),
    SystemContract.View {
    private var categoryRecyclerView: RecyclerView? = null
    private var contentRecyclerView: RecyclerView? = null
    private lateinit var categoryAdapter: SystemCategoryAdapter
    private lateinit var contentAdapter: SystemContentAdapter
    private var dataList: List<SystemCategory> = ArrayList()

    companion object {
        @JvmStatic
        fun newInstance() = SystemFragment()
    }

    override fun getLayoutResId(): Int = R.layout.fragment_system
    override fun createPresenter(): SystemPresenter = SystemPresenter()

    override fun initView(rootView: View?, savedInstanceState: Bundle?) {
        categoryRecyclerView = rootView?.findViewById(R.id.rv_system_category)
        contentRecyclerView = rootView?.findViewById(R.id.rv_system_content)
    }

    override fun initData() {
        super.initData()
        // Left menu
        categoryRecyclerView?.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        categoryAdapter = SystemCategoryAdapter(R.layout.item_system_category)
        categoryAdapter.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                categoryAdapter.setClickedPosition(position)
                val systemCategory = dataList[position]
                contentAdapter.setNewData(systemCategory.children)
            }
        }
        categoryRecyclerView?.adapter = categoryAdapter

        // Content
        contentRecyclerView?.layoutManager = GridLayoutManager(mContext, 2)
        contentAdapter = SystemContentAdapter(R.layout.item_system_content)
        contentAdapter.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val cid = contentAdapter.data[position].id
                val title = contentAdapter.data[position].name
                val bundle = Bundle()
                bundle.putInt(CID, cid)
                bundle.putString(TITLE, title)
                gotoActivity(mContext as Activity, SystemArticleActivity().javaClass, bundle)
            }
        }
        contentRecyclerView?.adapter = contentAdapter

        // 请求数据
        presenter.getSystemCategory()
    }

    override fun onSystemCategory(data: List<SystemCategory>?) {
        if (data != null) {
            dataList = data
        }
        categoryAdapter.setNewData(dataList)
        contentAdapter.setNewData(dataList[0].children)
    }
}
