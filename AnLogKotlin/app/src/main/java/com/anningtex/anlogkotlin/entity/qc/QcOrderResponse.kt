package com.anningtex.anlogkotlin.entity.qc

/**
 * @ClassName: QcOrderResponse
 * @Description: 获取qc专属布产单列表
 * @Author: alvis
 * @CreateDate: 2020/4/30 10:58
 */
data class QcOrderResponse(
    val BuyerGroupID: String,
    val ID: String,
    val IsUrgent: String,
    val OLID: String,
    val OrderNo: String,
    val PrintNotes: String,
    val PrintUserName: String,
    val PrintWriteDate: String,
    val PrintWriteUser: String,
    val QSum: Int,
    val SupplierID: String,
    val SupplierName: String,
    val UnitName: String,
    val WeaveNotes: String,
    val WeaveWriteDate: String,
    val WeaveWriteUser: String,
    val WriteUserName: String
) {
    override fun toString(): String {
        return "QcOrderResponse(OLID='$OLID', OrderNo='$OrderNo', QSum=$QSum, SupplierName='$SupplierName', UnitName='$UnitName')"
    }
}