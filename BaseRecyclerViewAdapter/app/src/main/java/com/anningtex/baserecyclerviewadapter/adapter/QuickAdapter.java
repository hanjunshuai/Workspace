package com.anningtex.baserecyclerviewadapter.adapter;

import android.content.Context;

import com.anningtex.baserecyclerviewadapter.R;
import com.anningtex.baserecyclerviewadapter.data.DataServer;
import com.anningtex.baserecyclerviewadapter.entity.Status;
import com.anningtex.library.BaseQuickAdapter;
import com.anningtex.library.BaseViewHolder;

/**
 * @ClassName: QuickAdapter
 * @Description: 快速写法
 * @Author: alvis
 * @CreateDate: 2020/6/8 11:20
 */
public class QuickAdapter extends BaseQuickAdapter<Status> {
    public QuickAdapter(Context context) {
        super(context, R.layout.tweet, DataServer.getSampleData(100));
    }

    public QuickAdapter(Context context, int dataSize) {
        super(context, R.layout.tweet, DataServer.getSampleData(dataSize));
    }

    @Override
    protected void convert(BaseViewHolder helper, Status item) {
        helper.setText(R.id.tweetName, item.getUserName())
                .setText(R.id.tweetText, item.getText())
                .setText(R.id.tweetDate, item.getCreatedAt())
                .setImageUrl(R.id.tweetAvatar, item.getUserAvatar())
                .setVisible(R.id.tweetRT, item.isRetweet())
                .linkify(R.id.tweetText);
    }
}
