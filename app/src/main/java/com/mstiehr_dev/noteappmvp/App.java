package com.mstiehr_dev.noteappmvp;

import android.app.Application;

import com.mstiehr_dev.noteappmvp.model.DaoMaster;
import com.mstiehr_dev.noteappmvp.model.DaoSession;

import org.greenrobot.greendao.database.Database;


public class App extends Application
{
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public static DaoSession getDaoSession()
    {
        return daoSession;
    }
}
