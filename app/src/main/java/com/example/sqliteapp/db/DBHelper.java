package com.example.sqliteapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;

import androidx.annotation.Nullable;

import com.example.sqliteapp.model.User;
import com.example.sqliteapp.util.Constants;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Constants.TABLE_NAME + " (" + Constants.KEY_USERNAME + " TEXT PRIMARY KEY, " + Constants.KEY_OCCUPATION + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
    }

    public void insertData(User user) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();;
        values.put(Constants.KEY_USERNAME, user.getUsername());
        values.put(Constants.KEY_OCCUPATION, user.getOccupation());

        db.insert(Constants.TABLE_NAME, null, values);
    }
    public Cursor getUserData(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {Constants.KEY_USERNAME, Constants.KEY_OCCUPATION},
                Constants.KEY_USERNAME+"=?",
                new String[]{username}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor readAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void updateData(String name, User user) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_USERNAME, name);
        values.put(Constants.KEY_OCCUPATION, user.getOccupation());

        db.update(Constants.TABLE_NAME, values, Constants.KEY_USERNAME+"=?", new String[]{name});
    }

    public void deleteData(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_USERNAME+"=?", new String[] {username});
    }

}
