package com.inc.tim.dotoday.data;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

public class DbOpenHelper extends DaoMaster.OpenHelper {

    public DbOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        switch (oldVersion) {
            case 1:
            case 2:
                //db.execSQL("ALTER TABLE " + TaskDao.TABLENAME + " ADD COLUMN " + TaskDao.Properties.Title.columnName + " TEXT DEFAULT 'DEFAULT_VAL'");
        }
    }
}
