package com.anningtex.wanandroid.gank

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.anningtex.wanandroid.R
import com.anningtex.wanandroid.base.mvp.BaseMVPFragment
import com.anningtex.wanandroid.gank.adapter.GankTodayAdapter
import com.anningtex.wanandroid.gank.adapter.WxPublicAdapter
import com.anningtex.wanandroid.gank.bean.GankToday
import com.anningtex.wanandroid.gank.bean.GankTodayEntity
import com.anningtex.wanandroid.gank.bean.WxPublic
import com.anningtex.wanandroid.gank.contract.GankContract
import com.anningtex.wanandroid.gank.presenter.GankPresenter
import com.anningtex.wanandroid.home.weight.WxPublicRecyclerView
import com.anningtex.wanandroid.util.URL
import com.anningtex.wanandroid.util.gotoActivity
import com.anningtex.wanandroid.web.WebViewActivity
import com.anningtex.wanandroid.widget.LinearItemDecoration
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * A simple [Fragment] subclass.
 */
class GankFragment : BaseMVPFragment<GankContract.View, GankPresenter>(), GankContract.View {
    private lateinit var headerView: View
    private lateinit var wxPublicRecyclerView: WxPublicRecyclerView
    private lateinit var headerImgView: ImageView
    private var gankRecyclerView: RecyclerView? = null
    private var wxPublicAdapter: WxPublicAdapter? = null
    private var gankTodayAdapter: GankTodayAdapter? = null
    private var chooseDateImgView: ImageView? = null

    companion object {
        @JvmStatic
        fun newInstance() = GankFragment()
    }

    override fun getLayoutResId(): Int = R.layout.fragment_gank
    override fun createPresenter(): GankPresenter = GankPresenter()
    override fun initView(rootView: View?, savedInstanceState: Bundle?) {
        headerView = LayoutInflater.from(mContext).inflate(R.layout.layout_gank_header, null, false)
        wxPublicRecyclerView = headerView.findViewById(R.id.rv_gank_header_wxpublic)
        headerImgView = headerView.findViewById(R.id.iv_gank_header_img)
        gankRecyclerView = rootView?.findViewById(R.id.rv_gank_today)
        chooseDateImgView = rootView?.findViewById(R.id.iv_date_choose)

        // 初始化 wxPublicRecyclerView
        wxPublicRecyclerView.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)


        // 初始化 gankRecyclerView
        gankRecyclerView?.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        val itemDecoration =
            LinearItemDecoration(mContext).color(mContext.resources.getColor(R.color.white_eaeaea))
                .height(1f)
                .margin(15f, 15f)
                .jumpPositions(arrayOf(0))
        gankRecyclerView?.addItemDecoration(itemDecoration)
    }

    override fun initData() {
        super.initData()
        presenter.getWxPublic()
        presenter.getGankToday()
        chooseDateImgView?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

            }
        })
    }

    override fun onWxPublic(list: List<WxPublic>?) {
        if (list == null) {
            return
        }
        if (wxPublicAdapter == null) {
            wxPublicAdapter = WxPublicAdapter(R.layout.item_wx_public, list)
            wxPublicAdapter?.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
                override fun onItemClick(
                    adapter: BaseQuickAdapter<*, *>?,
                    view: View?,
                    position: Int
                ) {
                    val bundle = Bundle()
                    bundle.putInt(WxPublicArticleActivity.WXPUBLIC_ID, list[position].id)
                    bundle.putString(WxPublicArticleActivity.WXPUBLIC_TITLE, list[position].name)
                    gotoActivity(mContext as Activity, WxPublicArticleActivity().javaClass, bundle)
                }
            }
            wxPublicRecyclerView.adapter = wxPublicAdapter
        } else {
            wxPublicAdapter?.setNewData(list)
        }
    }

    override fun onGankToday(map: HashMap<String, List<GankToday>>?) {
        Log.e("debug", "map = $map")
        val list: ArrayList<GankTodayEntity<GankToday>> = ArrayList()
        if (map != null) {
            for (key in map.keys) {
                if ("福利" == key) {
                    val meiziImage = map.get(key)?.get(0)?.url
                    Glide.with(mContext).load(meiziImage).into(headerImgView)
//                val matrix = ColorMatrix()

//                matrix.setSaturation(0f)
//                val filter = ColorMatrixColorFilter(matrix)
//                headerImgView.setColorFilter(filter)
                    continue
                }
                list.add(GankTodayEntity(true, key))
                val size = map.get(key)?.size ?: 0
                for (i in 0 until size) {
                    val gankToday = map.get(key)?.get(i)
                    if (gankToday != null) {
                        list.add(GankTodayEntity(gankToday))
                    }
                }
            }
        }
        if (gankTodayAdapter == null) {
            gankTodayAdapter = GankTodayAdapter(
                R.layout.item_gank_section_item,
                R.layout.item_gank_section_header,
                list
            )
            gankTodayAdapter?.addHeaderView(headerView)
            gankTodayAdapter?.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
                override fun onItemClick(
                    adapter: BaseQuickAdapter<*, *>?,
                    view: View?,
                    position: Int
                ) {
                    if (list[position].isHeader) {
                        return
                    }
                    val url = list[position].t.url
                    val bundle = Bundle()
                    bundle.putString(URL, url)
                    gotoActivity(mContext as Activity, WebViewActivity().javaClass, bundle)
                }
            }
            gankRecyclerView?.adapter = gankTodayAdapter
        } else {
            gankTodayAdapter?.setNewData(list)
        }
    }
}
