package com.anningtex.wanandroid.project.presenter

import com.anningtex.wanandroid.apiservice.ApiService
import com.anningtex.wanandroid.base.mvp.BasePresenter
import com.anningtex.wanandroid.http.BaseObserver
import com.anningtex.wanandroid.project.bean.ProjectTab
import com.anningtex.wanandroid.project.contract.ProjectContract

/**
 *
 * @ClassName:      ProjectPresenter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 10:29
 */
class ProjectPresenter : BasePresenter<ProjectContract.View>(), ProjectContract.Presenter {
    override fun getProjectTabs() {
        addSubscribe(
            create(ApiService::class.java).getProjectTabs(),
            object : BaseObserver<List<ProjectTab>>() {
                override fun onSuccess(data: List<ProjectTab>?) {
                    if (this@ProjectPresenter.isViewAttached()) {
                        this@ProjectPresenter.getView()?.onProjectTabs(data)
                    }
                }
            })
    }
}