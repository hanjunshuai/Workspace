package com.anningtex.testnotification.util;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static android.app.Notification.VISIBILITY_SECRET;
import static android.provider.Settings.EXTRA_APP_PACKAGE;
import static android.provider.Settings.EXTRA_CHANNEL_ID;
import static androidx.core.app.NotificationCompat.PRIORITY_DEFAULT;

/**
 * 通知栏工具栏
 */
public class NotificationUtil extends ContextWrapper {
    private static final String CHANNEL_ID = "default";
    private static final String CHANNEL_NAME = "Default_Channel";
    private NotificationManager mManager;
    private int[] flags;
    private boolean ongoing = false;
    private RemoteViews remoteViews = null;
    private PendingIntent intent = null;
    private String ticker = "";
    private int priority = Notification.PRIORITY_DEFAULT;
    private boolean onlyAlertOnce = false;
    private long when = 0;
    private Uri sound = null;
    private int defaults = 0;
    private long[] pattern = null;

    public NotificationUtil(Context base) {
        super(base);
        // android 8.0 以上需进行特殊处理,也就是targetSDKVersion为26以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }
    }

    /**
     * 检查是否有通知权限
     *
     * @return
     */
    public boolean checkNotifySetting() {
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        // areNotificationsEnabled方法的有效性官方只最低支持到API 19，
        // 低于19的仍可调用此方法不过只会返回true，即默认为用户已经开启了通知。
        return manager.areNotificationsEnabled();
    }

    /**
     * 打开权限
     */
    public void openNotifySetting() {
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
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        // 第一个参数：channel_id
        // 第二个参数 : channel_name
        // 第三个参数 : 设置通知重要级别
        // 注意：该级别必要在NotificationChannel 的构造函数中指定,总共5个级别
        // 范围从NotificationManager.IMPORTANCE_NONE(0)~NotificationManager.IMPORTANCE_HIGH(4)
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT);

        // 是否绕过请勿打扰模式
        channel.canShowBadge();
        // 闪关灯
        channel.enableLights(true);
        // 锁屏显示通知
        channel.setLockscreenVisibility(VISIBILITY_SECRET);
        // 闪关灯的颜色
        channel.setLightColor(Color.RED);
        // 桌面launcher的消息角标
        channel.canShowBadge();
        // 是否震动
        channel.enableVibration(true);
        // 获取系统通知响铃声音配置
        channel.getAudioAttributes();
        // 获取通知取到组
        channel.getGroup();
        // 设置可绕过,请勿打扰模式
        channel.setBypassDnd(true);
        // 设置震动模式
        channel.setVibrationPattern(new long[]{100, 100, 200});
        // 是否会有灯光
        channel.shouldShowLights();
        getManager().createNotificationChannel(channel);
    }

    /**
     * 获取创建一个NotificationManager的对象
     *
     * @return
     */
    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    /**
     * 清空所有的通知
     */
    public void clearNotification() {
        getManager().cancelAll();
    }

    /**
     * 获取Notification
     *
     * @param title
     * @param content
     * @param icon
     * @return
     */
    public Notification getNotification(String title, String content, int icon) {
        Notification build;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Android8.0 以上需要进行特殊处理
            // 通知用到NotificationCompat()这个v4库中的方法。但是在实际使用时发现书上的代码已经过时并且Android8.0已经不支持这种写法
            Notification.Builder builder = getChannelNotification(title, content, icon);
            build = builder.build();
        } else {
            NotificationCompat.Builder builder = getNotificationCompat(title, content, icon);
            build = builder.build();
        }
        if (flags != null && flags.length > 0) {
            for (int i = 0; i < flags.length; i++) {
                build.flags |= flags[i];
            }
        }
        return build;
    }

    private NotificationCompat.Builder getNotificationCompat(String title, String content, int icon) {
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        } else {
            //注意用下面这个方法，在8.0以上无法出现通知栏。8.0之前是正常的。这里需要增强判断逻辑
            builder = new NotificationCompat.Builder(getApplicationContext());
            builder.setPriority(PRIORITY_DEFAULT);
        }
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(icon);
        builder.setPriority(priority);
        builder.setOnlyAlertOnce(onlyAlertOnce);
        builder.setOngoing(ongoing);
        if (remoteViews != null) {
            builder.setContent(remoteViews);
        }
        if (intent != null) {
            builder.setContentIntent(intent);
        }
        if (ticker != null && ticker.length() > 0) {
            builder.setTicker(ticker);
        }
        if (when != 0) {
            builder.setWhen(when);
        }
        if (sound != null) {
            builder.setSound(sound);
        }
        if (defaults != 0) {
            builder.setDefaults(defaults);
        }
        //点击自动删除通知
        builder.setAutoCancel(true);
        return builder;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private Notification.Builder getChannelNotification(String title, String content, int icon) {
        Notification.Builder builder = new Notification.Builder(getApplicationContext(), CHANNEL_ID);
        Notification.Builder notificationBuilder = builder
                // 设置标题
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(icon)
                // 设置通知左右滑时是否可以取消
                .setOngoing(ongoing)
                // 设置优先级
                .setPriority(priority)
                // 是否提示一次 true 如果Notification已经存在状态栏即使在调用notify也不会更新
                .setOnlyAlertOnce(onlyAlertOnce)
                .setAutoCancel(true);

        if (remoteViews != null) {
            // 设置自定义view通知栏
            notificationBuilder.setContent(remoteViews);
        }
        if (intent != null) {
            notificationBuilder.setContentIntent(intent);
        }
        if (ticker != null && ticker.length() > 0) {
            // 设置状态栏标题
            notificationBuilder.setTicker(ticker);
        }
        if (when != 0) {
            notificationBuilder.setWhen(when);
        }

        if (sound != null) {
            notificationBuilder.setSound(sound);
        }
        if (defaults != 0) {
            //设置默认的提示音
            notificationBuilder.setDefaults(defaults);
        }
        if (pattern != null) {
            // 自定义震动效果
            notificationBuilder.setVibrate(pattern);
        }
        return notificationBuilder;
    }

    /**
     * 调用该方法可以发送通知
     *
     * @param notifyId notifyId
     * @param title    title
     * @param content  content
     * @deprecated
     */
    public void sendNotificationCompat(int notifyId, String title, String content, int icon) {
        NotificationCompat.Builder builder = getNotificationCompat(title, content, icon);
        Notification build = builder.build();
        if (flags != null && flags.length > 0) {
            for (int a = 0; a < flags.length; a++) {
                build.flags |= flags[a];
            }
        }
        getManager().notify(notifyId, build);
    }

    /**
     * 建议使用这个发送通知
     * 调用该方法可以发送通知
     *
     * @param notifyId notifyId
     * @param title    title
     * @param content  content
     */
    public void sendNotification(int notifyId, String title, String content, int icon) {
        Notification build;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //android 8.0以上需要特殊处理，也就是targetSDKVersion为26以上
            //通知用到NotificationCompat()这个V4库中的方法。但是在实际使用时发现书上的代码已经过时并且Android8.0已经不支持这种写法
            Notification.Builder builder = getChannelNotification(title, content, icon);
            build = builder.build();
        } else {
            NotificationCompat.Builder builder = getNotificationCompat(title, content, icon);
            build = builder.build();
        }
        if (flags != null && flags.length > 0) {
            for (int a = 0; a < flags.length; a++) {
                build.flags |= flags[a];
            }
        }
        getManager().notify(notifyId, build);
    }

    public NotificationUtil setOngoing(boolean ongoing) {
        this.ongoing = ongoing;
        return this;
    }

    /**
     * 设置自定义view通知栏布局
     *
     * @param remoteViews view
     * @return
     */
    public NotificationUtil setContent(RemoteViews remoteViews) {
        this.remoteViews = remoteViews;
        return this;
    }

    /**
     * 设置内容点击
     *
     * @param intent intent
     * @return
     */
    public NotificationUtil setContentIntent(PendingIntent intent) {
        this.intent = intent;
        return this;
    }

    public NotificationUtil setRemoteViews(RemoteViews remoteViews) {
        this.remoteViews = remoteViews;
        return this;
    }

    public NotificationUtil setIntent(PendingIntent intent) {
        this.intent = intent;
        return this;
    }

    public NotificationUtil setTicker(String ticker) {
        this.ticker = ticker;
        return this;
    }

    public NotificationUtil setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public NotificationUtil setOnlyAlertOnce(boolean onlyAlertOnce) {
        this.onlyAlertOnce = onlyAlertOnce;
        return this;
    }

    public NotificationUtil setWhen(long when) {
        this.when = when;
        return this;
    }

    public NotificationUtil setSound(Uri sound) {
        this.sound = sound;
        return this;
    }

    public NotificationUtil setDefaults(int defaults) {
        this.defaults = defaults;
        return this;
    }

    public NotificationUtil setPattern(long[] pattern) {
        this.pattern = pattern;
        return this;
    }

    /**
     * 自定义震动效果
     *
     * @param pattern pattern
     * @return
     */
    public NotificationUtil setVibrate(long[] pattern) {
        this.pattern = pattern;
        return this;
    }

    /**
     * 设置flag标签
     *
     * @param flags flags
     * @return
     */
    public NotificationUtil setFlags(int... flags) {
        this.flags = flags;
        return this;
    }
}
