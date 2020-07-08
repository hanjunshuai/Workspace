package com.anningtex.testgreendao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.anningtex.testgreendao.bean.DaoMaster;
import com.anningtex.testgreendao.bean.StudentDao;

import org.greenrobot.greendao.database.Database;

/**
 * @ClassName: MyDaoMaster
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/7/8 16:02
 */
public class MyDaoMaster extends DaoMaster.OpenHelper {
    private static final String TAG = "MyDaoMaster";

    public MyDaoMaster(Context context, String name) {
        super(context, name);
    }

    public MyDaoMaster(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, StudentDao.class);
        Log.e(TAG, "onUpgrade: " + oldVersion + " newVersion = " + newVersion);
    }
}
