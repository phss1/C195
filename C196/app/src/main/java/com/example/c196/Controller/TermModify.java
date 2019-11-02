package com.example.c196.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

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

        myHelper = new DBConnector(TermModify.this);
        myHelper.getWritableDatabase();

        Term term = dp.getAllTerms().get(Term.getSelectedItemIndex());
        EditText title = findViewById(R.id.termModifyTitleTxtFld);
        EditText startDate = findViewById(R.id.termModifyStartDateTxtFld);
        EditText endDate = findViewById(R.id.termModifyEndDateTxtFld);

        title.setText(term.getTitle());
        startDate.setText(term.getStartDate());
        endDate.setText(term.getEndDate());

        List<String> termCourses = populateListView();
        Boolean isTermCoursesEmpty = termCourses.isEmpty();
        if(!isTermCoursesEmpty)
        {
            ArrayAdapter<String> termCoursesAdapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_list_item_single_choice, termCourses
            );

            ListView listView = findViewById(R.id.termModifyLstVw);
            listView.setAdapter(termCoursesAdapter);
            listView.setChoiceMode(2);
        }
    }

    public void onClickModCoursesBtn(View view)
    {
        Intent intent = new Intent(this, TermModifyCourse.class);
        startActivity(intent);
    }

    public void onClickCancelBtn(View view)
    {
        Intent intent = new Intent(this, TermView.class);
        startActivity(intent);
    }

    public void onCLickModTermDeleteBtn(View view)
    {
        try
        {
            ListView listView = findViewById(R.id.termModifyLstVw);
            SparseBooleanArray checked = listView.getCheckedItemPositions();
            for (int i = 0; i < listView.getAdapter().getCount(); i++)
            {
                if (checked.get(i))
                {
                    String query = "update course set term_id = -1 where title = \""
                            + listView.getItemAtPosition(i) + "\";";
                    myHelper.updateRecord(query);
                    onCreate(new Bundle());
                }
            }
        }
        catch(Exception e)
        {

        }
    }

    public void onClickSaveBtn(View view)
    {
        Intent intent = new Intent(this, TermView.class);
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
