package com.example.c196.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;

public class TermModifyCourse extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_modify_course);

        myHelper = new DBConnector(TermModifyCourse.this);
        myHelper.getWritableDatabase();


    }

    public void onClickAddCToTermCancelBtn(View view)
    {
        Intent intent = new Intent(this, TermModify.class);
        startActivity(intent);
    }

    public void onClickAddCToTermSaveBtn(View view)
    {
        Intent intent = new Intent(this, TermModify.class);
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
        myHelper = new DBConnector(TermModifyCourse.this);
        myHelper.getWritableDatabase();
    }
}