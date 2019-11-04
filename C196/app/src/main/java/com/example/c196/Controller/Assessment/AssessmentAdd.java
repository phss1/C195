package com.example.c196.Controller.Assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.c196.Classes.Course;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;
import com.example.c196.Utility.UtilityMethods;

import java.util.ArrayList;

public class AssessmentAdd extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_add);
        getSupportActionBar().setTitle("Add Assessment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Spinner spinner = findViewById(R.id.addAssessmentSpinner);

        ArrayList<String> courseArray = UtilityMethods.createCourseSpinnerValues();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, courseArray);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(Course.getSelectedItemIndex());
    }

    public void onClickSaveBtn(View view)
    {
        String title = ((EditText) findViewById(R.id.titleTxtFld)).getText().toString();
        String startDate = ((EditText) findViewById(R.id.startDateTxtFld)).getText().toString();
        String endDate = ((EditText) findViewById(R.id.endDateTxtFld)).getText().toString();
        String associatedCourseTitle = ((Spinner) findViewById(R.id.addAssessmentSpinner)).getSelectedItem().toString();
        Boolean valuesNotNull = !title.isEmpty() && !startDate.isEmpty() && !endDate.isEmpty()
                && UtilityMethods.isValidDate(startDate) == true
                && UtilityMethods.isValidDate(endDate) == true;

        ArrayList<Course> courses = dp.getAllCourses();
        for(Course course : courses)
        {
            boolean foundTitleMatch = course.getTitle().contains(associatedCourseTitle) ? true : false;
            if(foundTitleMatch)
            {
                UtilityMethods.displayGuiMessage(AssessmentAdd.this, "There was a match found: " + associatedCourseTitle);
                int courseId = course.getId();

                String query = "insert into assessment(course_id, title, start_date, end_date) " +
                        "values(" + courseId + ", \"" + title + "\", \"" + startDate + "\", \""
                        + endDate + "\", " + ");";
                //UtilityMethods.displayGuiMessage(AssessmentAdd.this, "" + query);
                myHelper.insertRecord(query);
                break;
            }
        }
        /*
        try
        {
            if (valuesNotNull.equals(true))
            {
                String slqQuery = "insert into assessment(course_id, title, description, ) values(" + "\"" + title + "\""
                        + ", \"" + description + "\", \"" + startDate + "\");";

                myHelper.insertRecord(slqQuery);

                finish();
            }
            else
            {
                //
            }
        } catch (Exception e)
        {
            //
        }*/
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
