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
        myHelper = new DBConnector(AssessmentAdd.this);
        myHelper.getWritableDatabase();

        Spinner spinner1 = findViewById(R.id.addATypeSpn);
        String[] statusArray = new String[]{"Project", "Objective"};
        ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, statusArray);
        spinner1.setAdapter(spinnerAdapter1);

        Spinner spinner2 = findViewById(R.id.addAssessmentSpinner);
        ArrayList<String> courseArray = UtilityMethods.createCourseSpinnerValues();
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, courseArray);
        spinner2.setAdapter(spinnerAdapter2);
        spinner2.setSelection(Course.getSelectedItemIndex());
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
                        finish();
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
