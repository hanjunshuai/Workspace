package com.anningtex.wanandroid.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.anningtex.wanandroid.R
import com.anningtex.wanandroid.base.mvp.BaseMVPFragment
import com.anningtex.wanandroid.common.bean.FragmentItem
import com.anningtex.wanandroid.project.adapter.ProjectPageAdapter
import com.anningtex.wanandroid.project.bean.ProjectTab
import com.anningtex.wanandroid.project.contract.ProjectContract
import com.anningtex.wanandroid.project.presenter.ProjectPresenter
import com.anningtex.wanandroid.project.weight.ProjectViewPager
import com.google.android.material.tabs.TabLayout

/**
 * A simple [Fragment] subclass.
 * Use the [ProjectFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProjectFragment : BaseMVPFragment<ProjectContract.View, ProjectPresenter>(),
    ProjectContract.View {
    private lateinit var adapter: ProjectPageAdapter

    override fun getLayoutResId(): Int = R.layout.fragment_project

    override fun createPresenter(): ProjectPresenter = ProjectPresenter()

    companion object {
        @JvmStatic
        fun newInstance() = ProjectFragment()
    }

    override fun initView(rootView: View?, savedInstanceState: Bundle?) {
        val tabLayout: TabLayout? = rootView?.findViewById(R.id.tl_project_tabs)
        val viewPager: ProjectViewPager? = rootView?.findViewById(R.id.vp_project_pager)
        val fragmentList = mutableListOf<FragmentItem>()
        adapter = ProjectPageAdapter(childFragmentManager, fragmentList)
        viewPager?.adapter = adapter
        tabLayout?.setupWithViewPager(viewPager)
    }

    override fun initData() {
        super.initData()
        presenter.getProjectTabs()
    }

    override fun onProjectTabs(projectTabs: List<ProjectTab>?) {
        val projectTabsList = getFragmentItems(projectTabs)
        adapter.setDataSource(projectTabsList)
    }

    private fun getFragmentItems(projectTabs: List<ProjectTab>?): List<FragmentItem> {
        val list = mutableListOf<FragmentItem>()
        if (projectTabs != null) {
            for (projectTab in projectTabs) {
                list.add(
                    FragmentItem(
                        projectTab.name, ProjectPageFragment.newInstance(projectTab.id)
                    )
                )
            }
        }
        return list
    }

}
