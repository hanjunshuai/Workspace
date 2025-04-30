package com.anning.projectdoc.lib.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;


import com.anning.projectdoc.R;
import com.anning.projectdoc.lib.common.ZFileActivity;
import com.anning.projectdoc.lib.content.ZFileContent;

import java.io.File;

public class ZFilePicActivity extends ZFileActivity {

    private ImageView zfilePicShow;

    @Override
    public int getContentView() {
        return R.layout.activity_zfile_pic;
    }

    @Override
    public void init(@Nullable Bundle var1) {
        initView();
        ZFileContent.setStatusBarTransparent(this);
        String filePath = getIntent().getStringExtra("picFilePath");
        ZFileContent.getZFileHelp().getImageLoadListener().loadImage(zfilePicShow, new File(filePath));
        zfilePicShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initView() {
        zfilePicShow = (ImageView) findViewById(R.id.zfile_pic_show);
    }
}
