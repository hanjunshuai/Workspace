package com.anningtex.wanandroid.project.contract

import com.anningtex.wanandroid.base.mvp.IView
import com.anningtex.wanandroid.project.bean.ProjectResponse

/**
 *
 * @ClassName:      ProjectPageContract
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 11:00
 */
interface ProjectPageContract {
    interface View : IView {
        fun onProjectLists(page: Int, response: ProjectResponse?)
    }

    interface Presenter {
        /**
         * 项目列表
         */
        fun getProjectLists(page: Int, cid: Int)
    }
}