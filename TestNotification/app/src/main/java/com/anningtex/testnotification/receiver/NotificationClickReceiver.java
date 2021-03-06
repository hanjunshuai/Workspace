package com.anningtex.testnotification.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.anningtex.testnotification.Main2Activity;

/**
 * @ClassName: NotificationClickReceiver
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/5/23 16:26
 */
public class NotificationClickReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //todo 跳转之前要处理的逻辑
        Log.i("TAG", "userClick:我被点击啦！！！ ");
        Intent newIntent = new Intent(context, Main2Activity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent);
    }
}
