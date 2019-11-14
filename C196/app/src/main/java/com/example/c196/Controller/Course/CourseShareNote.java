package com.example.c196.Controller.Course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.c196.Classes.Course;
import com.example.c196.Classes.Note;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class CourseShareNote extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_share_note);
        myHelper = new DBConnector(CourseShareNote.this);
        myHelper.getWritableDatabase();

        getSupportActionBar().setTitle("Share Course Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<String> notes = getNotes();
        ArrayAdapter<String> notesAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_single_choice, notes
        );
        ListView listView = findViewById(R.id.notesLstVw);
        listView.setAdapter(notesAdapter);
        listView.setChoiceMode(1);
        listView.setSelection(0);

    }

    private List<String> getNotes()
    {
        List<String> notesList = new ArrayList<>();
        int courseId = DataProvider.getAllCourses().get(Course.getSelectedItemIndex()).getId();
        String query = "SELECT * from note where course_id = " + courseId;
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        dp.getAllNotes().clear();
        while (cursor.moveToNext())
        {
            Note note = new Note(cursor.getInt(0), cursor.getString(2), cursor.getString(3));
            dp.addNote(note);
            notesList.add(cursor.getString(2));
        }

        return notesList;
    }

    public void onClickShareEmailBtn(View view)
    {
        String email = ((EditText) findViewById(R.id.editText)).getText().toString();

        ListView listView = findViewById(R.id.notesLstVw);
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
            if (checked.get(i))
            {
                Note note = dp.getAllNotes().get(i);
                String subject = "Reminder for note: " + note.getTitle();
                String message = "You've received this email as a reminder of your note.`n`n"
                        + note.getDescription();

                Intent it = new Intent(Intent.ACTION_SEND);
                it.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                it.putExtra(Intent.EXTRA_SUBJECT,subject);
                it.putExtra(Intent.EXTRA_TEXT,message);
                it.setType("message/rfc822");
                startActivity(Intent.createChooser(it,"Choose Mail App"));
            }
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
        myHelper = new DBConnector(CourseShareNote.this);
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