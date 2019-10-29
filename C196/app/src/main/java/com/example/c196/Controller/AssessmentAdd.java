package com.example.c196.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;

public class AssessmentAdd extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_add);
    }

    public void onClickCancelBtn(View view)
    {
        Intent intent = new Intent(this, AssessmentView.class);
        startActivity(intent);
    }

    public void onClickSaveBtn(View view)
    {
        String title = ((EditText) findViewById(R.id.titleTxtFld)).getText().toString();
        String description = ((EditText) findViewById(R.id.descriptionTxtFld)).getText().toString();
        String startDate = ((EditText) findViewById(R.id.startDateTxtFld)).getText().toString();
        String endDate = ((EditText) findViewById(R.id.endDateTxtFld)).getText().toString();
        Boolean valuesNotNull = !title.isEmpty() && !description.isEmpty() && !startDate.isEmpty() && !endDate.isEmpty();

        try
        {
            if (valuesNotNull.equals(true))
            {
                String slqQuery = "insert into assessment(course_id, title, description, ) values(" + "\"" + title + "\""
                        + ", \"" + description + "\", \"" + startDate + "\");";

                myHelper.insertRecord(slqQuery);

                Intent intent = new Intent(this, AssessmentView.class);
                startActivity(intent);
            }
            else
            {
                //
            }
        } catch (Exception e)
        {
            //
        }
    }

    public void onClickAddGoalBtn(View view)
    {
        Intent intent = new Intent(this, GoalAdd.class);
        startActivity(intent);
    }
}
