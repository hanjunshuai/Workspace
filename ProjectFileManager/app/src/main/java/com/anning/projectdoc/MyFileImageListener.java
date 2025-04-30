package com.anning.projectdoc;

import android.widget.ImageView;

import com.anning.projectdoc.lib.listener.ZFileListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

public class MyFileImageListener extends ZFileListener.ZFileImageListener {

    /**
     * 图片类型加载
     */
    @Override
    public void loadImage(ImageView imageView, File file) {
        Glide.with(imageView.getContext())
                .load(file)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_zfile_other)
                        .error(R.drawable.ic_zfile_other)
                )
                .into(imageView);
    }
}