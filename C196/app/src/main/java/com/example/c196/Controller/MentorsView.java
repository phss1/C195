package com.example.c196.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196.R;

public class MentorsView extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentors_view);
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
}
