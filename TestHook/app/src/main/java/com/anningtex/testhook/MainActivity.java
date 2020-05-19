package com.anningtex.testhook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 插件化架构-拦截Activity的启动流程绕过AndroidManifes 检测
 *
 * 1.简介:点击按钮,去服务器上下载一个功能 apk ,保存到本地,没有运行是单独的,
 * 我们要把它启动起来,并且做到参数传递
 *
 *  a.启动,启动的一个Activity ,这个插件Activity 是没有被注册的
 *  b.类需要加载,插件的Activity的类是在插件中
 *  c.还需要加载资源
 *
 * 2.基于代理设计模式,Activity 启动流程
 *
 * 3.拦截启动:进入我的方法,现在真正执行的类其实是 Single里面mInstance实行
 *   a.获取activityManagerNative里面的gDefault
 *   b.获取gDefault中的mInstance属性
 *
 *   c.报错是因为TestActivity 没注册,用一个注册的ProxyActivity占坑,待会会让ProxyActivity去过检测
 *   d.换回来 hook ActivityManager 里面的mH是一个Handler
 *      1>获取ActivityThread 实例
 *      2>获取 ActivityThread 中的mH
 *   e.hook handleLaunchActivty
 *
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        Intent intent = new Intent(this,TestActivity.class);
        startActivity(intent);
    }
}
