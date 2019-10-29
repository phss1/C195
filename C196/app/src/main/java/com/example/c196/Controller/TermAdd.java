package com.example.c196.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;

public class TermAdd extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add);
    }

    public void onClickSaveBtn(View view)
    {
        String title = ((EditText) findViewById(R.id.termTitleTxtFld)).getText().toString();
        String startDate = ((EditText) findViewById(R.id.termStartDateTxtFld)).getText().toString();
        String endDate = ((EditText) findViewById(R.id.termEndDateTxtFld)).getText().toString();
        Boolean valuesNotNull = !title.isEmpty() && !startDate.isEmpty() && !endDate.isEmpty();

        try
        {
            if(valuesNotNull)
            {
                String slqQuery = "insert into term(course_id, title, description, ) values(-1, " + "\"" + title + "\""
                        + ", \"" + startDate + "\", \"" + endDate + "\");";

                myHelper.insertRecord(slqQuery);

                Intent intent = new Intent(this, TermView.class);
                startActivity(intent);
            }
        }
        catch(Exception e)
        {
            //
        }

    }

    public void onClickCancelBtn(View view)
    {
        Intent intent = new Intent(this, TermView.class);
        startActivity(intent);
    }
}
