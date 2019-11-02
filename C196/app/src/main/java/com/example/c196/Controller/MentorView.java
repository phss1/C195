package com.example.c196.Controller;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c196.Classes.Mentor;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class MentorView extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentors_view);
        getSupportActionBar().setTitle("View Mentors");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myHelper = new DBConnector(MentorView.this);
        myHelper.getWritableDatabase();

        List<String> mentors = populateListView();
        Boolean isMentorsEmpty = mentors.isEmpty();
        if(!isMentorsEmpty)
        {
            ArrayAdapter<String> mentorsAdapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_list_item_1, mentors
            );

            ListView listView = findViewById(R.id.mentorsLstVw);
            listView.setAdapter(mentorsAdapter);
            listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                    {

                        Mentor.setSelectedItemIndex(i);
                        onClickModifyMentor(view);
                    }
                }
            );
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent intent = new Intent(this, Terms.class);
            startActivity(intent);
        }

        return true;
    }

    public void onActionAddMentor(View view)
    {
        Intent intent = new Intent(this, MentorAdd.class);
        startActivity(intent);
    }

    public void onClickHomeBtn(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickModifyMentor(View view)
    {
        Intent intent = new Intent(this, MentorModify.class);
        startActivity(intent);
    }

    private List<String> populateListView()
    {
        List<String> mentorList = new ArrayList<>();
        String query = "SELECT * from mentor";
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        dp.getAllMentors().clear();
        while (cursor.moveToNext())
        {
            Mentor tempMentor = new Mentor(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3));
            dp.addMentor(tempMentor);
            mentorList.add(cursor.getString(1));
        }

        return mentorList;
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
        myHelper = new DBConnector(MentorView.this);
        myHelper.getWritableDatabase();
    }
}
