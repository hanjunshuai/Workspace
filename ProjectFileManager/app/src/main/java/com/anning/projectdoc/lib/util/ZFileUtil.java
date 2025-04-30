package com.anning.projectdoc.lib.util;

import android.content.Context;
import android.view.View;

import com.anning.projectdoc.lib.Function;
import com.anning.projectdoc.lib.async.ZFileThread;
import com.anning.projectdoc.lib.common.ZFileTypeManage;
import com.anning.projectdoc.lib.content.ZFileBean;
import com.anning.projectdoc.lib.content.ZFileConfiguration;
import com.anning.projectdoc.lib.content.ZFileContent;
import com.anning.projectdoc.lib.listener.ZFileQWFilter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class ZFileUtil {

    /**
     * 获取文件
     */
    public static void getList(Context context, Function bolck) {
        new ZFileThread(context, bolck).start(ZFileContent.getZFileConfig().getFilePath());
    }

    /**
     * 打开文件
     */
    public static void openFile(String filePath, View view) {
        ZFileTypeManage.getTypeManager().openFile(filePath, view);
    }

    /**
     * 查看文件详情
     */
    public static void infoFile(ZFileBean bean, Context context) {
        ZFileTypeManage.getTypeManager().infoFile(bean, context);
    }

    /**
     * 复制文件
     */
    private static boolean copyFile(String sourceFile, String targetFile) {
        boolean success = true;
        File oldFile = new File(sourceFile);
        File outFile = new File(targetFile + "/" + oldFile.getName());

        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = (new FileInputStream(oldFile)).getChannel();
            outputChannel = (new FileOutputStream(outFile)).getChannel();
            if (outputChannel != null) {
                outputChannel.transferFrom((ReadableByteChannel) inputChannel, 0L, inputChannel != null ? inputChannel.size() : 0L);
            }
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        } finally {
            if (inputChannel != null) {
                try {
                    inputChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (outputChannel != null) {
                try {
                    outputChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return success;
        }
    }

    /**
     * 剪切文件
     */
    private static boolean cutFile(String sourceFile, String targetFile) {
        boolean copySuccess = copyFile(sourceFile, targetFile);
        boolean delSuccess = false;
        try {
            delSuccess = new File(sourceFile).delete();
        } catch (Exception e) {
            e.printStackTrace();
            delSuccess = false;
        } finally {
            return copySuccess && delSuccess;
        }
    }

    /**
     * 解压文件
     */
    private static boolean extractFile(String zipFileName, String outPutDir) {
        boolean flag = true;
        try {
            ZipUtils.unZipFolder(zipFileName, outPutDir);
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**
     * 获取文件大小
     */
    public static String getFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        Double byte_size = Double.valueOf(df.format(fileS));
        if (byte_size < 1024) {
            return byte_size + " B";
        }
        Double kb_size = Double.valueOf(df.format(fileS / 1024d));
        if (kb_size < 1024) {
            return kb_size + " KB";
        }
        Double mb_size = Double.valueOf(df.format(fileS / 1048576d));
        if (mb_size < 1024) {
            return mb_size + " MB";
        }
        Double gb_size = Double.valueOf(df.format(fileS / 1073741824d));
        if (gb_size < 1024) {
            return gb_size + " GB";
        }
        return ">1TB";
    }

    /**
     * 获取QQ微信文件
     *
     * @param type          文件类型
     * @param filePathArray 路径
     * @param filterArray   过滤规则
     */
    public static List<ZFileBean> getQWFileData(int type, List<String> filePathArray, String[] filterArray) {
        ArrayList<ZFileBean> list = new ArrayList<ZFileBean>();
        File[] listFiles = null;
        if (filePathArray.size() <= 1) {
            File file = new File(filePathArray.get(0));
            if (file.exists()) {
                listFiles = file.listFiles(new ZFileQWFilter(filterArray, type == ZFileContent.ZFILE_QW_OTHER));
            }
        } else {
            File file1 = new File(filePathArray.get(0));
            File[] list1 = null;
            if (file1.exists()) {
                list1 = file1.listFiles(new ZFileQWFilter(filterArray, type == ZFileContent.ZFILE_QW_OTHER));
            } /*else {
                ZFileLog.e("路径 ${filePathArray[0]} 不存在")
            }*/
            File[] list2 = null;
            File file2 = new File(filePathArray.get(1));
            if (file2.exists()) {
                list2 = file2.listFiles(new ZFileQWFilter(filterArray, type == ZFileContent.ZFILE_QW_OTHER));
            } /*else {
                ZFileLog.e("路径 ${filePathArray[1]} 不存在")
            }*/
            if (list1 != null && list2 != null) {
                listFiles = ZFileContent.concatAll(list1, list2);
            } else {
                if (list1 != null) {
                    listFiles = list1;
                }
                if (list2 != null) {
                    listFiles = list2;
                }
            }
        }

        if (listFiles != null) {
            for (File it : listFiles) {
                if (!it.isHidden()) {
                    ZFileBean bean = new ZFileBean(
                            it.getName(),
                            it.isFile(),
                            it.getPath(),
                            ZFileOtherUtil.getFormatFileDate(it.lastModified()),
                            it.lastModified() + "",
                            getFileSize(it.length()),
                            it.length()
                    );
                    list.add(bean);
                }
            }
        }
        Collections.sort(list, new Comparator<ZFileBean>() {
            @Override
            public int compare(ZFileBean o1, ZFileBean o2) {
                return (int) (o1.getOriginalSize() - o2.getOriginalSize());
            }
        });
        return list;
    }

    public static void resetAll() {
        ZFileConfiguration fileConfig = ZFileContent.getZFileConfig();
        fileConfig.setFilePath("");
        /*fileConfig.setResources(new ZFileConfiguration.ZFileResources());
        fileConfig.setShowHiddenFile(false);
        fileConfig.setSortordBy(ZFileConfiguration.BY_DEFAULT);
        fileConfig.setSortord(ZFileConfiguration.ASC);
        fileConfig.setFileFilterArray(null);
        fileConfig.setMaxSize(10);
        fileConfig.setMaxSizeStr(String.format("您只能选取小于%dM的文件", fileConfig.getMaxSize()));
        fileConfig.setMaxLength(9);
        fileConfig.setMaxLengthStr(String.format("您最多可以选取%d个文件", fileConfig.getMaxLength()));
        fileConfig.setBoxStyle(ZFileConfiguration.STYLE2);
        fileConfig.setNeedLongClick(true);
        fileConfig.setOnlyFileHasLongClick(true);
        fileConfig.setLongClickOperateTitles(null);
        fileConfig.setOnlyFolder(false);
        fileConfig.setOnlyFile(false);*/
    }


}