package com.example.naoya.todomanager;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * To test SQLite.
 */
public class SQLite {

    private final static String TAG_WRITE = "write";
    private final static String TAG_READ = "read";
    private final static String DB_NAME = "todo.db";
    private final static String DB_TABLE = "todo";
    private final static int  DB_VERSION = 1;

    private SQLiteDatabase db;

    private void writeDB(String info) throws Exception{
        ContentValues values = new ContentValues();
        values.put("id",0);
        values.put("info",info);
        int colNum = db.update(DB_TABLE, values, null, null);
        if (colNum == 0) db.insert(DB_TABLE, "", values);
    }
    private String readDB() throws Exception{
        Cursor c = db.query(DB_TABLE, new String[]{"id", "info"}, "id = '0'", null, null, null, null);
        if (c.getCount() == 0) throw new Exception();
        c.moveToFirst();
        String str = c.getString(1);
        c.close();
        return str;
    }
}