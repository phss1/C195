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
import com.example.c196.Classes.Goal;
import com.example.c196.Classes.Note;
import com.example.c196.Classes.Term;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TermView extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_view);

        myHelper = new DBConnector(TermView.this);
        myHelper.getWritableDatabase();

        List<String> terms = populateListView();
        ArrayAdapter<String> termsAdapter = new ArrayAdapter<>
            (
            this, android.R.layout.simple_list_item_1, terms
            );

        ListView listView = findViewById(R.id.AssessmentLstVw);
        listView.setAdapter(termsAdapter);
        listView.setOnItemClickListener(
            new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    Term.setSelectedItemIndex(i);
                    onClickModifyTerm(view);
                }
            }
        );
    }

    public void onActionAddTerm(View view)
    {
        Intent intent = new Intent(this, TermAdd.class);
        startActivity(intent);
    }

    public void onClickHomeBtn(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickModifyTerm(View view)
    {
        Intent intent = new Intent(this, AssessmentModify.class);
        startActivity(intent);
    }

    private List<String> populateListView()
    {
        String query = "SELECT * from term";
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);
        String query2 = "SELECT * from course";
        Cursor cursor2 = myHelper.getReadableDatabase().rawQuery(query2,null);

        dp.getAllTerms().clear();
        List<String> termList = new ArrayList<>();
        ArrayList<Assessment> a = new ArrayList<>();
        ArrayList<Note> n = new ArrayList<>();

        while (cursor.moveToNext())
        {
            ArrayList<Course> courses = new ArrayList<>();
            while(cursor2.moveToNext())
            {
                Course tempCourse = new Course(cursor2.getInt(0), cursor2.getString(1), cursor2.getString(2),
                        cursor2.getInt(3), a, n, cursor2.getString(4), cursor2.getString(5));
                courses.add(tempCourse);
            }
            Term tempTerm = new Term(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), courses);
            dp.addTerm(tempTerm);
            termList.add(cursor.getString(1));
        }

        return termList;
    }
}
