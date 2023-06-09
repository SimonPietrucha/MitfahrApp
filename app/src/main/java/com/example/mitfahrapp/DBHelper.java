package com.example.mitfahrapp;
import static android.app.DownloadManager.COLUMN_ID;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DBHelper extends SQLiteOpenHelper {
        public static final String DBNAME = "Login.db";


        public DBHelper(Context context) {
            super(context, "Login.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase MyDB) {
            MyDB.execSQL("create Table users(ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");
            MyDB.execSQL("create Table mitfahrgelegenheit(ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, start TEXT, ziel TEXT, mitfahrer TEXT)");
        }


        @Override
        public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
            MyDB.execSQL("drop Table if exists users");
            MyDB.execSQL("drop Table if exists mitfahrgelegenheit");
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
    public boolean insertMitfahrgelegenheit(String username, String start, String ziel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("start", start);
        contentValues.put("ziel", ziel);

        // Füge einen neuen Eintrag in die Tabelle ein und erhalte die zugeordnete ID zurück
        long newRowId = db.insert("mitfahrgelegenheit", null, contentValues);
        db.close();

        // Überprüfe, ob das Einfügen erfolgreich war
        return newRowId != -1;
    }
    public Cursor getAllMitfahrgelegenheiten() {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        return MyDB.query("mitfahrgelegenheit", null, null, null, null, null, null);
    }
    public boolean addMitfahrer(int mitfahrgelegenheitId, String user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mitfahrer", user);

        int rowsAffected = db.update("mitfahrgelegenheit", values, "ID = ?", new String[]{String.valueOf(mitfahrgelegenheitId)});

        db.close();

        return rowsAffected > 0;
    }
    public boolean deleteMitfahrgelegenheit(int mitfahrgelegenheitId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("mitfahrgelegenheit", "ID = ?", new String[]{String.valueOf(mitfahrgelegenheitId)}) > 0;
    }
    public Cursor getMitfahrgelegenheitenByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                "ID",
                "username",
                "start",
                "ziel",
                "mitfahrer"
        };
        String selection = "username = ?";
        String[] selectionArgs = {username};

        return db.query("mitfahrgelegenheit", projection, selection, selectionArgs, null, null, null);
    }

}






