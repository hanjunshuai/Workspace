package com.anningtex.anlogkotlin.ui.main.fragment


import androidx.recyclerview.widget.LinearLayoutManager
import com.anningtex.anlogkotlin.R
import com.anningtex.anlogkotlin.base.BaseLazyFragment
import com.anningtex.anlogkotlin.entity.qc.DeliveryGoodsResponse
import com.anningtex.anlogkotlin.entity.qc.goods.FirstNode
import com.anningtex.anlogkotlin.entity.qc.goods.SecondNode
import com.anningtex.anlogkotlin.entity.qc.goods.ThirdNode
import com.anningtex.anlogkotlin.listener.OnNodeItemClickListener
import com.anningtex.anlogkotlin.ui.main.adapter.DeliveryGoodsAdapter
import com.anningtex.anlogkotlin.ui.main.contract.DeliveryGoodsContract
import com.anningtex.anlogkotlin.ui.main.presenter.DeliveryGoodsPresenter
import com.chad.library.adapter.base.entity.node.BaseNode
import kotlinx.android.synthetic.main.fragment_delivery_goods.*
import java.util.*

/**
 * 装车 Fragment
 */
class DeliveryGoodsFragment :
    BaseLazyFragment<DeliveryGoodsContract.View, DeliveryGoodsPresenter>(),
    DeliveryGoodsContract.View {

    var deliveryGoodsAdapter: DeliveryGoodsAdapter? = null
    override val layoutResId: Int = R.layout.fragment_delivery_goods

    override fun createPresenter(): DeliveryGoodsPresenter = DeliveryGoodsPresenter()

    override fun loadData() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        deliveryGoodsAdapter = DeliveryGoodsAdapter()
        recyclerView.adapter = deliveryGoodsAdapter
        mPresenter.getDeliveryGoods()

        deliveryGoodsAdapter?.mFirstProvider?.mOnNodeItemClickListener =
            object : OnNodeItemClickListener {
                override fun onItemClick(data: BaseNode?, position: Int) {
                    val entity = data as FirstNode
                    toast("mFirstProvider${entity.deliveryNo}")
                }
            }
        deliveryGoodsAdapter?.mThirdProvider?.mOnNodeItemClickListener =
            object : OnNodeItemClickListener {
                override fun onItemClick(data: BaseNode?, position: Int) {
                    val entity = data as ThirdNode
                    toast("mFirstProvider${entity.orderNo}")
                }
            }
    }

    override fun setDeliveryGoods(data: MutableList<DeliveryGoodsResponse>) {
        val list: MutableList<BaseNode> = ArrayList()
        for (i in data.indices) {
            var deliveryGoodsResponse = data[i]
            val secondNodeList: MutableList<BaseNode> = ArrayList()

            for (j in deliveryGoodsResponse.supplier.indices) {
                var supplier = deliveryGoodsResponse.supplier[j]

                val thirdNodeList: MutableList<BaseNode> = ArrayList()
                for (index in deliveryGoodsResponse.list_GoodsList.indices) {
                    val goodsBean = deliveryGoodsResponse.list_GoodsList[index]
                    if (goodsBean.supplierName == supplier) {
                        val node = ThirdNode()
                        node.orderNo = goodsBean.orderNo
                        node.orderId = goodsBean.olid
                        node.deliveryNo = deliveryGoodsResponse.deliveryNo
                        node.metersPerBale =
                            "${goodsBean.metersPerBale}${goodsBean.metersPerBaleUnitName}"
                        node.qalesQuantity = goodsBean.balesQuantity.toString() + "包"
                        node.notes = goodsBean.notes

                        if (index == deliveryGoodsResponse.list_GoodsList.size - 1) {
                            node.isLast = true
                        }
                        thirdNodeList.add(node)
                    }
                }

                val seNode = SecondNode(deliveryGoodsResponse.supplier[j])
                seNode.childNode = thirdNodeList
                secondNodeList.add(seNode)
            }

            val entity = FirstNode()
            entity.childNode = secondNodeList
            entity.deliveryNo = deliveryGoodsResponse.deliveryNo
            entity.loadDate =
                if (deliveryGoodsResponse.planDate != null) deliveryGoodsResponse.planDate
                    .substring(0, 10) else ""
            entity.planDate =
                if (deliveryGoodsResponse.loadDate != null) deliveryGoodsResponse.loadDate
                    .substring(0, 10) else ""
            list.add(entity)
        }

        deliveryGoodsAdapter?.addData(list)
    }

}
