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
        /*myHelper.insertRecord("insert into course(term_id, mentor_id, title, status, start_date, end_date) " +
                "values(-1,-1,\"Course1\",\'true\',\"01/01/2020\",\"12/01/2020\");");
        myHelper.insertRecord("insert into assessment(course_id, title, date) values(-1, \"A1\", \"01/01/2020\");");
        myHelper.insertRecord("insert into assessment(course_id, title, date) values(-1, \"A2\", \"01/01/2020\");");
        myHelper.insertRecord("insert into assessment(course_id, title, date) values(-1, \"A3\", \"01/01/2020\");");
        myHelper.insertRecord("insert into assessment(course_id, title, date) values(-1, \"A4\", \"01/01/2020\");");
        */

        /*
        Next work on Mentor modify screen to get list of courses working

        // TODO modify mentors on modify course screen
        // TODO modify terms on modify course screen
        // TODO fix issue where
        // TODO add mentor and course status info to course detailed view
        // TODO setup percentage done on main screen based on courses with completed to not completed. find out if based on all courses or term?
        // TODO notifications for assessment goal dates
        // TODO create scheduler app
        // TODO story board
        // TODO do signed apk for deployment package and take screen shots
        // TODO Short essay on project reflection
            */

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
