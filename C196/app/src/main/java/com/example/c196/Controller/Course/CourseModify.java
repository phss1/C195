package com.example.c196.Controller.Course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.c196.Classes.Course;
import com.example.c196.Classes.Term;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;

public class CourseModify extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_modify);
        getSupportActionBar().setTitle("Modify Course");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myHelper = new DBConnector(CourseModify.this);
        myHelper.getWritableDatabase();

        Spinner spinner = findViewById(R.id.courseModifyStatusSpn);
        String[] statusArray = new String[]{"Plan to Take", "Not Started", "In Progress", "Completed"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, statusArray);
        spinner.setAdapter(spinnerAdapter);

        int indexToSelect;
        switch (dp.getAllCourses().get(Course.getSelectedItemIndex()).isStatus()) {
            case "Plan to Take":  indexToSelect = 0;
                spinner.setSelection(indexToSelect);
                break;
            case "Not Started":  indexToSelect = 1;
                spinner.setSelection(indexToSelect);
                break;
            case "In Progress":  indexToSelect = 2;
                spinner.setSelection(indexToSelect);
                break;
            case "Completed":  indexToSelect = 3;
                spinner.setSelection(indexToSelect);
                break;
        }

        Course course = dp.getAllCourses().get(Course.getSelectedItemIndex());
        EditText title = findViewById(R.id.courseModifyTitleTxtFld);
        EditText startDate = findViewById(R.id.courseModifyStartDateTxtFld);
        EditText endDate = findViewById(R.id.courseModifyEndDateTxtFld);

        title.setText(course.getTitle());
        startDate.setText(course.getStartDate());
        endDate.setText(course.getEndDate());
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
        myHelper = new DBConnector(CourseModify.this);
        myHelper.getWritableDatabase();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent intent = new Intent(this, CourseDetailedView.class);
            startActivity(intent);
        }

        return true;
    }
}
