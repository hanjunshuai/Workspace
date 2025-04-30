package com.anning.projectdoc.lib.common;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.anning.projectdoc.lib.content.ZFileBean;
import com.anning.projectdoc.lib.content.ZFileContent;

public abstract class ZFileType {
    /**
     * 打开文件
     *
     * @param filePath 文件路径
     * @param view     当前视图
     */
    public abstract void openFile(String filePath, View view);

    /**
     * 加载文件
     *
     * @param filePath 文件路径
     * @param pic      文件展示的图片
     */
    public abstract void loadingFile(String filePath, ImageView pic);

    protected final int getRes(int res, int defaultRes) {
        return res == -1 ? defaultRes : res;
    }
}
