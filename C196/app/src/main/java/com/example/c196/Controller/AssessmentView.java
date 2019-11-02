package com.example.c196.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.c196.Classes.Assessment;
import com.example.c196.Classes.Goal;
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

        getSupportActionBar().setTitle("View Assessments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

                        Assessment.setSelectedItemIndex(i);
                        onClickModifyAssessment(view);
                    }
                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent intent = new Intent(this, TermView.class);
            startActivity(intent);
        }

        return true;
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
        String query = "SELECT * from assessment";
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);
        String query2 = "SELECT * from goal";
        Cursor cursor2 = myHelper.getReadableDatabase().rawQuery(query2,null);

        dp.getAllMentors().clear();
        List<String> assessmentList = new ArrayList<>();

        while (cursor.moveToNext())
        {
            ArrayList<Goal> goals = new ArrayList<>();
            while(cursor2.moveToNext())
            {
                Goal tempGoal = new Goal(cursor2.getString(0), cursor2.getString(1));
                goals.add(tempGoal);
            }
            Assessment tempAssessment = new Assessment(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), goals);
            dp.addAssessment(tempAssessment);
            assessmentList.add(cursor.getString(1));
        }

        return assessmentList;
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
        myHelper = new DBConnector(AssessmentView.this);
        myHelper.getWritableDatabase();
    }
}
