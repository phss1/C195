package com.example.c196.Controller.Goal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.c196.Classes.Assessment;
import com.example.c196.Controller.Assessment.AssessmentModify;
import com.example.c196.Controller.Assessment.AssessmentView;
import com.example.c196.Controller.MainActivity;
import com.example.c196.Controller.Term.Terms;
import com.example.c196.R;
import com.example.c196.Utility.AlarmReceiver;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;
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

        if (valuesNotNull.equals(true)) {
            String slqQuery = "insert into goal(assessment_id, description, date) values("
                    + dp.getAllAssessments().get(Assessment.getSelectedItemIndex()).getId() + ", \""
                    + description + "\", \"" + date + "\");";
            UtilityMethods.displayGuiMessage(GoalAdd.this, "" + slqQuery);
            myHelper.insertRecord(slqQuery);

            boolean checked = ((CheckBox) findViewById(R.id.goalAlarmChkBx)).isChecked();
            if (checked) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                Date currentDate = sdf.parse(date);
                long addTwoMin = 30000;
                currentDate.setTime(Calendar.getInstance().getTimeInMillis() + addTwoMin);
                //long lStartDate = currentDate.getTime();

                int id = UtilityMethods.createUniqueId();
                String title = description + " - Reminder";
                String message = "Reminding you of your goal: " + description;
                onTimeSet(currentDate, date, title, message, id);
            }

            Intent intent = new Intent(this, AssessmentModify.class);
            startActivity(intent);
        }

        try
        {


        }
        catch (Exception e)
        {

        }
    }

    //

    public void onTimeSet(Date date, String dateString, String title, String message, int alarmId)
    {
        String[] dateValues = dateString.split("/");
        Calendar c = Calendar.getInstance();
        c.set(Integer.valueOf(dateValues[0]), Integer.valueOf(dateValues[1]), Integer.valueOf(dateValues[2]));
        c.set(Calendar.HOUR_OF_DAY, (c.get(Calendar.HOUR_OF_DAY)));
        c.setTime(date);

        startAlarm(c, title, message, alarmId);
    }

    private void startAlarm(Calendar c, String title, String message, int alarmId)
    {
        AlarmReceiver receiver = new AlarmReceiver();
        receiver.setTitle(title);
        receiver.setMessage(message);
        receiver.setAlarmId(alarmId);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarmId, intent, 0);

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
