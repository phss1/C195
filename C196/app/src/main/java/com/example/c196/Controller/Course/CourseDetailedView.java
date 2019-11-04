package com.example.c196.Controller.Course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.c196.Classes.Assessment;
import com.example.c196.Classes.Course;
import com.example.c196.Classes.Term;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class CourseDetailedView extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detailed_view);
        getSupportActionBar().setTitle("Course Detailed View");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myHelper = new DBConnector(CourseDetailedView.this);
        myHelper.getWritableDatabase();

        TextView title = findViewById(R.id.courseDVTitleTxtVw);
        TextView startDate = findViewById(R.id.courseDVStartDateTxtVw2);
        TextView endDate = findViewById(R.id.courseDVEndDateTxtVw);

        Course course = dp.getAllCourses().get(Course.getSelectedItemIndex());
        title.setText(course.getTitle());
        startDate.setText(course.getStartDate());
        endDate.setText(course.getEndDate());

        List<String> courseAssessments = populateListView();
        Boolean isCourseAssessmentsNull = courseAssessments.isEmpty();
        if(!isCourseAssessmentsNull)
        {
            ArrayAdapter<String> termCoursesAdapter = new ArrayAdapter<String>
                    (
                            this, android.R.layout.simple_list_item_1, courseAssessments
                    );

            ListView listView = findViewById(R.id.courseDVAssessmentLstVw);
            listView.setAdapter(termCoursesAdapter);
            listView.setChoiceMode(2);
        }
    }

    private List<String> populateListView()
    {
        List<String> courseList = new ArrayList<>();
        int courseId = DataProvider.getAllCourses().get(Course.getSelectedItemIndex()).getId();
        String query = "SELECT * from assessment where course_id = " + courseId;
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        while (cursor.moveToNext())
        {
            courseList.add(cursor.getString(2));
        }

        return courseList;
    }

    public void onClickModifyCourseBtn(View view)
    {
        Intent intent = new Intent(this, CourseModify.class);
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
        myHelper = new DBConnector(CourseDetailedView.this);
        myHelper.getWritableDatabase();
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
}
