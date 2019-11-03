package com.example.c196.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConnector extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "C196.DB";
    private static final int DATABASE_VERSION = 1;


    public DBConnector(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void createTables()
    {
        /*
        dropTable("mentor");
        dropTable("course");
        dropTable("term");
        dropTable("goal");
        dropTable("assessment");
        dropTable("note");*/

        this.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS mentor(mentor_id INTEGER primary key autoincrement,\n" +
                "name varchar(30) not null,\n" +
                "email varchar(30) not null,\n" +
                "phone varchar(30) not null);\n");

        this.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS course(course_id INTEGER primary key autoincrement, " +
                "term_id int(10) not null, " +
                "mentor_id int(10) not null, " +
                "title varchar(30) not null, " +
                "status boolean not null, " +
                "start_date datetime not null, " +
                "end_date datetime not null, " +
                "FOREIGN KEY(mentor_id) REFERENCES mentor(mentor_id), " +
                "FOREIGN KEY(term_id) REFERENCES term(term_id));");

        this.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS term(term_id INTEGER primary key autoincrement, " +
                //"course_id integer(10) not null,\n" +
                "title varchar(30) not null, " +
                "start_date datetime not null, " +
                "end_date datetime not null);"); //+
                //"FOREIGN KEY(course_id) REFERENCES course(course_id));");

        this.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS goal(goal_id INTEGER primary key autoincrement,\n" +
                "assessment_id int(10) not null,\n" +
                "goal_description varchar(30) not null,\n" +
                "goal_date varchar(30) not null, " +
                "FOREIGN KEY(assessment_id) REFERENCES assessment(assessment_id));");

        this.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS assessment(assessment_id INTEGER primary key autoincrement,\n" +
                "course_id int(10) not null, " +
                "title varchar(30) not null, " +
                "date datetime not null, " +
                "FOREIGN KEY(course_id) REFERENCES course(course_id));");

        this.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS note(note_id INTEGER primary key autoincrement,\n" +
                "course_id int(10) not null,\n" +
                "title varchar(30) not null,\n" +
                "description varchar(1000) not null, " +
                "FOREIGN KEY(course_id) REFERENCES course(course_id));");
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

    public int removeRecord(String tableName, String whereClause, String[] whereArgs)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(tableName, whereClause, whereArgs);
    }

    //update sql record methods
    public void updateRecord(String sqlStatement)
    {
        this.getWritableDatabase().execSQL(sqlStatement);
    }

    public int changeRecord(String tableName, Double value, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Salary", value);

        return db.update(tableName, contentValues, whereClause, whereArgs);
    }

    public void dropTable(String tableName)
    {
        this.getWritableDatabase().execSQL("DROP TABLE " + tableName);
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
