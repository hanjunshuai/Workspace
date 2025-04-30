package com.anning.projectdoc.lib.type;

import android.view.View;
import android.widget.ImageView;

import com.anning.projectdoc.R;
import com.anning.projectdoc.lib.common.ZFileType;
import com.anning.projectdoc.lib.content.ZFileContent;

/**
 * 表格文件
 */
public class XlsType extends ZFileType {
    public void openFile(String filePath, View view) {
        ZFileContent.getZFileHelp().getFileOpenListener().openXLS(filePath, view);
    }

    public void loadingFile(String filePath, ImageView pic) {
        pic.setImageResource(this.getRes(ZFileContent.getZFileConfig().getResources().getExcelRes(), R.drawable.ic_zfile_excel));
    }
}
