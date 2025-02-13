package com.example.c196.Controller.Term;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.c196.Classes.Term;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class TermAddCourses extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add_courses);
        getSupportActionBar().setTitle("Add Courses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myHelper = new DBConnector(TermAddCourses.this);
        myHelper.getWritableDatabase();

        List<String> termCourses = populateListView();
        Boolean isTermCoursesEmpty = termCourses.isEmpty();
        if(!isTermCoursesEmpty)
        {
            ArrayAdapter<String> termCoursesAdapter = new ArrayAdapter<>
            (
            this, android.R.layout.simple_list_item_multiple_choice, termCourses
            );

            ListView listView = findViewById(R.id.removalLstVw);
            listView.setAdapter(termCoursesAdapter);
            listView.setChoiceMode(2);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent intent = new Intent(this, TermModify.class);
            startActivity(intent);
        }

        return true;
    }

    public void onClickAddCToTermSaveBtn(View view)
    {
        try
        {
            ListView listView = findViewById(R.id.removalLstVw);
            SparseBooleanArray checked = listView.getCheckedItemPositions();
            int termId = DataProvider.getAllTerms().get(Term.getSelectedItemIndex()).getId();

            for (int i = 0; i < listView.getAdapter().getCount(); i++)
            {
                if(checked.get(i))
                {
                    String query = "update course set term_id = " + termId + " where title = \""
                            + listView.getItemAtPosition(i) + "\";";
                    //UtilityMethods.displayGuiMessage(TermAddCourses.this, query);
                    myHelper.updateRecord(query);
                }
            }
        }
        catch(Exception e)
        {

        }

        Intent intent = new Intent(this, TermModify.class);
        startActivity(intent);
    }

    public void termAddNewCourseBtn(View view)
    {
        Intent intent = new Intent(this, TermCreateCourse.class);
        startActivity(intent);
    }

    public void onClickAddCToTermCancelBtn(View view)
    {
        Intent intent = new Intent(this, TermModify.class);
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
        myHelper = new DBConnector(TermAddCourses.this);
        myHelper.getWritableDatabase();
    }

    private List<String> populateListView()
    {
        List<String> courseList = new ArrayList<>();
        String query = "SELECT * from course where term_id = -1";
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        while (cursor.moveToNext())
        {
            courseList.add(cursor.getString(3));
        }

        return courseList;
    }
}