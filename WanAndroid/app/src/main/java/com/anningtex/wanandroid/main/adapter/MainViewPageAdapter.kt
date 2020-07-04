package com.anningtex.wanandroid.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.anningtex.wanandroid.R
import com.anningtex.wanandroid.common.bean.FragmentItem

/**
 *
 * @ClassName:      MainViewPageAdapter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 15:21
 */
class MainViewPageAdapter(
    var context: Context,
    fm: FragmentManager,
    var fragmentItems: List<FragmentItem>
) : FragmentPagerAdapter(fm) {

    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItem(position: Int): Fragment {
        return fragmentItems.get(position).fragment
    }

    override fun getCount(): Int {
        return fragmentItems.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentItems.get(position).title
    }

    fun setDataSource(list: List<FragmentItem>) {
        fragmentItems = list
        notifyDataSetChanged()
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

    }

    fun getTabView(position: Int): View {
        val view: View = layoutInflater.inflate(R.layout.layout_main_tab, null, false)
        val textView: TextView = view.findViewById(R.id.tv_tab_title)
        textView.text = getPageTitle(position)
        textView.setTextColor(context.resources.getColor(R.color.color_666))
        textView.textSize = 18f
        return view
    }
}