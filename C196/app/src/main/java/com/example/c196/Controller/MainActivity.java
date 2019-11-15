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

        String query1 = "delete from goal";
        myHelper.deleteRecord(query1);
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

    /*

    AlarmHandler.scheduleCourseAlarm(getApplicationContext(), courseId, DateUtil.getDateTimestamp(course.start),
                    "Course starts today!", course.name + " begins on " + course.start);
    public static boolean scheduleCourseAlarm(Context context, long id, long time, String title, String text) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int nextAlarmId = getNextAlarmId(context);
        Intent intentAlarm = new Intent(context, AlarmHandler.class);
        intentAlarm.putExtra("id", id);
        intentAlarm.putExtra("title", title);
        intentAlarm.putExtra("text", text);
        intentAlarm.putExtra("destination", "course");
        intentAlarm.putExtra("nextAlarmId", nextAlarmId);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(context, nextAlarmId, intentAlarm, PendingIntent.FLAG_ONE_SHOT));

        SharedPreferences sp = context.getSharedPreferences(courseAlarmFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(Long.toString(id), nextAlarmId);
        editor.commit();

        incrementNextAlarmId(context);
        return true;
    }
     */
}
