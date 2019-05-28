package com.ceshi.ha.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ceshi.ha.utils.log.LogUtil;


/**
 * 1）.创建一个版本为1的“diaryOpenHelper.db”的数据库，
 * 2）.同时创建一个 “diary” 表（包含一个_id主键并自增长，topic字符型100长度， content字符型1000长度）
 * 3）.在数据库版本变化时请删除diary表，并重新创建出diary表。
 */
public class DBHelper extends SQLiteOpenHelper {

    public final static String DATABASENAME = "diaryOpenHelper.db";
    public final static int DATABASEVERSION = 1;

    // 创建数据库
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // 创建表等机构性文件
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table diary"
                + "(" + "id integer primary key autoincrement,"
                + "topic varchar(100),"
                + "content varchar(1000)" + ")";
        LogUtil.i(sql);
        db.execSQL(sql);
    }

    // 若数据库版本有更新，则调用此方法
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists diary";
        db.execSQL(sql);
        this.onCreate(db);
    }
}
