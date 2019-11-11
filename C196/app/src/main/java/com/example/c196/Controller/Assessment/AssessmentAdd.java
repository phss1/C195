package com.example.c196.Controller.Assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.c196.Classes.Assessment;
import com.example.c196.Classes.Course;
import com.example.c196.Classes.Note;
import com.example.c196.Controller.MainActivity;
import com.example.c196.R;
import com.example.c196.Utility.AlarmReceiver;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;
import com.example.c196.Utility.UtilityMethods;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class AssessmentAdd extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp = new DataProvider();
    AlarmReceiver ar = new AlarmReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_add);
        getSupportActionBar().setTitle("Add Assessment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myHelper = new DBConnector(AssessmentAdd.this);
        myHelper.getWritableDatabase();

        Spinner spinner1 = findViewById(R.id.addATypeSpn);
        String[] statusArray = new String[]{"Project", "Objective"};
        ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, statusArray);
        spinner1.setAdapter(spinnerAdapter1);

        Spinner spinner2 = findViewById(R.id.addAssessmentSpinner);
        ArrayList<String> courseArray = new ArrayList<>();

        if(UtilityMethods.createCourseSpinnerValues().size() <= 0 && !Course.isUseAlternate() == true)
        {
            courseArray = populateCourseList();
        }
        else
        {
            courseArray = UtilityMethods.createCourseSpinnerValues();
        }

        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, courseArray);
        spinner2.setAdapter(spinnerAdapter2);
        spinner2.setSelection(Course.getSelectedItemIndex());

    }

    public void checkedEnableAlarmChkBx(View view) throws ParseException
    {
        String id = new Random().toString();
        EditText startDate = findViewById(R.id.startDateTxtFld);
        String sDate = startDate.getText().toString();
        //UtilityMethods.displayGuiMessage(AssessmentAdd.this, sDate);

        boolean checked = ((CheckBox) view).isChecked();
        if(checked)
        {
            if(!sDate.isEmpty())
            {
                EditText tempTitle = findViewById(R.id.modAssTitleTxtFld);
                String newTitle = tempTitle.getText().toString();
                String title = newTitle + " Reminder";
                String message = "Reminding you of your goal for " + newTitle;
                int newAlarmId = UtilityMethods.createUniqueId();

                ar.setTitle(title);
                ar.setMessage(message);
                ar.setAlarmId(newAlarmId);

                AlarmReceiver ar = new AlarmReceiver();
                String sTitle = ar.getTitle();
                UtilityMethods.displayGuiMessage(AssessmentAdd.this, sTitle);

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                Date date = sdf.parse(sDate);
                long addTwoMin = 2000;
                date.setTime(Calendar.getInstance().getTimeInMillis() + addTwoMin);
                //long lStartDate = date.getTime();
                //UtilityMethods.displayGuiMessage(AssessmentAdd.this, ""+ Calendar.getInstance().get(Calendar.HOUR_OF_DAY));

                onTimeSet(date, sDate);
            }
        }
    }

    public void onTimeSet(Date date, String dateString)
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
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    public void onClickAddAssSaveBtn(View view)
    {
        String title = ((EditText) findViewById(R.id.titleTxtFld)).getText().toString();
        String startDate = ((EditText) findViewById(R.id.startDateTxtFld)).getText().toString();
        String endDate = ((EditText) findViewById(R.id.endDateTxtFld)).getText().toString();
        String assessmentType = ((Spinner) findViewById(R.id.addATypeSpn)).getSelectedItem().toString();
        String associatedCourseTitle = ((Spinner) findViewById(R.id.addAssessmentSpinner)).getSelectedItem().toString();
        Boolean valuesNotNull = !title.isEmpty() && UtilityMethods.isValidDate(startDate) == true
                && UtilityMethods.isValidDate(endDate) == true;

        try
        {
            if (valuesNotNull)
            {
                ArrayList<Course> courses = dp.getAllCourses();
                for(Course course : courses)
                {
                    boolean foundTitleMatch = course.getTitle().contains(associatedCourseTitle) ? true : false;
                    if(foundTitleMatch)
                    {
                        int courseId = course.getId();

                        String query = "insert into assessment(course_id, title, type, start_date, end_date) " +
                                "values(" + courseId + ", \"" + title + "\", \"" + assessmentType + "\", \""
                                + startDate + "\", \"" + endDate + "\");";
                        myHelper.insertRecord(query);

                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
            else
            {
                UtilityMethods.displayGuiMessage(AssessmentAdd.this, "Please make sure no fields are null and date fields are filled out in correct format.");
            }
        } catch (Exception e)
        {
            //
        }
    }

    private ArrayList<String> populateCourseList()
    {
        ArrayList<String> courseList = new ArrayList<>();
        String query = "SELECT * from course";
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        ArrayList<Assessment> a = new ArrayList<>();
        ArrayList<Note> n = new ArrayList<>();

        dp.getAllCourses().clear();
        while (cursor.moveToNext())
        {
            Course tempCourse = new Course(cursor.getInt(0), cursor.getString(3), cursor.getString(4),
                    a, n, cursor.getString(5), cursor.getString(6));
            dp.addCourse(tempCourse);
            courseList.add(cursor.getString(3));
        }

        return courseList;
    }

    public void onClickCancelBtn(View view)
    {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            finish();
        }

        return true;
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
        myHelper = new DBConnector(AssessmentAdd.this);
        myHelper.getWritableDatabase();
    }
}
