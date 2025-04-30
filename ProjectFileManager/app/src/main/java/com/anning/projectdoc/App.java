package com.anning.projectdoc;

import android.app.Application;

import com.anning.projectdoc.lib.content.ZFileContent;

public final class App extends Application {
    public void onCreate() {
        super.onCreate();
        ZFileContent.getZFileHelp().init(new MyFileImageListener()).setFileTypeListener(new MyFileTypeListener());
    }
}