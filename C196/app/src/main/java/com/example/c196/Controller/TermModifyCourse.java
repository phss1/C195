package com.example.c196.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.c196.Classes.Assessment;
import com.example.c196.Classes.Course;
import com.example.c196.Classes.Note;
import com.example.c196.Classes.Term;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;
import com.example.c196.Utility.UtilityMethods;

import java.util.ArrayList;
import java.util.List;

public class TermModifyCourse extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_modify_course);

        myHelper = new DBConnector(TermModifyCourse.this);
        myHelper.getWritableDatabase();

        List<String> termCourses = populateListView();
        Boolean isTermCoursesEmpty = termCourses.isEmpty();
        if(!isTermCoursesEmpty)
        {
            ArrayAdapter<String> termCoursesAdapter = new ArrayAdapter<>
            (
            this, android.R.layout.simple_list_item_multiple_choice, termCourses
            );

            ListView listView = findViewById(R.id.addCToTermLstVw);
            listView.setAdapter(termCoursesAdapter);
            listView.setChoiceMode(2);
        }
    }

    public void onClickAddCToTermSaveBtn(View view)
    {
        try
        {
            ListView listView = findViewById(R.id.addCToTermLstVw);
            SparseBooleanArray checked = listView.getCheckedItemPositions();
            ArrayList<String> selectedCoursesForTerm = new ArrayList<>();
            int termId = DataProvider.getAllTerms().get(Term.getSelectedItemIndex()).getId();

            for (int i = 0; i < listView.getAdapter().getCount(); i++)
            {
                if(checked.get(i))
                {
                    selectedCoursesForTerm.add(String.valueOf(listView.getItemAtPosition(i)));
                    String query = "update course set term_id = " + termId + " where title = \""
                            + listView.getItemAtPosition(i) + "\";";
                    //UtilityMethods.displayGuiMessage(TermModifyCourse.this, query);
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
        myHelper = new DBConnector(TermModifyCourse.this);
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