package com.anningtex.wanandroid.project.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.anningtex.wanandroid.common.bean.FragmentItem

/**
 *
 * @ClassName:      ProjectPageAdapter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 10:41
 */
class ProjectPageAdapter(fm: FragmentManager, var fragmentItems: List<FragmentItem>) :
    FragmentPagerAdapter(fm) {

    fun setDataSource(list: List<FragmentItem>) {
        fragmentItems = list
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return fragmentItems[position].fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentItems[position].title
    }

    override fun getCount(): Int {
        return fragmentItems.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return super.instantiateItem(container, position)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    }
}