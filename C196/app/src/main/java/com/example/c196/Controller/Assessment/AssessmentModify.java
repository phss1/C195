package com.example.c196.Controller.Assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.c196.Classes.Assessment;
import com.example.c196.Classes.Course;
import com.example.c196.Classes.Note;
import com.example.c196.Controller.Goal.GoalAdd;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;
import com.example.c196.Utility.UtilityMethods;
import java.util.ArrayList;
import java.util.List;

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

        if(Course.getSelectedItemIndex() == 0 && !Course.isUseAlternate() == true)
        {
            int assessmentId = assessment.getId();
            String query = "select course_id from assessment where assessment_id = " + assessmentId;
            Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

            int courseId=-1;
            while(cursor.moveToNext())
            {
                courseId = cursor.getInt(0);
            }

            String query2 = "select title from course where course_id = " + courseId;
            Cursor cursor2 = myHelper.getReadableDatabase().rawQuery(query2,null);

            while(cursor2.moveToNext())
            {
                String courseTitle = cursor2.getString(0);
                createCourseList();
                ArrayList<Course> courses = dp.getAllCourses();
                for(Course course : courses)
                {
                    if(course.getTitle().contains(courseTitle))
                    {
                        int index = courses.indexOf(course);
                        Spinner spinner2 = findViewById(R.id.modAssCourseSpn);
                        ArrayList<String> courseArray = UtilityMethods.createCourseSpinnerValues();
                        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter(this,
                                android.R.layout.simple_spinner_item, courseArray);
                        spinner2.setAdapter(spinnerAdapter2);
                        spinner2.setSelection(index);
                    }
                }
            }
        }

        if(Course.isUseAlternate() == true)
        {
            Spinner spinner2 = findViewById(R.id.modAssCourseSpn);
            ArrayList<String> courseArray = UtilityMethods.createCourseSpinnerValues();
            ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, courseArray);
            spinner2.setAdapter(spinnerAdapter2);
            spinner2.setSelection(Course.getSelectedItemIndex());
        }
        else
        {
            Spinner spinner2 = findViewById(R.id.modAssCourseSpn);
            ArrayList<String> courseArray = createCourseList();
            ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, courseArray);
            spinner2.setAdapter(spinnerAdapter2);
            spinner2.setSelection(Course.getSelectedItemIndex());
        }

        /*String query3 = "select * goal where assessment_id = "
                + DataProvider.getAllAssessments().get(Assessment.getSelectedItemIndex()).getId() + ";";
        UtilityMethods.displayGuiMessage(AssessmentModify.this, query3);
        Cursor cursor4 = myHelper.getReadableDatabase().rawQuery(query3,null);
        ArrayList<String> goals = new ArrayList<>();

        while(cursor4.moveToNext())
        {
            goals.add(cursor4.getString(2));
        }
        if(goals.size() > 1)
        {
            ArrayAdapter<String> assessmentsAdapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_list_item_1, goals
            );

            ListView listView = findViewById(R.id.asmtGoalLstVw);
            listView.setAdapter(assessmentsAdapter);
        }*/
    }

    private ArrayList<String> createCourseList()
    {
        String query = "select * from course";
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        ArrayList<Assessment> a = new ArrayList<>();
        ArrayList<Note> n = new ArrayList<>();
        ArrayList<java.lang.String> courseTitles = new ArrayList<>();

        dp.getAllCourses().clear();
        while (cursor.moveToNext())
        {
            Course tempCourse = new Course(cursor.getInt(0), cursor.getString(3), cursor.getString(4),
                    a, n, cursor.getString(5), cursor.getString(6));
            dp.addCourse(tempCourse);
            courseTitles.add(cursor.getString(3));
        }

        return courseTitles;
    }

    public void modAssSaveBtn(View view)
    {
        String title = ((EditText) findViewById(R.id.modAssTitleTxtFld)).getText().toString();
        String startDate = ((EditText) findViewById(R.id.modAssStartDateTxtFld)).getText().toString();
        String endDate = ((EditText) findViewById(R.id.modAssEndDateTxtFld)).getText().toString();
        String assessmentType = ((Spinner) findViewById(R.id.AddATypeSpn)).getSelectedItem().toString();
        String associatedCourseTitle = ((Spinner) findViewById(R.id.modAssCourseSpn)).getSelectedItem().toString();
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
                        String query = "update assessment set course_id = " + courseId + ", "
                                + "title = \"" + title + "\", type = \"" + assessmentType
                                + "\", start_date = \"" + startDate + "\", end_date = \""
                                + endDate + "\" where assessment_id = "
                                + DataProvider.getAllAssessments().get(Assessment.getSelectedItemIndex()).getId()
                                + ";";
                        myHelper.insertRecord(query);

                        Intent intent = new Intent(this, AssessmentView.class);
                        startActivity(intent);
                    }
                }
            }
            else
            {
                UtilityMethods.displayGuiMessage(AssessmentModify.this, "Please make sure no fields are null and date fields are filled out in correct format.");
            }
        } catch (Exception e)
        {
            //
        }
    }

    public void onClickAsmtAddGoal(View view)
    {
        Intent intent = new Intent(this, GoalAdd.class);
        startActivity(intent);
    }

    public void onClickModAssDeleteBtn(View view)
    {
        try
        {
            String asmtTitle = ((EditText) findViewById(R.id.modAssTitleTxtFld)).getText().toString();
            String query = "delete from assessment where title = \""
                    + asmtTitle + "\";";
            myHelper.deleteRecord(query);

            Intent intent = new Intent(this, AssessmentView.class);
            startActivity(intent);
        }
        catch(Exception e)
        {
            //
        }
    }

    public void modAssCancelBtn(View view)
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
        myHelper = new DBConnector(AssessmentModify.this);
        myHelper.getWritableDatabase();
    }
}
