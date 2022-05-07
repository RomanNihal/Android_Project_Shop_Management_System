package com.example.u_shop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Data_base extends SQLiteOpenHelper {
    private static final String s = "Account_details";
    public Data_base(Context context) {
        super(context, s, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table User_details(Name TEXT primary key, Email TEXT, Password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists User_details");
    }
    public boolean insert(String name, String email, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("Name",name);
        c.put("Email",email);
        c.put("Password",pass);
        long result = db.insert("User_details",null,c);
        if(result==-1){
            return false;
        }
        else{
            return true;
        }
    }
    public Cursor getInfo(){
        SQLiteDatabase s = this.getReadableDatabase();
        Cursor c = s.rawQuery("select * from User_details",null);
        return c;
    }
}
