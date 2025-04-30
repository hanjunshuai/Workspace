package com.anning.projectdoc.lib.listener;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.anning.projectdoc.R;
import com.anning.projectdoc.lib.common.ZFileType;
import com.anning.projectdoc.lib.content.ZFileBean;
import com.anning.projectdoc.lib.content.ZFileContent;
import com.anning.projectdoc.lib.dialog.ZFileAudioPlayDialog;
import com.anning.projectdoc.lib.dialog.ZFileSelectFolderDialog;
import com.anning.projectdoc.lib.type.ApkType;
import com.anning.projectdoc.lib.type.AudioType;
import com.anning.projectdoc.lib.type.ImageType;
import com.anning.projectdoc.lib.type.OtherType;
import com.anning.projectdoc.lib.type.PdfType;
import com.anning.projectdoc.lib.type.PptType;
import com.anning.projectdoc.lib.type.TxtType;
import com.anning.projectdoc.lib.type.VideoType;
import com.anning.projectdoc.lib.type.WordType;
import com.anning.projectdoc.lib.type.XlsType;
import com.anning.projectdoc.lib.type.ZipType;
import com.anning.projectdoc.lib.ui.ZFileListActivity;
import com.anning.projectdoc.lib.ui.ZFilePicActivity;
import com.anning.projectdoc.lib.ui.ZFileVideoPlayActivity;
import com.anning.projectdoc.lib.util.ZFileHelp;
import com.anning.projectdoc.lib.util.ZFileOpenUtil;

import java.io.File;
import java.util.List;

public class ZFileListener {
    /**
     * 获取文件数据
     */
    public interface ZFileLoadListener {

        /**
         * 获取手机里的文件List
         *
         * @param filePath String           指定的文件目录访问，空为SD卡根目录
         * @return MutableList<ZFileBean>?  list
         */
        List<ZFileBean> getFileList(Context context, String filePath);
    }

    /**
     * 图片或视频 显示
     */
    public abstract static class ZFileImageListener {

        /**
         * 图片类型加载
         */
        public abstract void loadImage(ImageView imageView, File file);

        /**
         * 视频类型加载
         */
        public void loadVideo(ImageView imageView, File file) {
            loadImage(imageView, file);
        }
    }

    /**
     * QQ 或 WeChat 获取
     */
    public abstract static class QWFileLoadListener {

        /**
         * 获取标题
         *
         * @return Array<String>
         */
        public String[] getTitles() {
            return null;
        }

        /**
         * 获取过滤规则
         *
         * @param fileType Int      文件类型 see [ZFILE_QW_PIC] [ZFILE_QW_MEDIA] [ZFILE_QW_DOCUMENT] [ZFILE_QW_OTHER]
         */
        public abstract String[] getFilterArray(int fileType);

        /**
         * 获取 QQ 或 WeChat 文件路径
         *
         * @param qwType   String         QQ 或 WeChat  see [ZFileConfiguration.QQ] [ZFileConfiguration.WECHAT]
         * @param fileType Int          文件类型 see [ZFILE_QW_PIC] [ZFILE_QW_MEDIA] [ZFILE_QW_DOCUMENT] [ZFILE_QW_OTHER]
         * @return MutableList<String>  文件路径集合（因为QQ或WeChat保存的文件可能存在多个路径）
         */
        public abstract List<String> getQWFilePathArray(String qwType, int fileType);

        /**
         * 获取数据
         *
         * @param fileType        Int                          文件类型 see [ZFILE_QW_PIC] [ZFILE_QW_MEDIA] [ZFILE_QW_DOCUMENT] [ZFILE_QW_OTHER]
         * @param qwFilePathArray MutableList<String>   QQ 或 WeChat 文件路径集合
         * @param filterArray     Array<String>             过滤规则
         */
        public abstract List<ZFileBean> getQWFileDatas(int fileType, List<String> qwFilePathArray, String[] filterArray);

    }

    /**
     * 文件类型
     */
    public static class ZFileTypeListener {

        public ZFileType getFileType(String filePath) {
            switch (ZFileHelp.getFileTypeBySuffix(filePath)) {
                case ZFileContent.PNG:
                case ZFileContent.JPG:
                case ZFileContent.JPEG:
                case ZFileContent.GIF:
                    return new ImageType();
                case ZFileContent.MP3:
                case ZFileContent.AAC:
                case ZFileContent.WAV:
                    return new AudioType();
                case ZFileContent.MP4:
                case ZFileContent._3GP:
                    return new VideoType();
                case ZFileContent.TXT:
                case ZFileContent.XML:
                case ZFileContent.JSON:
                    return new TxtType();
                case ZFileContent.ZIP:
                    return new ZipType();
                case ZFileContent.DOC:
                    return new WordType();
                case ZFileContent.XLS:
                    return new XlsType();
                case ZFileContent.PPT:
                    return new PptType();
                case ZFileContent.PDF:
                    return new PdfType();
                case ZFileContent.APK:
                    return new ApkType();
                default:
                    return new OtherType();
            }
        }

    }

    /**
     * 打开文件
     */
    public static class ZFileOpenListener {

        /**
         * 打开音频
         */
        public void openAudio(String filePath, View view) {
            if (view.getContext() instanceof AppCompatActivity) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                String tag = "ZFileAudioPlayDialog";
                ZFileContent.checkFragmentByTag((AppCompatActivity) view.getContext(), tag);
                ZFileAudioPlayDialog.newInstance(filePath).show(activity.getSupportFragmentManager(), tag);
            }
        }

        /**
         * 打开图片
         */
        public void openImage(String filePath, View view) {
            Intent intent = new Intent(view.getContext(), ZFilePicActivity.class);
            intent.putExtra("picFilePath", filePath);
            ActivityOptions activityOptions = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                activityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) view.getContext(), view,
                        ZFileContent.getStringById(view.getContext(), R.string.zfile_sharedElement_pic));
                view.getContext().startActivity(intent, activityOptions.toBundle());
            } else {
                view.getContext().startActivity(intent);
            }
        }

        /**
         * 打开视频
         */
        public void openVideo(String filePath, View view) {
            Intent intent = new Intent(view.getContext(), ZFileVideoPlayActivity.class);
            intent.putExtra("videoFilePath", filePath);
            ActivityOptions activityOptions = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                activityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) view.getContext(), view,
                        ZFileContent.getStringById(view.getContext(), R.string.zfile_sharedElement_video));
                view.getContext().startActivity(intent, activityOptions.toBundle());
            } else {
                view.getContext().startActivity(intent);
            }
        }

        /**
         * 打开Txt
         */
        public void openTXT(String filePath, View view) {
            ZFileOpenUtil.openTXT(filePath, view);
        }

        /**
         * 打开zip
         */
        public void openZIP(final String filePath, final View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("请选择");
            builder.setItems(new String[]{"打开", "解压"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        ZFileOpenUtil.openZIP(filePath, view);
                    } else {
                        zipSelect(filePath, view);
                    }
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }

        public void zipSelect(final String filePath, final View view) {
            if (view.getContext() instanceof ZFileListActivity) {
                final ZFileListActivity it = (ZFileListActivity) view.getContext();
                ZFileContent.checkFragmentByTag(it, "ZFileSelectFolderDialog");
                ZFileSelectFolderDialog dialog = ZFileSelectFolderDialog.newInstance("解压");
                dialog.setSelectFolderListener(new ZFileSelectFolderDialog.SelectFolderListener() {
                    @Override
                    public void invoke(String folder) {

                    }
                });
                dialog.show(it.getSupportFragmentManager(), "ZFileSelectFolderDialog");
            }
        }

        /**
         * 打开word
         */
        public void openDOC(String filePath, View view) {
            ZFileOpenUtil.openDOC(filePath, view);
        }

        /**
         * 打开表格
         */
        public void openXLS(String filePath, View view) {
            ZFileOpenUtil.openXLS(filePath, view);
        }

        /**
         * 打开PPT
         */
        public void openPPT(String filePath, View view) {
            ZFileOpenUtil.openPPT(filePath, view);
        }

        /**
         * 打开PDF
         */
        public void openPDF(String filePath, View view) {
            ZFileOpenUtil.openPDF(filePath, view);
        }

        public void openOther(String filePath, View view) {
            Log.e("ZFileManageHelp", String.format("【%s】不支持预览该文件 ---> %s", ZFileContent.getFileType(filePath), filePath));
            ZFileContent.toast(view, "暂不支持预览该文件");
        }
    }
}