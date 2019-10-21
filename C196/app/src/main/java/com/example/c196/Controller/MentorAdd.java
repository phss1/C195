package com.example.c196.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196.*;
import com.example.c196.Utility.UtilityMethods;

public class MentorAdd extends AppCompatActivity
{
    UtilityMethods utilities = new UtilityMethods();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_add);
    }

    public void onClickSaveBtn(View view)
    {
        String name = ((View) findViewById(R.id.nameTxtFld)).toString();
        String email = ((View) findViewById(R.id.emailTxtFld)).toString();
        String phone = ((View) findViewById(R.id.phoneTxtFld)).toString();

        EditText test = findViewById(R.id.nameTxtFld);
        utilities.displayGuiMessage(MentorAdd.this, "name saved as: " + name);
        //Toast.makeText(MentorAdd.this, "name saved as: " + test.toString(), Toast.LENGTH_SHORT).show();
    }

    public void onClickCancelBtn(View view)
    {
        Intent intent = new Intent(this, MentorsView.class);
        startActivity(intent);
    }
}