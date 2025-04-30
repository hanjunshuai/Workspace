package com.anning.projectdoc.lib.type;

import android.view.View;
import android.widget.ImageView;

import com.anning.projectdoc.lib.common.ZFileType;
import com.anning.projectdoc.lib.content.ZFileContent;

/**
 * 视频文件
 */
public class VideoType extends ZFileType {
    public void openFile(String filePath, View view) {
        ZFileContent.getZFileHelp().getFileOpenListener().openVideo(filePath, view);
    }

    public void loadingFile(String filePath, ImageView pic) {
        ZFileContent.getZFileHelp().getImageLoadListener().loadVideo(pic, ZFileContent.toFile(filePath));
    }
}