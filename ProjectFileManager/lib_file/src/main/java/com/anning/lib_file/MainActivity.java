package com.anning.lib_file;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.InputStream;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.text).setOnClickListener(view -> {
            ChooseFile.create(this)
                    .setUIConfig(new ChooseFileUIConfig.Builder().build())
                    .setTypeFilter(new IFileTypeFilter() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public List<ChooseFileInfo> doFilter(List<ChooseFileInfo> list) {
                            list.stream().filter(new Predicate<ChooseFileInfo>() {
                                        @Override
                                        public boolean test(ChooseFileInfo item) {
                                            //只要文档文件
                                            return item.fileType == ChooseFile.FILE_TYPE_FOLDER ||
                                                    item.fileType == ChooseFile.FILE_TYPE_TEXT ||
                                                    item.fileType == ChooseFile.FILE_TYPE_PDF;
                                        }
                                    })
                                    .collect(Collectors.toList());
                            return list;
                        }
                    })
                    .forResult(info -> {
                        Toast.makeText(this, "选中的文件：" + info.fileName, Toast.LENGTH_SHORT).show();
                        Uri uri = Uri.parse(info.filePathUri);
                        InputStream fis = null;
                        try {
                            fis = getContentResolver().openInputStream(uri);
                            Log.e("TAG", "文件的Uri: " + info.filePathUri + " uri:" + uri + " fis:" + fis);
                            fis.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("TAG", e.toString());
                        }
                    });
        });
    }
}