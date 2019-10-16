package com.example.c196.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196.R;

public class ViewMentors extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mentors);
    }

    public void onActionAddMentor(View view)
    {
        Intent intent = new Intent(this, AddMentor.class);
        startActivity(intent);
    }

    public void onClickDeleteMentor(View view)
    {

    }

    public void onClickModifyMentor(View view)
    {
        Intent intent = new Intent(this, ModifyMentor.class);
        startActivity(intent);
    }
}
