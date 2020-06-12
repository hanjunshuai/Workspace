package com.anningtex.baserecyclerviewadapter.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.anningtex.baserecyclerviewadapter.R;
import com.anningtex.baserecyclerviewadapter.entity.MultipleItem;
import com.anningtex.library.BaseMultiItemQuickAdapter;
import com.anningtex.library.BaseViewHolder;

import java.util.List;

/**
 * @ClassName: MultipleItemQuickAdapter
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/6/12 14:45
 */
public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem> {
    public MultipleItemQuickAdapter(Context context, List data) {
        super(context, data);
        addItemType(MultipleItem.TEXT, R.layout.text_view);
        addItemType(MultipleItem.IMG, R.layout.image_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem multipleItem) {
        switch (helper.getItemViewType()) {
            case MultipleItem.TEXT:
                TextView textView = helper.getView().findViewById(R.id.item_tv);
                textView.setText(multipleItem.getContent());
                helper.setText(R.id.item_tv, multipleItem.getContent());
                break;
            case MultipleItem.IMG:
                helper.setImageUrl(R.id.iv, multipleItem.getContent());
                break;
        }
    }
}
