package com.example.bonaventurajason.mydictionary;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

public class DictionaryHelper {
    private Context context;
    private DBHelper dbHelper;

    private SQLiteDatabase database;

    public DictionaryHelper(Context context) {
        this.context = context;
    }

    public DictionaryHelper open() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<DictionaryModel> getDataInd(){
        Cursor cursor = database.query(DBContract.TABLE_IND, null, null, null, null, null, DBContract.Column._ID+ " ASC", null);
        cursor.moveToFirst();
        ArrayList<DictionaryModel> arrayList = new ArrayList<>();
        DictionaryModel dictionaryModel;
        if (cursor.getCount()>0) {
            do {
                dictionaryModel = new DictionaryModel();
                dictionaryModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.Column._ID)));
                dictionaryModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Column.KATA)));
                dictionaryModel.setKeterangan(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Column.KETERANGAN)));

                arrayList.add(dictionaryModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<DictionaryModel> getDataEng(){
        Cursor cursor = database.query(DBContract.TABLE_ENG, null, null, null, null, null, DBContract.Column._ID+ " ASC", null);
        cursor.moveToFirst();
        ArrayList<DictionaryModel> arrayList = new ArrayList<>();
        DictionaryModel dictionaryModel;
        if (cursor.getCount()>0) {
            do {
                dictionaryModel = new DictionaryModel();
                dictionaryModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.Column._ID)));
                dictionaryModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Column.KATA)));
                dictionaryModel.setKeterangan(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Column.KETERANGAN)));

                arrayList.add(dictionaryModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public void beginTransaction(){

        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

    public void insertTransaction(DictionaryModel dictionaryModel, String language) {
        SQLiteStatement statement;
        if (language.equals("ind")) {
            String sqlInd = "INSERT INTO "+ DBContract.TABLE_IND+" ("+ DBContract.Column.KATA+", "+ DBContract.Column.KETERANGAN +") VALUES (?, ?)";
            statement = database.compileStatement(sqlInd);
            statement.bindString(1, dictionaryModel.getKata());
            statement.bindString(2,dictionaryModel.getKeterangan());
            statement.execute();
            statement.clearBindings();
        } else if (language.equals("eng")){
            String sqlEng = "INSERT INTO "+ DBContract.TABLE_ENG+" ("+ DBContract.Column.KATA+", "+ DBContract.Column.KETERANGAN +") VALUES (?, ?)";
            statement = database.compileStatement(sqlEng);
            statement.bindString(1, dictionaryModel.getKata());
            statement.bindString(2, dictionaryModel.getKeterangan());
            statement.execute();
            statement.clearBindings();
        }
    }

    public Cursor retrieveInd(String search) {
        String [] columns = {DBContract.Column._ID, DBContract.Column.KATA, DBContract.Column.KETERANGAN};
        Cursor cursor;

        if (search != null && search.length() > 0) {
            String sql = "SELECT * FROM "+DBContract.TABLE_IND+" WHERE "+DBContract.Column.KATA+" LIKE '"+search+"%'";
            cursor = database.rawQuery(sql, null);
            return cursor;
        }
        cursor = database.query(DBContract.TABLE_IND, columns,null,null,null,null,null);
        return cursor;
    }

    public Cursor retrieveEng(String search) {
        String [] columns = {DBContract.Column._ID, DBContract.Column.KATA, DBContract.Column.KETERANGAN};
        Cursor cursor;

        if (search != null && search.length() > 0) {
            String sql = "SELECT * FROM "+DBContract.TABLE_ENG+" WHERE "+DBContract.Column.KATA+" LIKE '"+search+"%'";
            cursor = database.rawQuery(sql, null);
            return cursor;
        }
        cursor = database.query(DBContract.TABLE_ENG, columns,null,null,null,null,null);
        return cursor;
    }
}
