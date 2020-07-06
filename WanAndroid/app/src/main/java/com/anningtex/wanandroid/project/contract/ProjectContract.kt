package com.anningtex.wanandroid.project.contract

import com.anningtex.wanandroid.base.mvp.IView
import com.anningtex.wanandroid.project.bean.ProjectTab

/**
 *
 * @ClassName:      ProjectContract
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 10:30
 */
interface ProjectContract {
    interface View : IView {
        fun onProjectTabs(projectTabs: List<ProjectTab>?)
    }

    interface Presenter {
        fun getProjectTabs()
    }
}