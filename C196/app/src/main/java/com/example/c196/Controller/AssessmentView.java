package com.example.c196.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.c196.Classes.Mentor;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class AssessmentView extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_view);

        myHelper = new DBConnector(AssessmentView.this);
        myHelper.getWritableDatabase();

        List<String> mentors = populateListView();
        ArrayAdapter<String> mentorsAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, mentors
        );

        ListView listView = findViewById(R.id.AssessmentLstVw);
        listView.setAdapter(mentorsAdapter);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                    {

                        Mentor.setSelectedItemIndex(i);
                        onClickModifyAssessment(view);
                    }
                }
        );
    }

    public void onActionAddAssessment(View view)
    {
        Intent intent = new Intent(this, AssessmentAdd.class);
        startActivity(intent);
    }

    public void onClickHomeBtn(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickModifyAssessment(View view)
    {
        Intent intent = new Intent(this, AssessmentModify.class);
        startActivity(intent);
    }

    private List<String> populateListView()
    {
        List<String> mentorList = new ArrayList<>();
        String query = "SELECT * from assessment";
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        dp.getAllMentors().clear();
        while (cursor.moveToNext())
        {
            //Mentor tempMentor = new Mentor(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            //dp.addMentor(tempMentor);
            mentorList.add(cursor.getString(1));
        }

        return mentorList;
    }
}
