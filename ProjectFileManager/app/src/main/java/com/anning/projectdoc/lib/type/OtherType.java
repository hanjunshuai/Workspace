package com.anning.projectdoc.lib.type;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.anning.projectdoc.R;
import com.anning.projectdoc.lib.common.ZFileType;
import com.anning.projectdoc.lib.content.ZFileContent;

/**
 * 其他类型的文件
 */
public class OtherType extends ZFileType {
    public void openFile(@NonNull String filePath, @NonNull View view) {
        ZFileContent.getZFileHelp().getFileOpenListener().openOther(filePath, view);
    }

    public void loadingFile(@NonNull String filePath, @NonNull ImageView pic) {
        pic.setImageResource(this.getRes(ZFileContent.getZFileConfig().getResources().getOtherRes(), R.drawable.ic_zfile_other));
    }
}
