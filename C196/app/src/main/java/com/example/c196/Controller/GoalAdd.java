package com.example.c196.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.c196.Controller.AssessmentView;
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
                String slqQuery = "insert into goal(description, date) values(" + "\"" + description + "\""
                        + ", \"" + date + "\";";

                myHelper.insertRecord(slqQuery);

                Intent intent = new Intent(this, MentorView.class);
                startActivity(intent);
            }
            else
            {
                //
            }
        } catch (Exception e)
        {

        }

        Intent intent = new Intent(this, AssessmentView.class);
        startActivity(intent);
    }
}
