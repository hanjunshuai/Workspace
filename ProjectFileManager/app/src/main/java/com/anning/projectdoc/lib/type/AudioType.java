package com.anning.projectdoc.lib.type;

import android.view.View;
import android.widget.ImageView;

import com.anning.projectdoc.R;
import com.anning.projectdoc.lib.common.ZFileType;
import com.anning.projectdoc.lib.content.ZFileContent;

/**
 * 音频文件
 */
public class AudioType extends ZFileType {
    public void openFile(String filePath, View view) {
        ZFileContent.getZFileHelp().getFileOpenListener().openAudio(filePath, view);
    }

    public void loadingFile(String filePath, ImageView pic) {
        int resId = ZFileContent.getZFileConfig().getResources().getAudioRes();
        pic.setImageResource(this.getRes(ZFileContent.getZFileConfig().getResources().getAudioRes(), R.drawable.ic_zfile_audio));
    }
}
