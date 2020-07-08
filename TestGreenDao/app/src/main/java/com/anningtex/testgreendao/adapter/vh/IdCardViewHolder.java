package com.anningtex.testgreendao.adapter.vh;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anningtex.testgreendao.R;
import com.anningtex.testgreendao.bean.IdCard;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName: IdCardViewHolder
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/7/8 16:10
 */
public class IdCardViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.green_dao_id_card_name_tv)
    TextView mGreenDaoIdCardNameTv;
    @BindView(R.id.green_dao_id_card_no_tv)
    TextView mGreenDaoIdCardNoTv;

    public IdCardViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void setDataSource(IdCard idCard){
        if (idCard != null) {
            mGreenDaoIdCardNameTv.setText("持有者名字：" + idCard.getUserName());
            mGreenDaoIdCardNoTv.setText("卡号 ：" + idCard.getIdNo() );
        }
    }
}
