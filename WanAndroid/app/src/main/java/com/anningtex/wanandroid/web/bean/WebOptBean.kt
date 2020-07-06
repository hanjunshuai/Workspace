package com.anningtex.wanandroid.web.bean

import android.os.Parcel
import android.os.Parcelable

/**
 *
 * @ClassName:      WebOptBean
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 9:33
 */
data class WebOptBean(val resId: Int, val title: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(resId)
        dest?.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WebOptBean> {
        override fun createFromParcel(parcel: Parcel): WebOptBean {
            return WebOptBean(parcel)
        }

        override fun newArray(size: Int): Array<WebOptBean?> {
            return arrayOfNulls(size)
        }
    }

}