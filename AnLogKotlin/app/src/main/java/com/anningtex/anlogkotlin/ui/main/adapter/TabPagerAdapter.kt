package com.anningtex.anlogkotlin.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 *
 * @ClassName:      TabPagerAdapter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/16 15:18
 */
class TabPagerAdapter(fm: FragmentManager?, title: List<String>?, views: List<Fragment>?) :
    FragmentPagerAdapter(fm!!) {
    private val title: List<String> = title!!
    private val views: List<Fragment> = views!!
    override fun getItem(position: Int): Fragment {
        return views[position]
    }

    override fun getCount(): Int {
        return views.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return title[position]
    }

}