package com.anningtex.baserecyclerviewadapter.adapter;

import android.content.Context;

import com.anningtex.baserecyclerviewadapter.R;
import com.anningtex.baserecyclerviewadapter.entity.HomeItem;
import com.anningtex.library.BaseQuickAdapter;
import com.anningtex.library.BaseViewHolder;

import java.util.List;

/**
 * @ClassName: HomeAdapter
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/6/8 16:41
 */
public class HomeAdapter extends BaseQuickAdapter<HomeItem> {
    public HomeAdapter(Context context, int layoutResId, List<HomeItem> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeItem item) {
        helper.setText(R.id.info_text, item.title);
    }
}
