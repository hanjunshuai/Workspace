package com.anning.projectdoc.lib.common;

import static com.anning.projectdoc.lib.content.ZFileContent.ERROR_MSG;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.io.File;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


import com.anning.projectdoc.lib.content.ZFileBean;
import com.anning.projectdoc.lib.content.ZFileConfiguration;
import com.anning.projectdoc.lib.content.ZFileContent;
import com.anning.projectdoc.lib.listener.ProxyListener;
import com.anning.projectdoc.lib.listener.ZFileDefaultLoadListener;
import com.anning.projectdoc.lib.listener.ZFileListener;
import com.anning.projectdoc.lib.ui.ProxyFragment;
import com.anning.projectdoc.lib.ui.ZFileListActivity;
import com.anning.projectdoc.lib.ui.ZFileQWActivity;
import com.anning.projectdoc.lib.util.PermissionUtil;
import com.anning.projectdoc.lib.util.UriUtils;
import com.anning.projectdoc.lib.util.ZFileOtherUtil;
import com.anning.projectdoc.lib.util.ZFileUtil;

public class ZFileManageHelp {

    /**
     * 图片类型和视频类型的显示方式，必须手动实现 并在调用前或Application中初始化
     */
    private ZFileListener.ZFileImageListener imageLoadListener;
    /**
     * 文件数据获取
     */
    private ZFileListener.ZFileLoadListener fileLoadListener = new ZFileDefaultLoadListener();

    /**
     * QQ or WeChat 文件获取
     */
    private ZFileListener.QWFileLoadListener qwLoadListener;
    /**
     * 文件类型
     */
    private ZFileListener.ZFileTypeListener fileTypeListener = new ZFileListener.ZFileTypeListener();
    /**
     * 打开默认支持的文件
     */
    private ZFileListener.ZFileOpenListener fileOpenListener = new ZFileListener.ZFileOpenListener();
    /**
     * 文件的相关配置信息
     */
    private ZFileConfiguration config = new ZFileConfiguration();

    public ZFileListener.ZFileImageListener getImageLoadListener() {
        if (imageLoadListener == null) {
            throw new NullPointerException("ZFileImageListener is Null, You need call method \"init()\"");
        }
        return imageLoadListener;
    }

    public ZFileManageHelp init(ZFileListener.ZFileImageListener imageLoadeListener) {
        this.imageLoadListener = imageLoadeListener;
        return this;
    }

    public final ZFileListener.ZFileLoadListener getFileLoadListener() {
        return this.fileLoadListener;
    }

    public final ZFileManageHelp setFileLoadListener(ZFileListener.ZFileLoadListener fileLoadListener) {
        this.fileLoadListener = fileLoadListener;
        return this;
    }

    public final ZFileListener.QWFileLoadListener getQWFileLoadListener() {
        return this.qwLoadListener;
    }

    public final ZFileManageHelp setQWFileLoadListener(ZFileListener.QWFileLoadListener qwLoadListener) {
        this.qwLoadListener = qwLoadListener;
        return this;
    }

    public final ZFileListener.ZFileTypeListener getFileTypeListener() {
        return this.fileTypeListener;
    }

    public final ZFileManageHelp setFileTypeListener(ZFileListener.ZFileTypeListener fileTypeListener) {
        this.fileTypeListener = fileTypeListener;
        return this;
    }


    public final ZFileListener.ZFileOpenListener getFileOpenListener() {
        return this.fileOpenListener;
    }

    public final ZFileManageHelp setFileOpenListener(ZFileListener.ZFileOpenListener fileOpenListener) {
        this.fileOpenListener = fileOpenListener;
        return this;
    }

    public final ZFileConfiguration getConfiguration() {
        return this.config;
    }

    public final ZFileManageHelp setConfiguration(ZFileConfiguration config) {
        this.config = config;
        return this;
    }

    /**
     * 获取返回的数据
     */
    public List<ZFileBean> getSelectData(Context context, int requestCode, int resultCode, Intent data) {
        List<ZFileBean> list = new ArrayList<>();
        if (data == null) {
            return list;
        }
        if (requestCode == ZFileContent.ZFILE_REQUEST_CODE) {
            if (data.getClipData() != null) {
                ClipData clipData = data.getClipData();
                int itemCount = clipData.getItemCount();
                for (int i = 0; i < itemCount; i++) {
                    Uri uri = clipData.getItemAt(i).getUri();
                    safToData(context, list, uri);
                }
            } else {
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getData();
                    safToData(context, list, uri);
                } else if (resultCode == ZFileContent.ZFILE_RESULT_CODE) {
                    list = data.getParcelableArrayListExtra(ZFileContent.ZFILE_SELECT_DATA_KEY);
                }
            }
        } else {
            if (data.getClipData() != null) {
                ClipData clipData = data.getClipData();
                int itemCount = clipData.getItemCount();
                for (int i = 0; i < itemCount; i++) {
                    Uri uri = clipData.getItemAt(i).getUri();
                    safToData(context, list, uri);
                }
            } else {
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getData();
                    safToData(context, list, uri);
                } else if (resultCode == ZFileContent.ZFILE_RESULT_CODE) {
                    list = data.getParcelableArrayListExtra(ZFileContent.ZFILE_SELECT_DATA_KEY);
                }
            }
        }
        return list;
    }

    /**
     * SAF框架选择文件后转化为ZFileBean列表
     *
     * @param context
     * @param list
     * @param uri
     */
    private void safToData(Context context, List<ZFileBean> list, Uri uri) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                int dataIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
                int displayNameIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME);
                int mimeTypeIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE);
                int sizeIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE);
                int dateModifiedIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATE_MODIFIED);
                int lastModifiedIndex = cursor.getColumnIndex("last_modified");


                while (cursor.moveToNext()) {
                    String path = "";
                    boolean isDATA = false;
                    if (dataIndex != -1) {
                        path = cursor.getString(dataIndex);
                        isDATA = true;
                    } else if (displayNameIndex != -1) {
                        path = UriUtils.getPathByUri(context, uri);
                    }
                    String mimeType = cursor.getString(mimeTypeIndex);
                    long size = cursor.getLong(sizeIndex);
                    long date = (dateModifiedIndex != -1) ? cursor.getLong(dateModifiedIndex) : (lastModifiedIndex != -1 ? cursor.getLong(lastModifiedIndex) / 1000 : 0);
                    String fileSize = ZFileUtil.getFileSize(size);
                    String lastModified = ZFileOtherUtil.getFormatFileDate(date * (long) 1000);
                    String name = isDATA ? path.substring(path.lastIndexOf("/") + 1) : path;
                    double originSize = size / 1048576d; // byte -> MB
                    ZFileBean bean = new ZFileBean(name, true, path, lastModified, String.valueOf(date), fileSize, size);
                    if (originSize <= config.getMaxSize()) {
                        if (list.size() < config.getMaxLength()) {
                            list.add(bean);
                        } else {
                            Log.e("ZFileManageHelp", "超过配置的maxLength长度文件：" + bean.toString());
                        }
                    } else {
                        Log.e("ZFileManageHelp", "超过配置的maxSize大小文件：" + bean.toString());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public final void start(Object fragmentOrActivity) {
        start(fragmentOrActivity, null);
    }

    /**
     * 跳转至文件管理页面，如果listener为null，则在当前页面的onActivityResult()方法中回调，否则在listener中回调
     *
     * @param fragmentOrActivity
     * @param listener           链式回调数据
     */
    public final void start(Object fragmentOrActivity, ProxyListener listener) {
        //开启代理
        ProxyFragment fragment = null;
        if (fragmentOrActivity instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) fragmentOrActivity;
            fragment = ProxyFragment.beginRequest(activity, ZFileContent.ZFILE_REQUEST_CODE, listener);
        } else if (fragmentOrActivity instanceof Fragment) {
            Fragment f = (Fragment) fragmentOrActivity;
            fragment = ProxyFragment.beginRequest(f, ZFileContent.ZFILE_REQUEST_CODE, listener);
        }
        if (getConfiguration().isUseSAF()) {
            final ProxyFragment finalFragment = fragment;
            PermissionUtil.getInstance().with(fragment)
                    .requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionUtil.PermissionListener() {
                        @Override
                        public void onGranted() {
                            startSAF(finalFragment);
                        }

                        @Override
                        public void onDenied(List<String> deniedPermission) {
                            ZFileContent.toast(finalFragment.getContext(), "权限申请失败");
                        }

                        @Override
                        public void onShouldShowRationale(List<String> deniedPermission) {

                        }
                    });
        } else {
            switch (getConfiguration().getFilePath()) {
                case ZFileConfiguration.QQ:
                    startByQQ(fragment != null ? fragment : fragmentOrActivity);
                    break;
                case ZFileConfiguration.WECHAT:
                    startByWechat(fragment != null ? fragment : fragmentOrActivity);
                    break;
                default:
                    startByFileManager(fragment != null ? fragment : fragmentOrActivity, getConfiguration().getFilePath());
            }
        }
    }

    private void startSAF(Object context) {
        if (context == null) return;
        /*
         * 隐式允许用户选择一种特定类型的数据。
         * Same as : ACTION_GET_CONTENT , ACTION_OPEN_DOCUMENT
         */
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, config.getMaxLength() > 1);
        String mimeType = "";
        //"file/*"比"*/*"少了一些侧边栏选项
        if (config.getFileFilterArray() != null && config.getFileFilterArray().length > 0) {
            intent.setType(TextUtils.isEmpty(mimeType) ? "*/*" : mimeType);
            intent.putExtra(Intent.EXTRA_MIME_TYPES, config.getFileFilterArray());
        } else {
            intent.setType("*/*");
        }
        // Only return URIs that can be opened with ContentResolver
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            activity.startActivityForResult(intent, ZFileContent.ZFILE_REQUEST_CODE);
        } else if (context instanceof Fragment) {
            Fragment fragment = (Fragment) context;
            fragment.startActivityForResult(intent, ZFileContent.ZFILE_REQUEST_CODE);
        }
    }

    private void startByQQ(Object fragmentOrActivity) {
        ArrayMap<String, Object> map = getMap();
        if (fragmentOrActivity instanceof Activity) {
            Activity activity = (Activity) fragmentOrActivity;
            map.put(ZFileContent.QW_FILE_TYPE_KEY, ZFileConfiguration.QQ);
            ZFileContent.jumpActivity(activity, ZFileQWActivity.class, map);
        } else if (fragmentOrActivity instanceof Fragment) {
            Fragment fragment = (Fragment) fragmentOrActivity;
            map.put(ZFileContent.QW_FILE_TYPE_KEY, ZFileConfiguration.QQ);
            ZFileContent.jumpActivity(fragment, ZFileQWActivity.class, map);
        } else {
            throw new IllegalArgumentException(ERROR_MSG);
        }
    }

    private void startByWechat(Object fragmentOrActivity) {
        ArrayMap<String, Object> map = getMap();
        if (fragmentOrActivity instanceof Activity) {
            Activity activity = (Activity) fragmentOrActivity;
            map.put(ZFileContent.QW_FILE_TYPE_KEY, ZFileConfiguration.WECHAT);
            ZFileContent.jumpActivity(activity, ZFileQWActivity.class, map);
        } else if (fragmentOrActivity instanceof Fragment) {
            Fragment fragment = (Fragment) fragmentOrActivity;
            map.put(ZFileContent.QW_FILE_TYPE_KEY, ZFileConfiguration.WECHAT);
            ZFileContent.jumpActivity(fragment, ZFileQWActivity.class, map);
        } else {
            throw new IllegalArgumentException(ERROR_MSG);
        }
    }

    private void startByFileManager(Object fragmentOrActivity, String path) {
        String newPath = "";
        if (TextUtils.isEmpty(path)) {
            newPath = ZFileContent.getSD_ROOT();
        } else {
            newPath = path;
        }
        if (!new File(newPath).exists()) {
            throw new NullPointerException(String.format("%s 路径不存在", newPath));
        }
        ArrayMap<String, Object> map = getMap();
        if (fragmentOrActivity instanceof Activity) {
            Activity activity = (Activity) fragmentOrActivity;
            map.put(ZFileContent.FILE_START_PATH_KEY, path);
            ZFileContent.jumpActivity(activity, ZFileListActivity.class, map);
        } else if (fragmentOrActivity instanceof Fragment) {
            Fragment fragment = (Fragment) fragmentOrActivity;
            map.put(ZFileContent.FILE_START_PATH_KEY, path);
            ZFileContent.jumpActivity(fragment, ZFileListActivity.class, map);
        } else {
            throw new IllegalArgumentException(ERROR_MSG);
        }
    }

    //根据自定义对象 文件名排序 （文件夹在上面，文件在下面）
    public static void sortFilesByInfo(List<ZFileBean> fileList) {
        Collections.sort(fileList, (o1, o2) -> {
            if (!o1.isFile() && (o2.isFile()))
                return -1;
            if ((o1.isFile()) && !o2.isFile())
                return 1;
            return Collator.getInstance(Locale.getDefault()).compare(o1.getFileName(), o2.getFileName());
        });
    }

    private ArrayMap<String, Object> getMap() {
        return new ArrayMap<>();
    }

    public static ZFileManageHelp getInstance() {
        return Builder.MANAGER;
    }

    public static final class Builder {

        private static final ZFileManageHelp MANAGER;

        public static final Builder INSTANCE;

        public static final ZFileManageHelp getMANAGER() {
            return MANAGER;
        }

        static {
            Builder builder = new Builder();
            INSTANCE = builder;
            MANAGER = new ZFileManageHelp();
        }
    }
}
