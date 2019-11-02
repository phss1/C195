package com.example.c196.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;
import com.example.c196.Utility.UtilityMethods;

public class TermAdd extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add);
        getSupportActionBar().setTitle("Add Term");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myHelper = new DBConnector(TermAdd.this);
        myHelper.getWritableDatabase();
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

    public void onClickSaveBtn(View view)
    {
        String title = ((EditText) findViewById(R.id.termTitleTxtFld)).getText().toString();
        String startDate = ((EditText) findViewById(R.id.termStartDateTxtFld)).getText().toString();
        String endDate = ((EditText) findViewById(R.id.termEndDateTxtFld)).getText().toString();
        Boolean valuesNotNull = !title.isEmpty() && !startDate.isEmpty() && !endDate.isEmpty()
                && UtilityMethods.isValidDate(startDate) == true
                && UtilityMethods.isValidDate(endDate) == true;

        try
        {
            if(valuesNotNull)
            {
                String sqlQuery = "insert into term(title, start_date, end_date) values(\"" + title
                        + "\", \"" + startDate + "\", \"" + endDate + "\");";
                myHelper.insertRecord(sqlQuery);

                Intent intent = new Intent(this, Terms.class);
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
        Intent intent = new Intent(this, Terms.class);
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
        myHelper = new DBConnector(TermAdd.this);
        myHelper.getWritableDatabase();
    }
}
