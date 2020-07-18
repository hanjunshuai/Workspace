package com.anningtex.anlogkotlin.base

import com.anningtex.baselibrary.base.AbsBaseContract

/**
 *
 * @ClassName:      BaseControct
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/16 9:03
 */
interface BaseContract {
    interface View : AbsBaseContract.View
    interface Presenter<V : View> : AbsBaseContract.Presenter<V>
}