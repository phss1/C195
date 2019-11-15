package com.example.c196.Controller.Goal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.c196.Classes.Assessment;
import com.example.c196.Controller.Assessment.AssessmentAdd;
import com.example.c196.Controller.Assessment.AssessmentModify;
import com.example.c196.Controller.Assessment.AssessmentView;
import com.example.c196.Controller.MainActivity;
import com.example.c196.Controller.Term.Terms;
import com.example.c196.R;
import com.example.c196.Utility.AlarmReceiver;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;
import com.example.c196.Utility.NotificationHelper;
import com.example.c196.Utility.UtilityMethods;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class GoalAdd extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_add);
        getSupportActionBar().setTitle("Add Goal");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent intent = new Intent(this, Terms.class);
            startActivity(intent);
        }

        return true;
    }

    public void onClickCancelBtn(View view)
    {
        Intent intent = new Intent(this, AssessmentView.class);
        startActivity(intent);
    }

    public void onClickSaveBtn(View view) throws ParseException
    {
        String description = ((EditText) findViewById(R.id.descriptionTxtFld)).getText().toString();
        String date = ((EditText) findViewById(R.id.dateTxtFld)).getText().toString();
        Boolean valuesNotNull = !description.isEmpty() && !date.isEmpty() && UtilityMethods.isValidDate(date);

        if (valuesNotNull.equals(true))
        {
            isCheckBoxTicked();
            String slqQuery = "insert into goal(assessment_id, description, date) values("
                    + dp.getAllAssessments().get(Assessment.getSelectedItemIndex()).getId() + ", \""
                    + description + "\", \"" + date + "\");";
            myHelper.insertRecord(slqQuery);

            Intent intent = new Intent(this, AssessmentModify.class);
            startActivity(intent);
        }
    }

    public void isCheckBoxTicked() throws ParseException
    {
        EditText startDate = findViewById(R.id.dateTxtFld);
        String sDate = startDate.getText().toString();

        boolean checked = ((CheckBox) findViewById(R.id.goalAlarmChkBx)).isChecked();
        if(checked)
        {
            if(!sDate.isEmpty())
            {
                EditText tempTitle = findViewById(R.id.descriptionTxtFld);
                String newTitle = tempTitle.getText().toString();
                String title = newTitle + " reminder for your goal.";
                String message = "Reminding you of your goal for " + newTitle;

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                Date date = sdf.parse(sDate);
                long addTwoMin = 15000;
                date.setTime(Calendar.getInstance().getTimeInMillis() + addTwoMin);

                onTimeSet(date, sDate, title, message);
            }
        }
    }

    public void onTimeSet(Date date, String dateString, String title, String message)
    {
        String[] dateValues = dateString.split("/");
        Calendar c = Calendar.getInstance();
        c.set(Integer.valueOf(dateValues[0]), Integer.valueOf(dateValues[1]), Integer.valueOf(dateValues[2]));
        c.set(Calendar.HOUR_OF_DAY, (c.get(Calendar.HOUR_OF_DAY)));
        c.setTime(date);
        //Long time = c.getTimeInMillis();

        startAlarm(c, title, message);
    }

    private void startAlarm(Calendar c, String title, String message)
    {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

        /*
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Context context = getApplicationContext();
        Intent intent = new Intent(this, AlarmReceiver.class);

        Intent intentAlarm = new Intent(context, AlarmReceiver.class);
        //intentAlarm.putExtra("id", id);
        intentAlarm.putExtra("title", title);
        intentAlarm.putExtra("text", message);
        intentAlarm.putExtra("nextAlarmId", nextAlarmId);
        alarmManager.set(AlarmManager.RTC_WAKEUP, c, PendingIntent.getBroadcast(context, nextAlarmId, intentAlarm, PendingIntent.FLAG_ONE_SHOT));
*/

        //PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarmId, intent, 0);
        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }


    /*AlarmHandler.scheduleCourseAlarm(getApplicationContext(), courseId, DateUtil.getDateTimestamp(course.start),
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
    }*/

    /*public void onTimeSet(Date date, String dateString)
    {
        String[] dateValues = dateString.split("/");
        Calendar c = Calendar.getInstance();
        c.set(Integer.valueOf(dateValues[0]), Integer.valueOf(dateValues[1]), Integer.valueOf(dateValues[2]));
        c.set(Calendar.HOUR_OF_DAY, (c.get(Calendar.HOUR_OF_DAY)));
        c.setTime(date);

        startAlarm(c);
    }

    private void startAlarm(Calendar c)
    {
        int alarmId = ar.getAlarmId();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarmId, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }*/

    @Override
    protected void onPause()
    {
        super.onPause();
        myHelper.close();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        myHelper = new DBConnector(GoalAdd.this);
        myHelper.getWritableDatabase();
    }
}
