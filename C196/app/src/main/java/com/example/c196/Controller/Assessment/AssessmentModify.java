package com.example.c196.Controller.Assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.c196.Classes.Assessment;
import com.example.c196.Classes.Course;
import com.example.c196.Classes.Goal;
import com.example.c196.Classes.Note;
import com.example.c196.Controller.Course.CourseDetailedView;
import com.example.c196.Controller.Term.Terms;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;
import com.example.c196.Utility.UtilityMethods;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.example.c196.Utility.DataProvider.getAllAssessments;

public class AssessmentModify extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_modify);
        getSupportActionBar().setTitle("Modify Assessment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myHelper = new DBConnector(AssessmentModify.this);
        myHelper.getWritableDatabase();



        Spinner spinner1 = findViewById(R.id.AddATypeSpn);
        String[] statusArray = new String[]{"Project", "Objective"};
        ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, statusArray);
        spinner1.setAdapter(spinnerAdapter1);

        Spinner spinner2 = findViewById(R.id.modAssCourseSpn);
        ArrayList<String> courseArray = UtilityMethods.createCourseSpinnerValues();
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, courseArray);
        spinner2.setAdapter(spinnerAdapter2);
        spinner2.setSelection(Course.getSelectedItemIndex());
    }

    private List<String> populateListView()
    {
        List<String> assessmentList = new ArrayList<>();
        String query = "SELECT * from assessment";
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);
        ArrayList<Goal> a = new ArrayList<>();

        dp.getAllAssessments().clear();
        while (cursor.moveToNext())
        {
            Assessment assessment = new Assessment(cursor.getInt(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), a);
            dp.addAssessment(assessment);
            assessmentList.add(cursor.getString(3));
        }
/*
        ArrayList<Assessment> assessments = dp.getAllAssessments();
        String selectedTermCourseTitle = courses.get(index);
        for(Course course : allCourses)
        {
            boolean foundTitleMatch = course.getTitle().contains(selectedTermCourseTitle) ? true : false;
            if(foundTitleMatch)
            {
                Course.setSelectedItemIndex(allCourses.indexOf(course));
                Intent intent = new Intent(this, CourseDetailedView.class);
                startActivity(intent);
                break;
            }
        }
*/
        return assessmentList;
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
        myHelper = new DBConnector(AssessmentModify.this);
        myHelper.getWritableDatabase();
    }
}
