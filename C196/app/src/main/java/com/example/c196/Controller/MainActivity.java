package com.example.c196.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c196.Controller.Assessment.AssessmentView;
import com.example.c196.Controller.Course.Courses;
import com.example.c196.Controller.Mentor.MentorView;
import com.example.c196.Controller.Term.Terms;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;

public class MainActivity extends AppCompatActivity
{
    DBConnector myHelper;

    //TODO make sure to set up TermDetailedView listview to change to viewcourse screen

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

        myHelper = new DBConnector(MainActivity.this);
        myHelper.getWritableDatabase();
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
        Intent intent = new Intent(this, Terms.class);
        startActivity(intent);
    }

    public void onClickViewCourses(View view)
    {
        Intent intent = new Intent(this, Courses.class);
        startActivity(intent);
    }

    public void onClickViewAssessments(View view)
    {
        Intent intent = new Intent(this, AssessmentView.class);
        startActivity(intent);
    }
}
