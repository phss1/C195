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
        /*
        String name = ((EditText) findViewById(R.id.nameTxtFld)).getText().toString();
        String email = ((EditText) findViewById(R.id.emailTxtFld)).getText().toString();
        String phone = ((EditText) findViewById(R.id.phoneTxtFld)).getText().toString();
        Boolean valuesNotNull = !name.isEmpty() && !email.isEmpty() && !phone.isEmpty();

        try
        {
            if (valuesNotNull.equals(true))
            {
                String slqQuery = "insert into goal(description, start) values(" + "\"" + name + "\""
                        + ", \"" + email + "\", \"" + phone + "\");";

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
        startActivity(intent);*/
    }

    public void onClickAddGoalBtn(View view)
    {
        Intent intent = new Intent(this, AssessmentModify.class);
        startActivity(intent);
    }
}
