package com.example.c196.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;

public class AssessmentModify extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_modify);
    }


}
