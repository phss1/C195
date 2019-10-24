package com.example.c196.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.UtilityMethods;

public class MentorsView extends AppCompatActivity
{
    DBConnector myHelper;
    UtilityMethods utilities;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentors_view);

        myHelper = new DBConnector(MentorsView.this);
        myHelper.getWritableDatabase();
    }

    public void onActionAddMentor(View view)
    {
        Intent intent = new Intent(this, MentorAdd.class);
        startActivity(intent);
    }

    public void onClickDeleteMentor(View view)
    {

    }

    public void onClickModifyMentor(View view)
    {
        Intent intent = new Intent(this, MentorModify.class);
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
        utilities.displayGuiMessage(MentorsView.this, myHelper.getDatabaseName() + " closed!");
    }
}
