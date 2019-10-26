package com.example.c196.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c196.Classes.Mentor;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;
import com.example.c196.Utility.UtilityMethods;

public class MentorModify extends AppCompatActivity
{
    DBConnector myHelper;
    UtilityMethods utilities;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_modify);

        myHelper = new DBConnector(MentorModify.this);
        myHelper.getWritableDatabase();

        //Mentor mentorToModify = dp.getAllMentors().get(Mentor.getSelectedItemIndex());
        //utilities.displayGuiMessage(MentorModify.this, "modify mentor index is: " + Mentor.getSelectedItemIndex());
        //Mentor mentor = dp.getAllMentors().get(0);
        utilities.displayGuiMessage(MentorModify.this, "number of mentors empty: " + dp.getAllMentors().isEmpty());
        EditText name = (EditText) findViewById(R.id.nameTxtFld2);
        EditText email = (EditText) findViewById(R.id.emailTxtFld2);
        EditText phone = (EditText) findViewById(R.id.phoneTxtFld2);

        name.setText("testint 123");
    }

    public void onClickSaveBtn(View view)
    {

    }

    public void onClickDeleteBtn(View view)
    {

    }

    public void onClickCancelBtn(View view)
    {
        Intent intent = new Intent(this, MentorView.class);
        startActivity(intent);
    }
}
