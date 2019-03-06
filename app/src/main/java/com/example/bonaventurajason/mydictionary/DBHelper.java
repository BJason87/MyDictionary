package com.example.bonaventurajason.mydictionary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "dictionary.db";
    private static int DATABASE_VERSION = 1;

    private String CREATE_TABLE_IND = "CREATE TABLE "+ DBContract.TABLE_IND+" (" +
            DBContract.Column._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBContract.Column.KATA+ " TEXT NOT NULL, "+
            DBContract.Column.KETERANGAN+" TEXT NOT NULL);";

    private String CREATE_TABLE_ENG = "CREATE TABLE "+ DBContract.TABLE_ENG+" (" +
            DBContract.Column._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBContract.Column.KATA+ " TEXT NOT NULL, "+
            DBContract.Column.KETERANGAN+" TEXT NOT NULL);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_IND);
        db.execSQL(CREATE_TABLE_ENG);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DBContract.TABLE_IND);
        db.execSQL("DROP TABLE IF EXISTS "+DBContract.TABLE_ENG);
        onCreate(db);
    }
}
