package com.example.c196.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.c196.R;

public class MentorAdd extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_add);
    }

    public void onClickSaveBtn(View view)
    {
        //String text = (textEmailAddress) fineViewById();
    }

    public void onClickCancelBtn(View view)
    {
        Intent intent = new Intent(this, MentorsView.class);
        startActivity(intent);
    }
}
