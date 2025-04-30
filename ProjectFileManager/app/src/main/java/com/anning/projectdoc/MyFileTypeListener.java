package com.anning.projectdoc;


import com.anning.projectdoc.lib.common.ZFileType;
import com.anning.projectdoc.lib.content.ZFileContent;
import com.anning.projectdoc.lib.listener.ZFileListener;
import com.anning.projectdoc.lib.util.ZFileHelp;

public class MyFileTypeListener extends ZFileListener.ZFileTypeListener {

    @Override
    public ZFileType getFileType(String filePath) {
        switch (ZFileHelp.getFileTypeBySuffix(filePath)) {
            case ZFileContent.TXT:
            case ZFileContent.XML:
            case ZFileContent.JSON:
                return new MyTxtType();
            default:
                return super.getFileType(filePath);
        }
    }
}