package com.example.c196.Controller.Course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.c196.Classes.Course;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;
import com.example.c196.Utility.UtilityMethods;

import java.util.ArrayList;
import java.util.List;

public class CourseDeleteNote extends AppCompatActivity
{
    DBConnector myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_delete_note);
        getSupportActionBar().setTitle("Delete a Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myHelper = new DBConnector(CourseDeleteNote.this);
        myHelper.getWritableDatabase();

        List<String> courseNotes = populateListView();
        Boolean isCoursesNoteEmpty = courseNotes.isEmpty();
        if(!isCoursesNoteEmpty)
        {
            ArrayAdapter<String> courseNotesAdapter = new ArrayAdapter<>
                    (
                            this, android.R.layout.simple_list_item_single_choice, courseNotes
                    );

            ListView listView = findViewById(R.id.viewCourseNotesLstVw);
            listView.setAdapter(courseNotesAdapter);
            listView.setChoiceMode(1);
        }
    }

    private List<String> populateListView()
    {
        List<String> notesList = new ArrayList<>();
        int courseId = DataProvider.getAllCourses().get(Course.getSelectedItemIndex()).getId();
        String query = "SELECT * from note where course_id = " + courseId;
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        while (cursor.moveToNext())
        {
            notesList.add(cursor.getString(2));
        }

        return notesList;
    }

    public void onCLickCourseDeleteNoteBtn(View view)
    {
        try
        {
            ListView listView = findViewById(R.id.viewCourseNotesLstVw);
            //if(UtilityMethods.isAnItemChecked(listView) > 0)
            //{
                SparseBooleanArray checked = listView.getCheckedItemPositions();
                for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                    if (checked.get(i))
                    {
                        String query = "delete from note where title = \""
                                + listView.getItemAtPosition(i) + "\";";

                        UtilityMethods.displayGuiMessage(CourseDeleteNote.this, "" + query);
                        myHelper.deleteRecord(query);

                        Intent intent = new Intent(this, CourseModify.class);
                        startActivity(intent);
                    }
                }
            //}
        }
        catch(Exception e)
        {
            //
        }
    }

    public void coAddNoteCancelBtn(View view)
    {
        Intent intent = new Intent(this, CourseModify.class);
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
        myHelper = new DBConnector(CourseDeleteNote.this);
        myHelper.getWritableDatabase();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent intent = new Intent(this, CourseModify.class);
            startActivity(intent);
        }

        return true;
    }
}
