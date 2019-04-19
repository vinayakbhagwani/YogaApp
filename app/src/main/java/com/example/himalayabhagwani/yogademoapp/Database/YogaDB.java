package com.example.himalayabhagwani.yogademoapp.Database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class YogaDB extends SQLiteAssetHelper{

    private static final String DB_NAME = "Yoga.db";
    private static final int DB_VER = 1;

    public YogaDB(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    // Method to read database
    public int getSettingMode() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Mode"};
        String sqlTable = "Setting";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);
        c.moveToFirst();
        return c.getInt(c.getColumnIndex("Mode"));
    }


    // Method to write/save information in the database
    public void setSettingMode(int value) {

        SQLiteDatabase db = getReadableDatabase();
        String query = "UPDATE Setting SET Mode = "+value;
        db.execSQL(query);

    }

    // Method to read database
    public int getAlarmMode() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Mode"};
        String sqlTable = "Alarm";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);
        c.moveToFirst();
        return c.getInt(c.getColumnIndex("Mode"));
    }


    // Method to write/save information in the database
    public void setAlarmMode(int value) {

        SQLiteDatabase db = getReadableDatabase();
        String query = "UPDATE Alarm SET Mode = "+value;
        db.execSQL(query);

    }

    public List<String> getWorkoutDays() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Day"};
        String sqlTable = "WorkoutDays";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);

        List<String> result = new ArrayList<>();

        if(c.moveToFirst()) {

            do {
                result.add(c.getString(c.getColumnIndex("Day")));
            }while (c.moveToNext());

        }

        return result;
    }

    public void setWorkoutDays(String value) {

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO WorkoutDays(Day) VALUES('%s');",value);
        db.execSQL(query);

    }

}
