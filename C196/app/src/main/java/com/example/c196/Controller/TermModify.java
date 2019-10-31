package com.example.c196.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.c196.Classes.Assessment;
import com.example.c196.Classes.Course;
import com.example.c196.Classes.Mentor;
import com.example.c196.Classes.Note;
import com.example.c196.Classes.Term;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;

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

        List<String> termCourses = populateListView();
        Boolean isTermCoursesEmpty = termCourses.isEmpty();
        if(!isTermCoursesEmpty)
        {
            ArrayAdapter<String> termCoursesAdapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_list_item_1, termCourses
            );

            ListView listView = findViewById(R.id.mentorsLstVw);
            listView.setAdapter(termCoursesAdapter);
            listView.setOnItemClickListener
            (
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                    {
                        Term.setSelectedItemIndex(i);
                        onClickModifyTermCourses(view);
                    }
                }
            );
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

    public void onClickSaveBtn(View view)
    {
        Intent intent = new Intent(this, TermView.class);
        startActivity(intent);
    }

    public void onClickModifyTermCourses(View view)
    {
        Intent intent = new Intent(this, MentorModify.class);
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
