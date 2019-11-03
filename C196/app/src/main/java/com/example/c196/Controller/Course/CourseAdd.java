package com.example.c196.Controller.Course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.UtilityMethods;

public class CourseAdd extends AppCompatActivity
{
    DBConnector myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);
        getSupportActionBar().setTitle("Add Course");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myHelper = new DBConnector(CourseAdd.this);
        myHelper.getWritableDatabase();

        Spinner spinner = findViewById(R.id.addCourseStatusSpn);
        String[] statusArray = new String[]{"Not Started", "In Progress", "Completed"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, statusArray);
        spinner.setAdapter(spinnerAdapter);
    }

    public void onClickAddCourseSaveBtn(View view)
    {
        String title = ((EditText) findViewById(R.id.addCourseTitleTxtFld)).getText().toString();
        String status = ((Spinner) findViewById(R.id.addCourseStatusSpn)).getSelectedItem().toString();
        String startDate = ((EditText) findViewById(R.id.addCourseStartDateTxtFld)).getText().toString();
        String endDate = ((EditText) findViewById(R.id.addCourseEndDateTxtFld)).getText().toString();

        Boolean valuesNotNull = !title.isEmpty() && !status.isEmpty()
                && !startDate.isEmpty() && !endDate.isEmpty()
                && UtilityMethods.isValidDate(startDate) == true
                && UtilityMethods.isValidDate(endDate) == true;
        try
        {
            if(valuesNotNull)
            {
                String sqlQuery = "insert into course(term_id, mentor_id, title, status,  " +
                        "start_date, end_date) values(-1, -1, \"" + title
                        + "\", \"" + status  + "\", \"" + startDate + "\", \"" + endDate + "\");";
                myHelper.insertRecord(sqlQuery);

                Intent intent = new Intent(this, Courses.class);
                startActivity(intent);
            }
            else
            {
                UtilityMethods.displayGuiMessage(CourseAdd.this, "Please make sure values are not null and date value is entered correctly.");
            }
        }
        catch(Exception e)
        {
            //
        }
    }

    public void onClickAddCourseCancelBtn(View view)
    {
        Intent intent = new Intent(this, Courses.class);
        startActivity(intent);
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
        myHelper = new DBConnector(CourseAdd.this);
        myHelper.getWritableDatabase();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent intent = new Intent(this, Courses.class);
            startActivity(intent);
        }

        return true;
    }
}
