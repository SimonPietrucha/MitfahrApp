package com.example.mitfahrapp;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.UUID;

public class DBHelper extends SQLiteOpenHelper {
        public static final String DBNAME = "Login.db";


        public DBHelper(Context context) {
            super(context, "Login.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase MyDB) {
            MyDB.execSQL("create Table users(username TEXT primary key, password TEXT)");
            MyDB.execSQL("create Table mitfahrgelegenheit(ID INT primary key, username TEXT, start TEXT, ziel TEXT )");
        }


        @Override
        public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
            MyDB.execSQL("drop Table if exists users");
        }

        public Boolean insertData(String username, String password){
            SQLiteDatabase MyDB = this.getWritableDatabase();
            ContentValues contentValues= new ContentValues();
            contentValues.put("username", username);
            contentValues.put("password", password);
            long result = MyDB.insert("users", null, contentValues);
            if(result==-1) return false;
            else
                return true;
        }

        public Boolean checkusername(String username) {
            SQLiteDatabase MyDB = this.getWritableDatabase();
            Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
            if (cursor.getCount() > 0)
                return true;
            else
                return false;
        }

        public Boolean checkusernamepassword(String username, String password){
            SQLiteDatabase MyDB = this.getWritableDatabase();
            Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
            if(cursor.getCount()>0)
                return true;
            else
                return false;
        }
    public boolean updateStartAndZiel(String username, String start, String ziel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("start", start);
        contentValues.put("ziel", ziel);

        // Aktualisiere die Zeile in deiner Tabelle mit den neuen Werten für "start" und "ziel"
        int rowsAffected = db.update("mitfahrgelegenheit", contentValues, null, null);
        db.close();

        // Überprüfe, ob die Aktualisierung erfolgreich war
        return rowsAffected > 0;
    }






    }
