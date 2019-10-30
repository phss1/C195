package com.example.c196.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;

public class TermModify extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_modify);

        myHelper = new DBConnector(TermModify.this);
        myHelper.getWritableDatabase();
    }

    public void onClickModCoursesBtn(View view)
    {
        Intent intent = new Intent(this, TermView.class);
        startActivity(intent);
    }

    public void onClickCancelBtn(View view)
    {
        Intent intent = new Intent(this, TermView.class);
        startActivity(intent);
    }

    public void onClickSaveBtn(View view)
    {
        Intent intent = new Intent(this, TermView.class);
        startActivity(intent);
    }
}
