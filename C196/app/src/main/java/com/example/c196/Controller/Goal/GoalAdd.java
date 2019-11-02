package com.example.c196.Controller.Goal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.c196.Controller.Assessment.AssessmentView;
import com.example.c196.Controller.Term.Terms;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;

public class GoalAdd extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_add);
        getSupportActionBar().setTitle("Add Goal");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent intent = new Intent(this, Terms.class);
            startActivity(intent);
        }

        return true;
    }

    public void onClickCancelBtn(View view)
    {
        Intent intent = new Intent(this, AssessmentView.class);
        startActivity(intent);
    }

    public void onClickSaveBtn(View view)
    {
        String description = ((EditText) findViewById(R.id.descriptionTxtFld)).getText().toString();
        String date = ((EditText) findViewById(R.id.dateTxtFld)).getText().toString();
        Boolean valuesNotNull = !description.isEmpty() && !date.isEmpty();

        try
        {
            if (valuesNotNull.equals(true))
            {
                String slqQuery = "insert into goal(assessment_id, description, date) values(-1, " + "\"" + description + "\""
                        + ", \"" + date + "\";";

                myHelper.insertRecord(slqQuery);

                Intent intent = new Intent(this, AssessmentView.class);
                startActivity(intent);
            }
            else
            {
                //
            }
        }
        catch (Exception e)
        {

        }

        Intent intent = new Intent(this, AssessmentView.class);
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
        myHelper = new DBConnector(GoalAdd.this);
        myHelper.getWritableDatabase();
    }
}
