package com.example.c196.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
            Intent intent = new Intent(this, Terms.class);
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
        Intent intent = new Intent(this, Terms.class);
        startActivity(intent);
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
            Course tempCourse = new Course(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),
                    a, n, cursor.getString(4), cursor.getString(5));
            dp.addCourse(tempCourse);
            courseList.add(cursor.getString(3));
        }

        return courseList;
    }

    public void onClickAddTermCourse(View view)
    {
        Intent intent = new Intent(this, TermAddCourse.class);
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

        }

        //String query = "delete from term where term_id = " + DataProvider.getAllTerms().get(Term.getSelectedItemIndex()).getId();
        //myHelper.deleteRecord(query);

        // TODO make sure to check for any existing courses before running this
        //String query2= "update course set term_id = -1 where term_id = " + DataProvider.getAllTerms().get(Term.getSelectedItemIndex()).getId();
       //myHelper.deleteRecord(query2);

        //Intent intent = new Intent(this, Terms.class);
        //startActivity(intent);
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
