package com.example.c196.Controller;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.UtilityMethods;

public class MainActivity extends AppCompatActivity
{
    UtilityMethods utilities = new UtilityMethods();
    DBConnector myHelper;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myHelper = new DBConnector(MainActivity.this);
        myHelper.getWritableDatabase();
        myHelper.createTables();
        //myHelper.insertRecord("insert into course(term_id, mentor_id, title, status, start_date, end_date) " +
        //        "values(-1,-1,\"Course1\",\'true\',\"01/01/2020\",\"12/01/2020\");");
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        /*
        Notes:
        Fix TermModify issue where removal of items from list view dont update list
        */

        /*
        myHelper.insertRecord("insert into customer(name, salary, hire_date) " +
                "values('Mike', 20000.00, '2019-10-05 22:00:00')");
        long result = myHelper.addRecord("name", "Max", "salary",
                20000.00, "hire_date", "2019-10-09 15:00:00");

        if(result == -1)
            Toast.makeText(MainActivity.this, "Insertion Error!",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivity.this, "Insertion worked!",
                    Toast.LENGTH_SHORT).show();


        myHelper.deleteRecord("delete from customer where ID = 2");

        String [] whereArgs = {"12"};
        int rows = myHelper.removeRecord("customer", "id = ?", whereArgs);
        Toast.makeText(MainActivity.this, "Rows deleted!" + rows,
                Toast.LENGTH_SHORT).show();


        //myHelper.updateRecord("update customer set name = 'Teddy', salary = 50000.00 where ID = 3");
        String[] whereArgs = {"4"};
        int rows = myHelper.changeRecord("customer", 16000.00, "id = ?", whereArgs);
        Toast.makeText(MainActivity.this, "Rows deleted!" + rows,
                Toast.LENGTH_SHORT).show();
        */
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        myHelper.close();
    }

    public void onClickViewMentorBtn(View view)
    {
        Intent intent = new Intent(this, MentorView.class);
        startActivity(intent);
    }

    public void onClickViewTerms(View view)
    {
        Intent intent = new Intent(this, TermView.class);
        startActivity(intent);
    }

    public void onClickViewCourses(View view)
    {
        Intent intent = new Intent(this, CourseView.class);
        startActivity(intent);
    }

    public void onClickViewAssessments(View view)
    {
        Intent intent = new Intent(this, AssessmentView.class);
        startActivity(intent);
    }
}
