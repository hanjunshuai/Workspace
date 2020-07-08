package com.anningtex.testgreendao;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.anningtex.testgreendao.bean.DaoMaster;
import com.anningtex.testgreendao.bean.DaoSession;
import com.anningtex.testgreendao.db.MyDaoMaster;

/**
 * @ClassName: BaseApplication
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/7/8 15:38
 */
public class AserbaoApplication extends Application {
    public static int screenWidth, screenHeight;
    public static AserbaoApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
    }

    private void initData() {
        instance = this;
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        initGreenDao();
    }


    public static final String DB_NAME = "aserbaos.db";

    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    private void initGreenDao() {
        MyDaoMaster helper = new MyDaoMaster(this, DB_NAME);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    private DaoSession daoSession;

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
