package com.anning.projectdoc.lib.type;

import android.view.View;
import android.widget.ImageView;

import com.anning.projectdoc.R;
import com.anning.projectdoc.lib.common.ZFileType;
import com.anning.projectdoc.lib.content.ZFileContent;

/**
 * word文件
 */
public class WordType extends ZFileType {
    public void openFile(String filePath, View view) {
        ZFileContent.getZFileHelp().getFileOpenListener().openDOC(filePath, view);
    }

    public void loadingFile(String filePath, ImageView pic) {
        pic.setImageResource(this.getRes(ZFileContent.getZFileConfig().getResources().getWordRes(), R.drawable.ic_zfile_word));
    }
}
