package com.example.c196.Controller;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.UtilityMethods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MentorsView extends AppCompatActivity
{
    DBConnector myHelper;
    UtilityMethods utilities;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentors_view);

        myHelper = new DBConnector(MentorsView.this);
        myHelper.getWritableDatabase();

        List<String> mentors = populateListView();
        Collections.sort(mentors);
        ArrayAdapter<String> mentorsAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, mentors
        );

        ListView listView = findViewById(R.id.mentorsLstVw);
        listView.setAdapter(mentorsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                utilities.displayGuiMessage(MentorsView.this, "Index of selected item is: " + i);
            }
        });

    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        myHelper.close();
        utilities.displayGuiMessage(MentorsView.this, myHelper.getDatabaseName() + " closed!");
    }

    public void onActionAddMentor(View view)
    {
        Intent intent = new Intent(this, MentorAdd.class);
        startActivity(intent);
    }

    public void onClickDeleteMentor(View view)
    {

    }

    public void onClickModifyMentor(View view)
    {
        Intent intent = new Intent(this, MentorModify.class);
        startActivity(intent);
    }

    private List<String> populateListView()
    {
        //String [] test = {DBHelper.test_code};

        List<String> mentorList = new ArrayList<>();
        String query = "SELECT * from mentor";
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);
        while (cursor.moveToNext())
        {
            mentorList.add(cursor.getString(1));
        }

        return mentorList;
    }
}
