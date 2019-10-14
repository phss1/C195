package com.example.c196.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "C196.DB";
    private static final int DATABASE_VERSION = 1;


    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void createTable(String tableName)
    {
        this.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + tableName + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " name TEXT, salary DOUBLE, hire_date DATE)");
    }

    //insert sql record methods
    public void insertRecord(String sqlStatement)
    {
        this.getWritableDatabase().execSQL(sqlStatement);
    }

    public long addRecord(String nameKey, String nameValue, String salaryKey, double salaryValue,
                          String hireDateKey, String hireDateValue)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nameKey, nameValue);
        values.put(salaryKey, salaryValue);
        values.put(hireDateKey, hireDateValue);

        return db.insert("customer", null, values);
    }

    //delete sql record methods
    public void deleteRecord(String sqlStatement)
    {
        this.getWritableDatabase().execSQL(sqlStatement);
    }

    public int removeRecord(String tableName, String whereClause, String [] whereArgs)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(tableName, whereClause, whereArgs);
    }

    //update sql record methods
    public void updateRecord(String sqlStatement)
    {
        this.getWritableDatabase().execSQL(sqlStatement);
    }

    public int changeRecord(String tableName, Double value, String whereClause, String[] whereArgs)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Salary", value);

        return db.update(tableName, contentValues, whereClause, whereArgs);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
