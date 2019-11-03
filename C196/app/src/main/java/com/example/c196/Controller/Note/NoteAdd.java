package com.example.c196.Controller.Note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.c196.Classes.Course;
import com.example.c196.Controller.Course.CourseDetailedView;
import com.example.c196.Controller.Course.CourseModify;
import com.example.c196.Controller.Term.TermDetailedView;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;
import com.example.c196.Utility.UtilityMethods;

public class NoteAdd extends AppCompatActivity
{
    DBConnector myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);
        getSupportActionBar().setTitle("Add a Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myHelper = new DBConnector(NoteAdd.this);
        myHelper.getWritableDatabase();
    }

    public void addNoteSaveBtn(View view)
    {
        String title = ((EditText) findViewById(R.id.addNoteTitle)).getText().toString();
        String description = ((EditText) findViewById(R.id.addNoteDesc)).getText().toString();
        Boolean valuesNotNull = !title.isEmpty() && !description.isEmpty();

        try
        {
            if(valuesNotNull)
            {
                String sqlQuery = "insert into course(course_id, title, description, title, status,  " +
                        "start_date, end_date) values(-1, \"" + title
                        + "\", \"" + description  + "\");";
                myHelper.insertRecord(sqlQuery);

                Intent intent = new Intent(this, CourseModify.class);
                startActivity(intent);
            }
            else
            {
                UtilityMethods.displayGuiMessage(NoteAdd.this, "Please make sure values are not null.");
            }
        }
        catch(Exception e)
        {
            //
        }


        Intent intent = new Intent(this, CourseModify.class);
        startActivity(intent);
    }

    public void addNoteCancelBtn(View view)
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
        myHelper = new DBConnector(NoteAdd.this);
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
