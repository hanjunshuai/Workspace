package com.anning.projectdoc.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anning.projectdoc.R;
import com.anning.projectdoc.adapter.FileTypeAdapter;
import com.anning.projectdoc.lib.content.ZFileBean;
import com.anning.projectdoc.lib.content.ZFileConfiguration;
import com.anning.projectdoc.lib.content.ZFileContent;
import com.anning.projectdoc.lib.listener.ZFileListener;
import com.anning.projectdoc.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderListActivity extends AppCompatActivity {
    public static void launchActivity(Context context) {
        Intent intent = new Intent(context, FolderListActivity.class);
        context.startActivity(intent);
    }

    private FileTypeAdapter fileTypeAdapter;
    private List<String> fileTypeList;


    private static final String DOC = "application/msword";
    private static final String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    private static final String XLS = "application/vnd.ms-excel";
    private static final String XLS1 = "application/x-excel";
    private static final String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final String PPT = "application/vnd.ms-powerpoint";
    private static final String PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    private static final String PDF = "application/pdf";
    private static final String MP4 = "video/mp4";
    private static final String M3U8 = "application/x-mpegURL";

    private static final int REQUEST_CODE_FILE = 985 << 2;
    private static final int REQUEST_PHONE_STATE = 211 << 2;

    private final String[] fileSuffix = {".pptx", ".ppt", ".xlsx", ".docx", ".xls", ".doc", ".pdf"};
    private final String[] videoSuffix = {".m3u8", ".mp4"};
    TextView tv;
    String type;
    AlertDialog.Builder dialog;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_list);

//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
//        fileTypeAdapter = new FileTypeAdapter();
//        recyclerView.setAdapter(fileTypeAdapter);
//
//        fileTypeList = new ArrayList<>();
//        fileTypeList.add("DOC");
//        fileTypeList.add("PPT");
//        fileTypeList.add("XLS");
//        fileTypeList.add("PDF");
//        fileTypeList.add("TXT");
//        fileTypeAdapter.setData(fileTypeList);
        AppCompatTextView tvPhone = findViewById(R.id.tv_phone);
        AppCompatTextView tvWechat = findViewById(R.id.tv_wechat);
        tvPhone.setOnClickListener(view -> {
            final ZFileConfiguration configuration = new ZFileConfiguration.Build()
                    .boxStyle(ZFileConfiguration.STYLE2)
                    .maxLength(2)
                    .maxLengthStr("亲，最多选2个！")
                    .useSAF(false)
                    .build();
            ZFileContent.getZFileHelp()
                    .setConfiguration(configuration)
                    .start(FolderListActivity.this);
        });

        tvWechat.setOnClickListener(v -> {
            ZFileConfiguration zFileConfig = ZFileContent.getZFileConfig();
            zFileConfig.setBoxStyle(ZFileConfiguration.STYLE2);
            zFileConfig.setFilePath(ZFileConfiguration.WECHAT);
            ZFileContent.getZFileHelp().setConfiguration(zFileConfig).start(this);
        });


        tv = findViewById(R.id.text);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionRead = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionRead == PackageManager.PERMISSION_GRANTED) {
                grantSuccess();
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PHONE_STATE);
            }
        } else {
            grantSuccess();
        }
        findViewById(R.id.btn_file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile(true);
            }
        });
        findViewById(R.id.btn_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile(false);
            }
        });
        findViewById(R.id.btn_file_path).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFileWithPath();
            }
        });
        findViewById(R.id.btn_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePic();
            }
        });

        LinearLayout layoutDoc = findViewById(R.id.layout_doc);
        LinearLayout layoutPpt = findViewById(R.id.layout_ppt);
        LinearLayout layoutXls = findViewById(R.id.layout_xls);
        LinearLayout layoutPdf = findViewById(R.id.layout_pdf);

        layoutDoc.setOnClickListener(view -> {
        });
        layoutPpt.setOnClickListener(view -> {
        });
        layoutXls.setOnClickListener(view -> {
        });
        layoutPdf.setOnClickListener(view -> {
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<ZFileBean> list = ZFileContent.getZFileHelp().getSelectData(getBaseContext(), requestCode, resultCode, data);
        StringBuilder sb = new StringBuilder();
        for (ZFileBean bean : list) {
            sb.append(bean).append("\n\n");
        }
        Log.e("TAG", sb.toString());

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_FILE) {
            Uri uri = data.getData();
            tv.setText("");
            String s = uri.toString() + "  \n " + uri.getPath() + " \n " + uri.getAuthority();
            tv.setText(s);
            Log.i("-----", s);

            if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
                path = uri.getPath();
            } else {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                    path = FileUtil.getPath(this, uri);
                } else {//4.4以下下系统调用方法
                    path = FileUtil.getRealPathFromURI(this, uri);
                }
            }

            //uri.getLastPathsegment()不一定能获取到文件名
            //content://com.android.providers.media.documents/document/video:5186
            //必须要通过path去判断

            String name = path.toLowerCase();
            if (!checkFileType(name)) {
                Toast.makeText(this, "暂不支持文件类型", Toast.LENGTH_SHORT).show();
                return;
            }


            doSomething();
        }
        //其他情况自行处理
    }

    /**
     * 选择图片文件
     */
    private void choosePic() {
        Intent intent;
        intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_CODE_FILE);
    }

    /**
     * 根据类型，加载文件选择器
     */
    private void chooseFileWithPath() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        String[] mimeTypes = {DOC, DOCX, PDF, PPT, PPTX, XLS, XLS1, XLSX};

        //跳转指定路径，如果路径不存在，切到sdcard
        //需要读权限
        String path = getSDPath();
        if (!TextUtils.isEmpty(path)) {
            path = path + File.separator + "tencent/MicroMsg/Download";
            File file = new File(path);
            if (file.exists()) {
                intent.setDataAndType(FileUtil.getUriFromFile(this, new File(path)), "application/*");
            } else {
                intent.setType("application/*");
            }
        } else {
            intent.setType("application/*");
        }

        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, REQUEST_CODE_FILE);
    }

    /**
     * 根据类型，加载文件选择器
     */
    private void chooseFile(boolean isFile) {
        //如果使用系统文件选择器，可以实现文件类型过滤，三方的文件选择器不可用。腾讯，es等
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        String[] mimeTypes = {DOC, DOCX, PDF, PPT, PPTX, XLS, XLS1, XLSX};
        if (!isFile) {
            mimeTypes = new String[]{MP4, M3U8};
        }
        intent.setType(isFile ? "application/*;*.xls" : "video/mp4;*.m3u8");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, REQUEST_CODE_FILE);
    }

    /**
     * 检查文件类型
     *
     * @param fileName
     * @return
     */
    private boolean checkFileType(String fileName) {
        if ("video".equals(type)) {
            for (String suffix : videoSuffix) {
                if (fileName.endsWith(suffix)) {
                    return true;
                }
            }
        } else {
            for (String suffix : fileSuffix) {
                if (fileName.endsWith(suffix)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 向js返回数据
     */
    private void doSomething() {
        tv.setText(tv.getText() + "\n----dosomething----\n" + path);
    }

    /**
     * @return
     */
    public String getSDPath() {
        String path = "";
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            File sdDir = Environment.getExternalStorageDirectory();//获取跟目录
            path = sdDir.toString();
        }
        return path;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults == null || grantResults.length == 0) {
            return;
        }
        if (requestCode == REQUEST_PHONE_STATE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                grantSuccess();
            } else {
                if (dialog == null) {
                    dialog = new AlertDialog.Builder(FolderListActivity.this);
                    dialog.setMessage("我们需要您的允许获取读取存储权限");
                    dialog.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PHONE_STATE);
                        }
                    }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            grantSuccess();
                        }
                    }).show();
                }
            }
        }
    }

    /**
     * 授权成功
     */
    private void grantSuccess() {

    }

}