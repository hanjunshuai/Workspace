package com.anning.projectringtone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getList(View view) {
        // 请求权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            Log.e("TAG", "无权限");
        } else {
            Log.e("TAG", "获取铃声列表");
            List<RingtoneItem> systemNotificationSounds = getSystemSounds();
            Log.e("TAG", "size : " + systemNotificationSounds.size());
            for (RingtoneItem ringtoneItem : systemNotificationSounds) {
                Log.e("TAG", "铃声名称 : " + ringtoneItem.toString());
            }
        }
    }

    public List<RingtoneItem> getSystemSounds() {
        List<RingtoneItem> soundList = new ArrayList<>();

        RingtoneManager ringtoneManager = new RingtoneManager(this);
        ringtoneManager.setType(RingtoneManager.TYPE_ALARM); // 也可以使用 TYPE_RINGTONE 或 TYPE_ALARM

        Cursor cursor = ringtoneManager.getCursor();

        try {
            while (cursor.moveToNext()) {
                String title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
                Uri uri = ringtoneManager.getRingtoneUri(cursor.getPosition());
                soundList.add(new RingtoneItem(title, uri));
            }
        } finally {
            cursor.close();
        }
        return soundList;
    }

    public Uri getDefaultNotificationSound() {
        return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }

    public void playRingtone(Uri ringtoneUri) {
        Ringtone ringtone = RingtoneManager.getRingtone(this, ringtoneUri);
        if (ringtone != null) {
            // ringtone.setLooping(false); // 设置为不循环播放
            ringtone.play();
        }
    }

    public void openNotificationSoundSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_SOUND_SETTINGS);
        context.startActivity(intent);
    }

    public void playSounds(View view) {
        playRingtone(getDefaultNotificationSound());
    }

    /**
     * 不允许普通应用直接修改系统默认的通知音
     */
    public void setDefaultSounds(View view) {
        openNotificationSoundSettings(this);
    }
}