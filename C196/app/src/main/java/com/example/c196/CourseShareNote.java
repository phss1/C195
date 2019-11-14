package com.example.c196;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.c196.Classes.Assessment;
import com.example.c196.Classes.Goal;

import java.util.ArrayList;
import java.util.List;

public class CourseShareNote extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_share_note);

        List<String> notes = populateListView();
        ArrayAdapter<String> notesAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, notes
        );

        ListView listView = findViewById(R.id.notesLstVw);
        listView.setAdapter(notesAdapter);
    }

    private List<String> populateListView()
    {
        String query = "SELECT * from assessment";
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        dp.getAllAssessments().clear();
        List<String> assessmentList = new ArrayList<>();
        ArrayList<Goal> g = new ArrayList<>();

        while (cursor.moveToNext())
        {
            Assessment tempAssessment = new Assessment(cursor.getInt(0), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5), g);
            dp.addAssessment(tempAssessment);
            assessmentList.add(cursor.getString(2));
        }

        return assessmentList;
    }

    public void onClickShareEmailBtn(View view)
    {
        String email = ((EditText) findViewById(R.id.editText)).getText().toString();
        String subject = "Reminder for note: ";
        String message = "You've received this email as a reminder of your note.";

        Intent it = new Intent(Intent.ACTION_SEND);
        it.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        it.putExtra(Intent.EXTRA_SUBJECT,subject);
        it.putExtra(Intent.EXTRA_TEXT,message);
        it.setType("message/rfc822");
        startActivity(Intent.createChooser(it,"Choose Mail App"));

    }
}