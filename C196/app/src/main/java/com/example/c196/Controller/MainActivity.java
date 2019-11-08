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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myHelper = new DBConnector(MainActivity.this);
        myHelper.getWritableDatabase();
        myHelper.createTables();

        // TODO complete course modify delete button
        // TODO modify mentors on modify course screen
        // TODO modify terms on modify course screen
        // TODO add mentor and course status info to course detailed view
        // TODO make sure to set up TermDetailedView listview to change to viewcourse screen
        // TODO setup percentage done on main screen based on courses with completed to not completed. find out if based on all courses or term?
        // TODO notifications for assessment goal dates
        // TODO create scheduler app
        // TODO story board
        // TODO do signed apk for deployment package and take screen shots
        // TODO Short essay on project reflection
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
