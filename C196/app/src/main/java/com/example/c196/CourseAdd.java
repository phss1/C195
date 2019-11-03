package com.example.c196;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.c196.Controller.Course.CourseDetailedView;
import com.example.c196.Controller.Course.CourseModify;
import com.example.c196.Controller.Course.Courses;
import com.example.c196.Utility.DBConnector;

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
        Intent intent = new Intent(this, CourseModify.class);
        startActivity(intent);
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
