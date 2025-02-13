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
            Intent intent = new Intent(this, AssessmentModify.class);
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
        Boolean valuesNotNull = !description.isEmpty() && !date.isEmpty()
                && UtilityMethods.isValidDate(date);

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
                String title = "Assessment Goal Reminder";
                String message = "Your goal " + newTitle + " is due today " + sDate + ".";

                String[] dateValues = sDate.split("/");
                Calendar c = Calendar.getInstance();
                int month = Integer.valueOf(dateValues[0]);
                int day = Integer.valueOf(dateValues[1]);
                int year = Integer.valueOf(dateValues[2]);
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                int second = c.get(Calendar.SECOND) + 30;
                c.set(Calendar.MONTH, month - 1);
                c.set(Calendar.DAY_OF_MONTH, day);
                c.set(Calendar.YEAR, year);
                c.set(Calendar.HOUR_OF_DAY, hour);
                c.set(Calendar.MINUTE, minute);
                c.set(Calendar.SECOND, second);

                startAlarm(c, title, message, "one");
            }
        }
    }

    public void onTimeSet(Date date, String dateString, String title,
                          String message, String channel)
    {
        String[] dateValues = dateString.split("/");
        Calendar c = Calendar.getInstance();
        c.set(Integer.valueOf(dateValues[0]), Integer.valueOf(dateValues[1]),
                Integer.valueOf(dateValues[2]));
        c.set(Calendar.HOUR_OF_DAY, (c.get(Calendar.HOUR_OF_DAY)));
        c.setTime(date);
        //Long time = c.getTimeInMillis();

        startAlarm(c, title, message, channel);
    }

    private void startAlarm(Calendar c, String title, String message, String channel)
    {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        intent.putExtra("channel", channel);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                UtilityMethods.createUniqueId(), intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

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
