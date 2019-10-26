package com.example.c196.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.UtilityMethods;

public class MentorModify extends AppCompatActivity
{
    DBConnector myHelper;
    UtilityMethods utilities;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_modify);

        myHelper = new DBConnector(MentorModify.this);
        myHelper.getWritableDatabase();
    }

    public void onClickSaveBtn(View view)
    {

    }

    public void onClickDeleteBtn(View view)
    {

    }

    public void onClickCancelBtn(View view)
    {
        Intent intent = new Intent(this, MentorsView.class);
        startActivity(intent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        myHelper.close();
        utilities.displayGuiMessage(MentorModify.this, myHelper.getDatabaseName() + " closed!");
    }
}
