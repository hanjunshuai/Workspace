package com.anningtex.testnotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.anningtex.testnotification.util.NotificationUtil;

import static android.provider.Settings.EXTRA_APP_PACKAGE;
import static android.provider.Settings.EXTRA_CHANNEL_ID;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private NotificationUtil mNotificationUtil;
    private RemoteViews remoteViews;
    Notification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        mNotificationUtil = new NotificationUtil(this);
        checkNotifySetting();

        remoteViews = getRemoteViews();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationUtil NotificationUtil = new NotificationUtil(MainActivity.this);
                NotificationUtil.setContent(remoteViews);
                notification = NotificationUtil.getNotification("这个是标题4", "这个是内容4", R.mipmap.ic_launcher);
                for (int i = 1; i <= 101; i++) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    remoteViews.setProgressBar(R.id.progress_horizontal, 100, i, false);
                    if (i == 100) {
                        remoteViews.setProgressBar(R.id.progress_horizontal, 100, i, false);
                        remoteViews.setTextViewText(R.id.title, "下载完成");
                    }
                    NotificationUtil.getManager().notify(4, notification);
                }
                getCount();
            }
        });
    }

    private void getCount() {
        if (remoteViews != null) {

        }
    }

    private RemoteViews getRemoteViews() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_progress);
//        // 设置 点击通知栏的上一首按钮时要执行的意图
//        remoteViews.setOnClickPendingIntent(R.id.btn_pre, getActivityPendingIntent(11));
//        // 设置 点击通知栏的下一首按钮时要执行的意图
//        remoteViews.setOnClickPendingIntent(R.id.btn_next, getActivityPendingIntent(12));
//        // 设置 点击通知栏的播放暂停按钮时要执行的意图
//        remoteViews.setOnClickPendingIntent(R.id.btn_start, getActivityPendingIntent(13));
//        // 设置 点击通知栏的根容器时要执行的意图
//        remoteViews.setOnClickPendingIntent(R.id.ll_root, getActivityPendingIntent(14));
        remoteViews.setTextViewText(R.id.tv_title, "下载进度");     // 设置通知栏上标题
        remoteViews.setTextViewText(R.id.tv_artist, "艺术家");   // 设置通知栏上艺术家
        return remoteViews;
    }

    /**
     * 检查是否已经开启通知权限
     */
    private void checkNotifySetting() {
//        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
//        // areNotificationsEnabled方法的有效性官方只最低支持到API 19，
//        // 低于19的仍可调用此方法不过只会返回true，即默认为用户已经开启了通知。
//        boolean isOpened = manager.areNotificationsEnabled();
        boolean isOpened = mNotificationUtil.checkNotifySetting();
        if (isOpened) {
            textView.setText("通知权限已经被打开" +
                    "\n手机型号:" + android.os.Build.MODEL +
                    "\nSDK版本:" + android.os.Build.VERSION.SDK +
                    "\n系统版本:" + android.os.Build.VERSION.RELEASE +
                    "\n软件包名:" + getPackageName());
//            startActivity(new Intent(MainActivity.this, Main2Activity.class));
        } else {
            textView.setText("还没有开启通知权限，点击去开启");
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                        //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
                        intent.putExtra(EXTRA_APP_PACKAGE, getPackageName());
                        intent.putExtra(EXTRA_CHANNEL_ID, getApplicationInfo().uid);

                        //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
                        intent.putExtra("app_package", getPackageName());
                        intent.putExtra("app_uid", getApplicationInfo().uid);

                        // 小米6 -MIUI9.6-8.0.0系统，是个特例，通知设置界面只能控制"允许使用通知圆点"——然而这个玩意并没有卵用，我想对雷布斯说：I'm not ok!!!
                        //  if ("MI 6".equals(Build.MODEL)) {
                        //      intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        //      Uri uri = Uri.fromParts("package", getPackageName(), null);
                        //      intent.setData(uri);
                        //      // intent.setAction("com.android.settings/.SubSettings");
                        //  }
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // 出现异常则跳转到应用设置界面：锤子坚果3——OC105 API25
                        Intent intent = new Intent();

                        //下面这种方案是直接跳转到当前应用的设置界面。
                        //https://blog.csdn.net/ysy950803/article/details/71910806
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                }
            });
        }
    }
}
