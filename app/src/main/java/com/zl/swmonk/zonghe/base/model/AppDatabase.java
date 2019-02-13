package com.zl.swmonk.zonghe.base.model;

import com.raizlabs.android.dbflow.annotation.Database;


@Database(name = AppDatabase.NAME,version = AppDatabase.VERSION)
public abstract class AppDatabase {

    //版本号
    static final int VERSION = 2;
    //数据库名称
    static final String NAME = "ZongHeDB";

}
