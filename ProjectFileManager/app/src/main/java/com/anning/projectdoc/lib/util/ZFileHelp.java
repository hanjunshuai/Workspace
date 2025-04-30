package com.anning.projectdoc.lib.util;

import com.anning.projectdoc.lib.common.ZFileType;
import com.anning.projectdoc.lib.common.ZFileTypeManage;
import com.anning.projectdoc.lib.content.ZFileContent;

import java.io.File;

public final class ZFileHelp {

    public static String getFileSize(String filePath) {
        return ZFileUtil.getFileSize(ZFileContent.toFile(filePath).length());
    }

    public static ZFileType getFileType(String filePath) {
        return ZFileTypeManage.getTypeManager().getFileType(filePath);
    }

    public static String getFileTypeBySuffix(String filePath) {
        return ZFileContent.getFileType(filePath);
    }


    public static String getFormatFileDate(File file) {
        return ZFileOtherUtil.getFormatFileDate(file.lastModified());
    }
}
