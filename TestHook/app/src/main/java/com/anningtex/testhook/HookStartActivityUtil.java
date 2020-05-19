package com.anningtex.testhook;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HookStartActivityUtil {
    private static final String EXTRA_ORIGIN_INTENT = "EXTRA_ORIGIN_INTENT";
    private Context mContext;
    private Class<?> mProxyClass;

    public HookStartActivityUtil(Context mContext, Class<?> proxyClass) {
        this.mContext = mContext.getApplicationContext();
        this.mProxyClass = proxyClass;
    }

    public void hookLaunchActivity() throws Exception {
        // 1>获取ActivityThread 实例
        Class<?> atClass = Class.forName("android.app.ActivityThread");
        Field scatField = atClass.getDeclaredField("sCurrentActivityThread");
        scatField.setAccessible(true);
        Object sCurrentActivityThread = scatField.get(null);
        // 2>获取 ActivityThread 中的mH
        Field mHField = atClass.getDeclaredField("mH");
        mHField.setAccessible(true);
        Handler handler = (Handler) mHField.get(sCurrentActivityThread);
        // e.hook handleLaunchActivity
        // 给Handler 设置CallBack 回调,也只能通过反射
        Class<?> handlerClass = Class.forName("android.os.Handler");
        Field mCallBackField = handlerClass.getDeclaredField("mCallBack");
        mCallBackField.setAccessible(true);
        mCallBackField.set(handler, new HandlerCallBack());
    }

    private class HandlerCallBack implements Handler.Callback {

        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Log.e("TAG", "handleMessage");
            // 每发一个消息都会走一次这个CallBack方法
            if (msg.what == 100) {
                handleLaunchActivity(msg);
            }
            return false;
        }

        /**
         * 开始启动创建Activity拦截
         *
         * @param msg
         */
        private void handleLaunchActivity(Message msg) {
            try {
                Object record = msg.obj;
                // 1.record 获取过安检的Intent
                Field intentField = record.getClass().getDeclaredField("intent");
                intentField.setAccessible(true);
                Intent safeIntent = (Intent) intentField.get(record);
                // 2.safeIntent 中获取原来的originIntent
                Intent originIntent = safeIntent.getParcelableExtra(EXTRA_ORIGIN_INTENT);
                // 3.重新设置回去
                if (originIntent != null) {
                    intentField.set(record, originIntent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void hookStartAcvity() throws Exception {
//        a.获取activityManagerNative里面的gDefault
        Class<?> amnClass = Class.forName("android.app.ActivityManagerNative");
        // 获取属性
        Field gDefaultField = amnClass.getDeclaredField("gDefault");
        // 设置权限
        gDefaultField.setAccessible(true);
        Object gDefault = gDefaultField.get(null);
//        b.获取gDefault中的mInstance属性
        Class<?> singletonClass = Class.forName("android.util.Singleton");
        Field mInstanceField = singletonClass.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);
        Object iamInstance = mInstanceField.get(gDefault);

        Class<?> iamClass = Class.forName("android.app.IActivityManager");
        Proxy.newProxyInstance(HookStartActivityUtil.class.getClassLoader(),
                new Class[]{iamClass},
                // InvocationHandler 必须执行,谁去执行
                new StartActivityInvocationHandler(iamInstance));

        //3.重新指定
        mInstanceField.set(gDefault, iamInstance);
    }

    private class StartActivityInvocationHandler implements InvocationHandler {
        // 方法执行者
        private Object mObject;

        public StartActivityInvocationHandler(Object object) {
            this.mObject = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.e("TAG", method.getName());

            // 替换 Intent , 过AndroidManifests.xml检测
            if (method.getName().equals("startActivity")) {
                // 1.首先获取原来的Intent
                Intent originIntent = (Intent) args[2];
                // 2.创建一个安全的
                Intent safeIntent = new Intent(mContext, mProxyClass);

                args[2] = safeIntent;
                //3.绑定原来的Intent
                safeIntent.putExtra(EXTRA_ORIGIN_INTENT, originIntent);
            }
            return method.invoke(mObject, args);
        }
    }
}
