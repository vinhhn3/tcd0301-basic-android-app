package com.example.tcd0301basicandroidapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context){
        super(context,"Userdata.db",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Userdetails(name TEXT PRIMARY KEY, contact TEXT, dob TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Userdetails");
    }

    public Boolean insertUserData(String name, String contact, String dob){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("contact", contact);
        contentValues.put("dob", dob);
        long result = db.insert("Userdetails", null, contentValues);
        return result != 1;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Userdetails", null);
        return cursor;
    }

    public Boolean deleteUserData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        // Check if name exists in the database
        Cursor cursor = db.rawQuery("SELECT * FROM Userdetails WHERE name = ?", new String[]{name});

        // name does not exist in the database
        if (cursor.getCount() == 0){
            return false;
        }

        // remove User if exist
        long result = db.delete("Userdetails", "name = ?", new String[]{name});

        return result != -1;
    }

    public Boolean updateUserData(String name, String newContact, String newDob){
        SQLiteDatabase db = this.getWritableDatabase();
        // Check if name exists in the database
        Cursor cursor = db.rawQuery("SELECT * FROM Userdetails WHERE name = ?", new String[]{name});

        // name does not exist in the database
        if (cursor.getCount() == 0){
            return false;
        }

        // if name exists, change the contact and Dob
        ContentValues contentValues = new ContentValues();
        contentValues.put("contact", newContact);
        contentValues.put("dob", newDob);

        long result = db.update("Userdetails", contentValues, "name = ?",
            new String[]{name});

        return result != -1;
    }
}
