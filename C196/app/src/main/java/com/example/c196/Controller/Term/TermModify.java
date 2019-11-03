package com.example.c196.Controller.Term;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.c196.Classes.*;
import com.example.c196.R;
import com.example.c196.Utility.*;

import java.util.ArrayList;
import java.util.List;

public class TermModify extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_modify);
        getSupportActionBar().setTitle("Modify Term");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myHelper = new DBConnector(TermModify.this);
        myHelper.getWritableDatabase();

        Term term = dp.getAllTerms().get(Term.getSelectedItemIndex());
        EditText title = findViewById(R.id.termModifyTitleTxtFld);
        EditText startDate = findViewById(R.id.termModifyStartDateTxtFld);
        EditText endDate = findViewById(R.id.termModifyEndDateTxtFld);

        title.setText(term.getTitle());
        startDate.setText(term.getStartDate());
        endDate.setText(term.getEndDate());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent intent = new Intent(this, TermDetailedView.class);
            startActivity(intent);
        }

        return true;
    }

    public void onClickCancelBtn(View view)
    {
        Intent intent = new Intent(this, Terms.class);
        startActivity(intent);
    }

    public void onClickSaveBtn(View view)
    {
        String title = ((EditText) findViewById(R.id.termModifyTitleTxtFld)).getText().toString();
        String startDate = ((EditText) findViewById(R.id.termModifyStartDateTxtFld)).getText().toString();
        String endDate = ((EditText) findViewById(R.id.termModifyEndDateTxtFld)).getText().toString();
        Boolean valuesNotNull = !title.isEmpty() && !startDate.isEmpty() && !endDate.isEmpty()
                && UtilityMethods.isValidDate(startDate) == true
                && UtilityMethods.isValidDate(endDate) == true;

        try
        {
            if(valuesNotNull)
            {
                String sqlQuery = "update term set title = \"" + title + "\", start_date = \""
                        + startDate + "\", end_date = \"" + endDate + "\" where term_id = "
                        + DataProvider.getAllTerms().get(Term.getSelectedItemIndex()).getId();
                myHelper.insertRecord(sqlQuery);

                Intent intent = new Intent(this, Terms.class);
                startActivity(intent);
            }
            else
            {
                UtilityMethods.displayGuiMessage(TermModify.this, "Please meake sure values are not null and date value is entered correctly.");
            }
        }
        catch(Exception e)
        {
            //
        }
    }

    private List<String> populateListView()
    {
        List<String> courseList = new ArrayList<>();
        String query = "SELECT * from course where term_id = " + DataProvider.getAllTerms().get(Term.getSelectedItemIndex()).getId();
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        ArrayList<Assessment> a = new ArrayList<>();
        ArrayList<Note> n = new ArrayList<>();

        dp.getAllCourses().clear();
        while (cursor.moveToNext())
        {
            Course tempCourse = new Course(cursor.getInt(0), cursor.getString(3), cursor.getString(4),
                    a, n, cursor.getString(5), cursor.getString(6));
            dp.addCourse(tempCourse);
            courseList.add(cursor.getString(3));
        }

        return courseList;
    }

    public void onClickAddTermCourse(View view)
    {
        Intent intent = new Intent(this, TermAddCourses.class);
        startActivity(intent);
    }

    public void onClickRemoveTermCourse(View view)
    {
        List<String> termCourses = populateListView();
        if(termCourses.size() > 0)
        {
            Intent intent = new Intent(this, TermRemoveCourse.class);
            startActivity(intent);
        }
        else
        {
            UtilityMethods.displayGuiMessage(TermModify.this, "Please add a course to the term first.");
        }
    }

    public void onClickDeleteTerm(View view)
    {
        String query = "select * from course where term_id = " + DataProvider.getAllTerms().get(Term.getSelectedItemIndex()).getId();
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        boolean coursesInTerm = cursor.getCount() > 0 ? true : false;
        UtilityMethods.displayGuiMessage(TermModify.this, "more than one item: " + coursesInTerm);

        if(!coursesInTerm)
        {
            String query2 = "delete from term where term_id = " + DataProvider.getAllTerms().get(Term.getSelectedItemIndex()).getId();
            myHelper.deleteRecord(query2);
            String query3= "update course set term_id = -1 where term_id = " + DataProvider.getAllTerms().get(Term.getSelectedItemIndex()).getId();
            myHelper.deleteRecord(query3);

            Intent intent = new Intent(this, Terms.class);
            startActivity(intent);
        }
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
        myHelper = new DBConnector(TermModify.this);
        myHelper.getWritableDatabase();
    }
}
