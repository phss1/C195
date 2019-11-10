package com.example.c196.Controller;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.c196.Classes.NotificationHelper;
import com.example.c196.Controller.Assessment.AssessmentView;
import com.example.c196.Controller.Course.Courses;
import com.example.c196.Controller.Mentor.MentorView;
import com.example.c196.Controller.Term.Terms;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;

public class MainActivity extends AppCompatActivity
{
    DBConnector myHelper;
    NotificationHelper notificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myHelper = new DBConnector(MainActivity.this);
        myHelper.getWritableDatabase();
        myHelper.createTables();

        notificationHelper = new NotificationHelper(this);

        String query = "select status from course";
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        double completed = 0;
        int notCompleted = 0;
        double totalCourseCount = 0;
        while(cursor.moveToNext())
        {
            double test = cursor.getString(0).contains("Completed") ? completed++ : notCompleted++;
            totalCourseCount++;
        }

        String percentDone = String.valueOf((completed / totalCourseCount) * 100);
        String degreeProgress = "Degree Completion - " + percentDone + "%";
        TextView degreeStatus = findViewById(R.id.testTxtVw);
        degreeStatus.setText(degreeProgress);

        // TODO notifications for assessment goal dates
        // TODO create scheduler app
        // TODO story board
        // TODO do signed apk for deployment package and take screen shots
        // TODO Short essay on project reflection

        sendNotification();
    }

    public void sendNotification()
    {
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification("Test Title", "Test Message");
        notificationHelper.getManager().notify(1, nb.build());
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
