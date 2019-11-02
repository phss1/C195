package com.example.c196.Controller;

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
import com.example.c196.Utility.UtilityMethods;

import java.util.ArrayList;
import java.util.List;

public class TermRemoveCourse extends AppCompatActivity
{
    DBConnector myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_remove_course);
        getSupportActionBar().setTitle("Remove Course from Term");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myHelper = new DBConnector(TermRemoveCourse.this);
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

    public void onClickDeteteCoFromTermBtn(View view)
    {
        try
        {
            ListView listView = findViewById(R.id.removalLstVw);
            if(UtilityMethods.isAnItemChecked(listView) > 0)
            {
                SparseBooleanArray checked = listView.getCheckedItemPositions();
                for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                    if (checked.get(i))
                    {
                        String query = "update course set term_id = -1 where title = \""
                                + listView.getItemAtPosition(i) + "\";";
                        UtilityMethods.displayGuiMessage(TermRemoveCourse.this, "term id is: " + listView.getItemAtPosition(i));
                        myHelper.updateRecord(query);
                    }
                }
            }
        }
        catch(Exception e)
        {
            //
        }

        Intent intent = new Intent(this, TermModify.class);
        startActivity(intent);
    }

    public void onClickCancelDeleteBtn(View view)
    {
        Intent intent = new Intent(this, TermModify.class);
        startActivity(intent);
    }

    private List<String> populateListView()
    {
        List<String> courseList = new ArrayList<>();
        int termId = DataProvider.getAllTerms().get(Term.getSelectedItemIndex()).getId();
        String query = "SELECT * from course where term_id = " + termId;
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        while (cursor.moveToNext())
        {
            courseList.add(cursor.getString(3));
        }

        return courseList;
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
        myHelper = new DBConnector(TermRemoveCourse.this);
        myHelper.getWritableDatabase();
    }
}
