package com.anningtex.testnotification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.anningtex.testnotification.util.NotificationUtil;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private NotificationManager mNotificationManager;

    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    private TextView tv_4;
    private TextView tv_5;
    private TextView tv_6;
    private TextView tv_7;
    private TextView tv_8;
    private TextView tv_9;
    private TextView tv_10;
    private TextView tv_11;
    private TextView tv_12;
    private TextView tv_13;
    private TextView tv_14;
    private TextView tv_15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        init();
    }


    private void init() {
        initView();
        initListener();
        initNotificationManager();
    }

    private void initView() {
        tv_1 = findViewById(R.id.tv_1);
        tv_2 = findViewById(R.id.tv_2);
        tv_3 = findViewById(R.id.tv_3);
        tv_4 = findViewById(R.id.tv_4);
        tv_5 = findViewById(R.id.tv_5);
        tv_6 = findViewById(R.id.tv_6);
        tv_7 = findViewById(R.id.tv_7);
        tv_8 = findViewById(R.id.tv_8);
        tv_9 = findViewById(R.id.tv_9);
        tv_10 = findViewById(R.id.tv_10);
        tv_11 = findViewById(R.id.tv_11);
        tv_12 = findViewById(R.id.tv_12);
        tv_13 = findViewById(R.id.tv_13);
        tv_14 = findViewById(R.id.tv_14);
        tv_15 = findViewById(R.id.tv_15);
    }

    private void initListener() {
        tv_1.setOnClickListener(this);
        tv_2.setOnClickListener(this);
        tv_3.setOnClickListener(this);
        tv_4.setOnClickListener(this);
        tv_5.setOnClickListener(this);
        tv_6.setOnClickListener(this);
        tv_7.setOnClickListener(this);
        tv_8.setOnClickListener(this);
        tv_9.setOnClickListener(this);
        tv_10.setOnClickListener(this);
        tv_11.setOnClickListener(this);
        tv_12.setOnClickListener(this);
        tv_13.setOnClickListener(this);
        tv_14.setOnClickListener(this);
        tv_15.setOnClickListener(this);
    }

    private void initNotificationManager() {
        // 创建一个NotificationManager的引用
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_1:
                cancelAllNotification();
                break;
            case R.id.tv_2:
                sendNotification1();
                break;
            case R.id.tv_3:
                sendNotification2();
                break;
            case R.id.tv_4:
                sendNotification3();
                break;
            case R.id.tv_5:
                sendNotification4();
                break;
            case R.id.tv_6:
                Intent intent = new Intent(this, ReminderReceiver.class);
                sendBroadcast(intent);
                break;
            case R.id.tv_7:

                break;
            case R.id.tv_8:
                sendNotification8();
                break;
            case R.id.tv_9:
                sendNotification9();
                break;
            case R.id.tv_10:
                sendNotification10();
                break;
            case R.id.tv_11:
                sendNotification11();
                break;
            case R.id.tv_12:
                sendNotification12();
                break;
            case R.id.tv_13:
                sendNotification13();
                break;
            case R.id.tv_14:
                sendNotification14();
                break;
            case R.id.tv_15:
                sendNotification15();
                break;
        }
    }

    private void cancelAllNotification() {
        NotificationUtil NotificationUtil = new NotificationUtil(this);
        NotificationUtil.clearNotification();
    }


    private void sendNotification1() {
        //这三个属性是必须要的，否则异常
        NotificationUtil NotificationUtil = new NotificationUtil(this);
        NotificationUtil.sendNotification(1, "这个是标题", "这个是内容", R.mipmap.ic_launcher);

    }


    private void sendNotification2() {
        //处理点击Notification的逻辑
        //创建intent
        Intent resultIntent = new Intent(this, TestActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);           //添加为栈顶Activity
        resultIntent.putExtra("what", 2);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 2, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 定义Notification的各种属性
        NotificationUtil NotificationUtil = new NotificationUtil(this);
        NotificationUtil
                .setContentIntent(resultPendingIntent)
                .sendNotificationCompat(2, "这个是标题2", "这个是内容2", R.mipmap.ic_launcher);
    }


    private void sendNotification3() {
        long[] vibrate = new long[]{0, 500, 1000, 1500};
        //处理点击Notification的逻辑
        //创建intent
        Intent resultIntent = new Intent(this, TestActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);           //添加为栈顶Activity
        resultIntent.putExtra("what", 3);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 3, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //发送pendingIntent

        NotificationUtil NotificationUtil = new NotificationUtil(this);
        NotificationUtil
                //让通知左右滑的时候是否可以取消通知
                .setOngoing(true)
                //设置内容点击处理intent
                .setContentIntent(resultPendingIntent)
                //设置状态栏的标题
                .setTicker("来通知消息啦")
                //设置自定义view通知栏布局
                .setContent(getRemoteViews())
                //设置sound
                .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
                //设置优先级
                .setPriority(Notification.PRIORITY_DEFAULT)
                //自定义震动效果
                .setVibrate(vibrate)
                //必须设置的属性，发送通知
                .sendNotification(3, "这个是标题3", "这个是内容3", R.mipmap.ic_launcher);
    }


    private void sendNotification4() {
        NotificationUtil NotificationUtil = new NotificationUtil(this);
        NotificationUtil.setContent(getRemoteViews());
        Notification notification = NotificationUtil.getNotification("这个是标题4", "这个是内容4", R.mipmap.ic_launcher);
        NotificationUtil.getManager().notify(4, notification);
    }


    private RemoteViews getRemoteViews() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_mobile_play);
        // 设置 点击通知栏的上一首按钮时要执行的意图
        remoteViews.setOnClickPendingIntent(R.id.btn_pre, getActivityPendingIntent(11));
        // 设置 点击通知栏的下一首按钮时要执行的意图
        remoteViews.setOnClickPendingIntent(R.id.btn_next, getActivityPendingIntent(12));
        // 设置 点击通知栏的播放暂停按钮时要执行的意图
        remoteViews.setOnClickPendingIntent(R.id.btn_start, getActivityPendingIntent(13));
        // 设置 点击通知栏的根容器时要执行的意图
        remoteViews.setOnClickPendingIntent(R.id.ll_root, getActivityPendingIntent(14));
        remoteViews.setTextViewText(R.id.tv_title, "标题");     // 设置通知栏上标题
        remoteViews.setTextViewText(R.id.tv_artist, "艺术家");   // 设置通知栏上艺术家
        return remoteViews;
    }


    /**
     * 获取一个Activity类型的PendingIntent对象
     */
    private PendingIntent getActivityPendingIntent(int what) {
        Intent intent = new Intent(this, TestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);           //添加为栈顶Activity
        intent.putExtra("what", what);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, what, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }


    private void sendNotification8() {
        for (int a = 0; a < 3; a++) {
            //这三个属性是必须要的，否则异常
            NotificationUtil NotificationUtil = new NotificationUtil(this);
            NotificationUtil.sendNotification(8, "这个是标题8", "这个是内容8", R.mipmap.ic_launcher);

        }
    }


    private void sendNotification9() {
        NotificationUtil NotificationUtil = new NotificationUtil(this);
        NotificationUtil
                //让通知左右滑的时候是否可以取消通知
                .setOngoing(true)
                //设置状态栏的标题
                .setTicker("有新消息呢9")
                //设置自定义view通知栏布局
                .setContent(getRemoteViews())
                //设置sound
                .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
                //设置优先级
                .setPriority(Notification.PRIORITY_DEFAULT)
                //自定义震动效果
                .setFlags(Notification.FLAG_NO_CLEAR)
                //必须设置的属性，发送通知
                .sendNotification(9, "有新消息呢9", "这个是标题9", R.mipmap.ic_launcher);
    }


    private void sendNotification10() {

        //处理点击Notification的逻辑
        //创建intent
        Intent resultIntent = new Intent(this, TestActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);           //添加为栈顶Activity
        resultIntent.putExtra("what", 10);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 10, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //设置 Notification 的 flags = FLAG_NO_CLEAR
        //FLAG_NO_CLEAR 表示该通知不能被状态栏的清除按钮给清除掉,也不能被手动清除,但能通过 cancel() 方法清除
        //flags 可以通过 |= 运算叠加效果

        NotificationUtil NotificationUtil = new NotificationUtil(this);
        NotificationUtil
                //让通知左右滑的时候是否可以取消通知
                .setOngoing(true)
                .setContentIntent(resultPendingIntent)
                //设置状态栏的标题
                .setTicker("有新消息呢10")
                //设置自定义view通知栏布局
                .setContent(getRemoteViews())
                .setDefaults(Notification.DEFAULT_ALL)
                //设置sound
                .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
                //设置优先级
                .setPriority(Notification.PRIORITY_DEFAULT)
                //自定义震动效果
                .setFlags(Notification.FLAG_AUTO_CANCEL)
                //必须设置的属性，发送通知
                .sendNotification(10, "有新消息呢10", "这个是标题10", R.mipmap.ic_launcher);
    }


    private void sendNotification11() {
        NotificationUtil NotificationUtil = new NotificationUtil(this);
        NotificationUtil
                .setOngoing(false)
                .setTicker("来通知消息啦")
                .setContent(getRemoteViews())
                //.setSound(Uri.parse("android.resource://com.yc.cn.ycnotification/" + R.raw.hah))
                .setSound(Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "2"))
                .setPriority(Notification.PRIORITY_DEFAULT)
                .sendNotification(11, "我是伴有铃声效果的通知11", "美妙么?安静听~11", R.mipmap.ic_launcher);

    }


    private void sendNotification12() {
        //震动也有两种设置方法,与设置铃声一样,在此不再赘述
        long[] vibrate = new long[]{0, 500, 1000, 1500};
//        Notification.Builder builder = new Notification.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("我是伴有震动效果的通知")
//                .setContentText("颤抖吧,逗比哈哈哈哈哈~")
//                //使用系统默认的震动参数,会与自定义的冲突
//                //.setDefaults(Notification.DEFAULT_VIBRATE)
//                //自定义震动效果
//                .setVibrate(vibrate);
//        //另一种设置震动的方法
//        //Notification notify = builder.build();
//        //调用系统默认震动
//        //notify.defaults = Notification.DEFAULT_VIBRATE;
//        //调用自己设置的震动
//        //notify.vibrate = vibrate;
//        //mManager.notify(3,notify);
//        mNotificationManager.notify(12, builder.build());

        NotificationUtil NotificationUtil = new NotificationUtil(this);
        NotificationUtil
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setVibrate(vibrate)
                .sendNotification(12, "我是伴有震动效果的通知", "颤抖吧,逗比哈哈哈哈哈~", R.mipmap.ic_launcher);

    }


    private void sendNotification13() {
        NotificationUtil NotificationUtil = new NotificationUtil(this);
        NotificationUtil
                .setDefaults(Notification.DEFAULT_ALL)
                .setFlags(Notification.FLAG_ONLY_ALERT_ONCE)
                .sendNotification(13, "仔细看,我就执行一遍", "好了,已经一遍了~", R.mipmap.ic_launcher);

    }


    private void sendNotification14() {
        NotificationUtil NotificationUtil = new NotificationUtil(this);
        NotificationUtil
                .setDefaults(Notification.DEFAULT_ALL)
                .setFlags(Notification.FLAG_ONLY_ALERT_ONCE)
                .sendNotification(14, "显示进度条14", "显示进度条内容，自定定义", R.mipmap.ic_launcher);
    }


    /**
     * 错误代码
     */
    private void ssendNotification151() {
        String id = "channel_1";
        String description = "123";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(id, "123", importance);
            //  mChannel.setDescription(description);
            //  mChannel.enableLights(true);
            //  mChannel.setLightColor(Color.RED);
            //  mChannel.enableVibration(true);
            //  mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
            Notification notification = new Notification.Builder(this, id)
                    .setContentTitle("Title")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setContentTitle("您有一条新通知")
                    .setContentText("这是一条逗你玩的消息")
                    .setAutoCancel(true)
//                    .setContentIntent(pintent)
                    .build();
            mNotificationManager.notify(1, notification);
        }
    }


    private void sendNotification15() {
        NotificationUtil NotificationUtil = new NotificationUtil(this);
        NotificationUtil.sendNotification(15, "新消息来了", "周末到了，不用上班了", R.mipmap.ic_launcher);
    }

}
