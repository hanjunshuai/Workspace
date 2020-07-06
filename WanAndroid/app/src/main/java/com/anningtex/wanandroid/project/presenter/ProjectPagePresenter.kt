package com.anningtex.wanandroid.project.presenter

import com.anningtex.wanandroid.apiservice.ApiService
import com.anningtex.wanandroid.base.mvp.BasePresenter
import com.anningtex.wanandroid.http.BaseObserver
import com.anningtex.wanandroid.project.bean.ProjectResponse
import com.anningtex.wanandroid.project.contract.ProjectPageContract

/**
 *
 * @ClassName:      ProjectPagePresenter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 11:00
 */
class ProjectPagePresenter : BasePresenter<ProjectPageContract.View>(),
    ProjectPageContract.Presenter {
    override fun getProjectLists(page: Int, cid: Int) {
        addSubscribe(
            create(ApiService::class.java).getProjectLists(page, cid),
            object : BaseObserver<ProjectResponse>() {
                override fun onSuccess(data: ProjectResponse?) {
                    if (this@ProjectPagePresenter.isViewAttached()) {
                        this@ProjectPagePresenter.getView()?.onProjectLists(page, data)
                    }
                }
            })
    }
}