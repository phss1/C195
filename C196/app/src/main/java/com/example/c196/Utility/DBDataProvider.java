package com.example.c196.Utility;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class DBDataProvider
{
    DBConnector myHelper;

    public void dropTable(String tableName)
    {
        myHelper.getWritableDatabase().execSQL("DROP TABLE " + tableName);
    }

    //insert sql record methods
    public void insertRecord(String sqlStatement)
    {
        myHelper.getWritableDatabase().execSQL(sqlStatement);
    }

    public long addRecord(String nameKey, String nameValue, String salaryKey, double salaryValue,
                          String hireDateKey, String hireDateValue)
    {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nameKey, nameValue);
        values.put(salaryKey, salaryValue);
        values.put(hireDateKey, hireDateValue);

        return db.insert("customer", null, values);
    }

    //delete sql record methods
    public void deleteRecord(String sqlStatement)
    {
        myHelper.getWritableDatabase().execSQL(sqlStatement);
    }

    public int removeRecord(String tableName, String whereClause, String [] whereArgs)
    {
        SQLiteDatabase db = myHelper.getWritableDatabase();

        return db.delete(tableName, whereClause, whereArgs);
    }

    //update sql record methods
    public void updateRecord(String sqlStatement)
    {
        myHelper.getWritableDatabase().execSQL(sqlStatement);
    }

    public int changeRecord(String tableName, Double value, String whereClause, String[] whereArgs)
    {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Salary", value);

        return db.update(tableName, contentValues, whereClause, whereArgs);
    }
}
