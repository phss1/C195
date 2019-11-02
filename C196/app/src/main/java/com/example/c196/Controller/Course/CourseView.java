package com.example.c196.Controller.Course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.c196.Classes.*;
import com.example.c196.Controller.MainActivity;
import com.example.c196.Controller.Mentor.MentorAdd;
import com.example.c196.Controller.Mentor.MentorModify;
import com.example.c196.Controller.Term.Terms;
import com.example.c196.R;
import com.example.c196.Utility.*;
import java.util.ArrayList;
import java.util.List;

public class CourseView extends AppCompatActivity
{
    DBConnector myHelper;
    UtilityMethods utilities;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);
        getSupportActionBar().setTitle("View Courses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myHelper = new DBConnector(CourseView.this);
        myHelper.getWritableDatabase();

        List<String> courses = populateListView();
        boolean isCoursesEmpty = courses.isEmpty();
        if(!isCoursesEmpty)
        {
            ArrayAdapter<String> coursesAdapter = new ArrayAdapter<>
            (
            this, android.R.layout.simple_list_item_1, courses
            );
            ListView listView = findViewById(R.id.courseLstVw);
            listView.setAdapter(coursesAdapter);
            listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                    {

                        Mentor.setSelectedItemIndex(i);
                        onClickModifyCourse(view);
                    }
                }
            );
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent intent = new Intent(this, Terms.class);
            startActivity(intent);
        }

        return true;
    }

    public void onActionAddCourse(View view)
    {
        Intent intent = new Intent(this, MentorAdd.class);
        startActivity(intent);
    }

    public void onClickHomeBtn(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickModifyCourse(View view)
    {
        Intent intent = new Intent(this, MentorModify.class);
        startActivity(intent);
    }

    private List<String> populateListView()
    {
        List<String> courseList = new ArrayList<>();
        String query = "SELECT * from course";
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        ArrayList<Assessment> a = new ArrayList<>();
        ArrayList<Note> n = new ArrayList<>();

        dp.getAllCourses().clear();
        while (cursor.moveToNext())
        {
            Course tempCourse = new Course(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),
            a, n, cursor.getString(4), cursor.getString(5));
            dp.addCourse(tempCourse);
            courseList.add(cursor.getString(3));
        }

        return courseList;
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
        myHelper = new DBConnector(CourseView.this);
        myHelper.getWritableDatabase();
    }
}
