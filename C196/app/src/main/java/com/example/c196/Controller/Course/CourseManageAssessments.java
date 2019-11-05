package com.example.c196.Controller.Course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.c196.Classes.Assessment;
import com.example.c196.Classes.Course;
import com.example.c196.Classes.Goal;
import com.example.c196.Classes.Note;
import com.example.c196.Classes.Term;
import com.example.c196.Controller.Assessment.AssessmentModify;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;
import com.example.c196.Utility.UtilityMethods;

import java.util.ArrayList;
import java.util.List;

public class CourseManageAssessments extends AppCompatActivity
{

    DBConnector myHelper;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_manage_assessments);
        getSupportActionBar().setTitle("Manage Assessments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myHelper = new DBConnector(CourseManageAssessments.this);
        myHelper.getWritableDatabase();

        List<String> assessments = populateListView();
        Boolean isAssessmentListEmpty = assessments.isEmpty();
        if(!isAssessmentListEmpty)
        {
            ArrayAdapter<String> assessmentAdapter = new ArrayAdapter<>
                    (
                            this, android.R.layout.simple_list_item_single_choice, assessments
                    );

            ListView listView = findViewById(R.id.courseManAssessmentsLstVw);
            listView.setAdapter(assessmentAdapter);
            listView.setChoiceMode(1);
        }
    }

    public void onClickCourseAModifyBtn(View view)
    {
        String query = "select * from assessment";
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);
        ArrayList<Goal> a = new ArrayList<>();

        dp.getAllAssessments().clear();

        ListView listView = findViewById(R.id.courseManAssessmentsLstVw);
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
            if (checked.get(i))
            {
                while (cursor.moveToNext())
                {
                    Assessment assessment = new Assessment(cursor.getInt(1), cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getString(5), a);
                    dp.addAssessment(assessment);
                }

                String assessmentTitle = listView.getItemAtPosition(i).toString();
                ArrayList<Assessment> assessments = dp.getAllAssessments();
                for(Assessment assessment : assessments)
                {
                    boolean foundTitleMatch = assessment.getTitle().contains(assessmentTitle) ? true : false;
                    if(foundTitleMatch)
                    {
                        Assessment.setSelectedItemIndex(assessments.indexOf(assessment));
                        Intent intent = new Intent(this, AssessmentModify.class);
                        startActivity(intent);
                        break;
                    }
                }
                break;
            }
        }
    }

    public static void populateAssessmentList()
    {

    }

    public void onClickCourseADeleteBtn(View view)
    {
        try
        {
            ListView listView = findViewById(R.id.courseManAssessmentsLstVw);
            SparseBooleanArray checked = listView.getCheckedItemPositions();
            for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                if (checked.get(i))
                {
                    String query = "delete from assessment where title = \""
                            + listView.getItemAtPosition(i) + "\";";
                    myHelper.deleteRecord(query);

                    Intent intent = new Intent(this, Courses.class);
                    startActivity(intent);
                }
            }
        }
        catch(Exception e)
        {
            //
        }


    }

    private List<String> populateListView()
    {
        List<String> assessmentList = new ArrayList<>();
        String query = "SELECT * from assessment";
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);
        ArrayList<Goal> a = new ArrayList<>();

        while (cursor.moveToNext())
        {
            Assessment assessment = new Assessment(cursor.getInt(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), a);
            dp.addAssessment(assessment);
            assessmentList.add(cursor.getString(2));
        }

        return assessmentList;
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
        myHelper = new DBConnector(CourseManageAssessments.this);
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
