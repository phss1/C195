package com.example.c196.Controller;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c196.Classes.Mentor;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;
import com.example.c196.Utility.UtilityMethods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MentorView extends AppCompatActivity
{
    DBConnector myHelper;
    UtilityMethods utilities;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentors_view);

        myHelper = new DBConnector(MentorView.this);
        myHelper.getWritableDatabase();

        List<String> mentors = populateListView();
        ArrayAdapter<String> mentorsAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, mentors
        );

        ListView listView = findViewById(R.id.mentorsLstVw);
        listView.setAdapter(mentorsAdapter);
        ListView tempListView = listView;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Mentor.selectedItemIndex = i;
                onClickModifyMentor(view);
            }
        });
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

        while (cursor.moveToNext())
        {
            Mentor tempMentor = new Mentor(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3));
            dp.addMentor(tempMentor);
            mentorList.add(cursor.getString(1));
        }

        return mentorList;
    }
}
