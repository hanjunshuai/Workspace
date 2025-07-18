package com.anning.projectringtone;

import android.net.Uri;

public class RingtoneItem {
    private String title;
    private Uri uri;

    public RingtoneItem(String title, Uri uri) {
        this.title = title;
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public Uri getUri() {
        return uri;
    }

    @Override
    public String toString() {
        return "RingtoneItem{" +
                "title='" + title + '\'' +
                ", uri=" + uri +
                '}';
    }
}
