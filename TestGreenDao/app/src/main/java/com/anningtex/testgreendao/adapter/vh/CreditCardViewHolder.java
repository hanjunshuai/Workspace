package com.anningtex.testgreendao.adapter.vh;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anningtex.testgreendao.R;
import com.anningtex.testgreendao.bean.CreditCard;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName: CreditCardViewHolder
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/7/8 16:10
 */
public class CreditCardViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.green_dao_credit_card_name_tv)
    TextView mGreenDaoCreditCardNameTv;
    @BindView(R.id.green_dao_credit_card_id_tv)
    TextView mGreenDaoCreditCardIdTv;
    @BindView(R.id.green_dao_credit_card_which_bank_tv)
    TextView mGreenDaoCreditCardWhichBankTv;

    public CreditCardViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setDataSource(CreditCard mCreditCardList) {
        mGreenDaoCreditCardNameTv.setText("名字：" + mCreditCardList.getUserName() + " 用户ID: " + mCreditCardList.getStudentId());
        mGreenDaoCreditCardIdTv.setText("卡Id：" + mCreditCardList.getId());
        mGreenDaoCreditCardWhichBankTv.setText("银行：" + mCreditCardList.getWhichBank() + " 卡号： " + mCreditCardList.getCardNum());
    }
}
