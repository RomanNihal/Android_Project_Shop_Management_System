package com.example.u_shop;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class productDetailsDataBase extends SQLiteOpenHelper {

    public productDetailsDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public void query(String sql){
        SQLiteDatabase d = getWritableDatabase();
        d.execSQL(sql);
    }
    public void insert(String name, String pName, String pPrice, String pDes, byte[] image){
        SQLiteDatabase d = getWritableDatabase();
        String sql = "insert into product values (null, ?, ?, ?, ?, ?)";

        SQLiteStatement statement = d.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, pName);
        statement.bindString(3, pPrice);
        statement.bindString(4, pDes);
        statement.bindBlob(5, image);
        statement.executeInsert();
    }
    public void updateData(int id, String pPrice, byte[] image){
        SQLiteDatabase d = getWritableDatabase();
        String sql = "UPDATE PRODUCT SET pPrice = ?, Image = ? WHERE id = ?";
        SQLiteStatement statement = d.compileStatement(sql);
        statement.bindString(1, pPrice);
        statement.bindBlob(2, image);
        statement.bindDouble(3,(double)id);
        statement.execute();
        d.close();
    }
    public void deleteData(int id){
        SQLiteDatabase d = getWritableDatabase();
        String sql = "DELETE FROM PRODUCT WHERE id = ?";
        SQLiteStatement statement = d.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);
        statement.execute();
        d.close();
    }
    public Cursor getData(String sql){
        SQLiteDatabase d = getReadableDatabase();
        return d.rawQuery(sql,null);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
