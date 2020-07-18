package com.anningtex.anlogkotlin.ui.main.adapter

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.anningtex.anlogkotlin.R
import com.anningtex.anlogkotlin.entity.qc.QcOrderResponse
import com.anningtex.anlogkotlin.listener.OnItemClickListener
import com.anningtex.anlogkotlin.listener.OnLongItemClickListener
import com.anningtex.baselibrary.adapter.BaseRecyclerViewAdapter
import com.anningtex.baselibrary.base.BaseViewHolder

/**
 *
 * @ClassName:      InspectionAdapter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/16 17:30
 */
class InspectionAdapter :
    BaseRecyclerViewAdapter<QcOrderResponse, BaseViewHolder<QcOrderResponse>>() {

    //变色数据的其实位置 position
    private var beginChangePos = 0

    //需要改变颜色的text
    private val text: String? = null

    //text改变的颜色
    private val span: ForegroundColorSpan? = null

    var mOnItemClickListener: OnItemClickListener? = null
    var mOnLongItemClickListener: OnLongItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<QcOrderResponse> =
        BaseViewHolder<QcOrderResponse>(
            LayoutInflater.from(parent.context).inflate(R.layout.item_inspection, parent, false)
        )

    override fun onBindViewHolder(holder: BaseViewHolder<QcOrderResponse>, position: Int) {
        var qcOrderResponse = data?.get(position)
        var clothNumber = holder.getView<TextView>(R.id.item_tv_qc_cloth_number)
        if (text != null) {
            beginChangePos = qcOrderResponse?.OrderNo?.indexOf(text)!!
            var builder: SpannableStringBuilder = SpannableStringBuilder(qcOrderResponse?.OrderNo)
            if (beginChangePos != -1) {
                builder.setSpan(
                    span,
                    beginChangePos,
                    beginChangePos + text.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                clothNumber.text = builder
            }
        } else {
            clothNumber.text = qcOrderResponse?.OrderNo
        }

        if (qcOrderResponse?.IsUrgent.equals("1")) {
            var drawableLeft =
                holder.itemView.context.resources.getDrawable(R.mipmap.icon_expedited)
            clothNumber.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)
        } else {
            clothNumber.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        }
        clothNumber.compoundDrawablePadding = 4

        holder.run {
            setText(R.id.item_tv_qc_factory, qcOrderResponse?.SupplierName)
            setText(
                R.id.item_tv_qc_sum, " ${qcOrderResponse?.QSum}${qcOrderResponse?.UnitName}"
            )

            if (qcOrderResponse?.PrintWriteDate != null) {
                setVisible(R.id.item_tv_qc_print_date, true)
                setText(
                    R.id.item_tv_qc_print_date,
                    "[印] ${qcOrderResponse.PrintWriteDate.replace("\r\n", " ").substring(0, 19)}"

                )
            } else {
                setGone(R.id.item_tv_qc_print_date, true)
                setText(R.id.item_tv_qc_print_date, "")
            }
            if (qcOrderResponse?.WeaveWriteDate != null) {
                setVisible(R.id.item_tv_qc_weave_date, true)
                setText(
                    R.id.item_tv_qc_weave_date,
                    "[坯] ${qcOrderResponse.WeaveWriteDate.replace("\r\n", " ").substring(0, 19)}"
                )
            } else {
                setGone(R.id.item_tv_qc_weave_date, true)
                setText(R.id.item_tv_qc_weave_date, "")
            }

            itemView.setOnClickListener { mOnItemClickListener?.onClickItem(position) }
            itemView.setOnLongClickListener {
                mOnLongItemClickListener?.onLongClickItem(position)
                false
            }
        }
    }
}