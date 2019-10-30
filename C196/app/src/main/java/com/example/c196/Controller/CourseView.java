package com.example.c196.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.c196.Classes.Mentor;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;
import com.example.c196.Utility.UtilityMethods;

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
        List<String> mentorList = new ArrayList<>();
        String query = "SELECT * from course";
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        dp.getAllMentors().clear();
        while (cursor.moveToNext())
        {
            //Course tempMentor = new Course(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
            //dp.addMentor(tempMentor);
            mentorList.add(cursor.getString(1));
        }

        return mentorList;
    }
}
