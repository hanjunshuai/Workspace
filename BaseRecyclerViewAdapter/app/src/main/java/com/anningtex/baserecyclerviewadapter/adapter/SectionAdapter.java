package com.anningtex.baserecyclerviewadapter.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.anningtex.baserecyclerviewadapter.R;
import com.anningtex.baserecyclerviewadapter.entity.MySection;
import com.anningtex.library.BaseSectionQuickAdapter;
import com.anningtex.library.BaseViewHolder;

import java.util.List;

/**
 * @ClassName: SectionAdapter
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/6/8 17:48
 */
public class SectionAdapter extends BaseSectionQuickAdapter<MySection> {
    public SectionAdapter(Context context, int layoutResId, int sectionHeadResId, List data) {
        super(context, layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, final MySection item) {
        helper.setText(R.id.header, item.header);
        if (!item.isMroe) helper.setVisible(R.id.more, false);
        else
            helper.setOnClickListener(R.id.more, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, item.header + "more..", Toast.LENGTH_LONG).show();
                }
            });
    }


    @Override
    protected void convert(BaseViewHolder helper, MySection item) {
        helper.setImageUrl(R.id.iv, (String) item.t);
    }
}
