package com.example.c196.Controller.Assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_modify);
        getSupportActionBar().setTitle("Modify Assessment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myHelper = new DBConnector(AssessmentModify.this);
        myHelper.getWritableDatabase();

        Assessment assessment = dp.getAllAssessments().get(Assessment.getSelectedItemIndex());
        EditText title = findViewById(R.id.modAssTitleTxtFld);
        EditText startDate = findViewById(R.id.modAssStartDateTxtFld);
        EditText endDate = findViewById(R.id.modAssEndDateTxtFld);
        title.setText(assessment.getTitle());
        startDate.setText(assessment.getStartDate());
        endDate.setText(assessment.getEndDate());

        String assessmentType = (dp.getAllAssessments().get(Assessment.getSelectedItemIndex())).getType();
        Spinner spinner1 = findViewById(R.id.AddATypeSpn);
        String[] statusArray = new String[]{"Project", "Objective"};
        ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, statusArray);
        spinner1.setAdapter(spinnerAdapter1);

        switch (assessmentType)
        {
            case "Project":
                spinner1.setSelection(0);
                break;
            case "Objective":
                spinner1.setSelection(1);
                break;
        }

        UtilityMethods.displayGuiMessage(AssessmentModify.this, "String course selected index null: " + Course.getSelectedItemIndex());

        if(Course.getSelectedItemIndex() == 0)
        {
            int assessmentId = assessment.getId();
            String query = "select course_id from assessment where assessment_id = " + assessmentId;
            Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

            int courseId=-1;
            while(cursor.moveToNext())
            {
                courseId = cursor.getInt(0);
            }

            String query2 = "select * from course where course_id = " + courseId;
            Cursor cursor2 = myHelper.getReadableDatabase().rawQuery(query2,null);

            while(cursor2.moveToNext())
            {
                String courseTitle = cursor2.getString(0);
                ArrayList<Course> courses = dp.getAllCourses();
                for(Course course : courses)
                {
                    if(course.getTitle().contains(courseTitle))
                    {
                        int index = courses.indexOf(course);
                        Course.setSelectedItemIndex(index);
                    }
                }
            }
        }

        Spinner spinner2 = findViewById(R.id.modAssCourseSpn);
        ArrayList<String> courseArray = UtilityMethods.createCourseSpinnerValues();
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, courseArray);
        spinner2.setAdapter(spinnerAdapter2);
        spinner2.setSelection(Course.getSelectedItemIndex());
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
