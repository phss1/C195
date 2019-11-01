package com.example.c196.Controller;

import android.content.Intent;
import android.database.Cursor;
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

        Mentor mentorToModify = dp.getAllMentors().get(Mentor.getSelectedItemIndex());
        EditText name = findViewById(R.id.nameTxtFld2);
        EditText email = findViewById(R.id.emailTxtFld2);
        EditText phone = findViewById(R.id.phoneTxtFld2);

        name.setText(mentorToModify.getName());
        email.setText(mentorToModify.getEmail());
        phone.setText(mentorToModify.getPhone());
    }

    public void onClickSaveBtn(View view)
    {
        EditText name = findViewById(R.id.nameTxtFld2);
        EditText email = findViewById(R.id.emailTxtFld2);
        EditText phone = findViewById(R.id.phoneTxtFld2);

        int mentorId = dp.getAllMentors().get(Mentor.getSelectedItemIndex()).getId();
        String query = "update mentor set name = \"" + name.getText().toString() + "\", email = \"" + email.getText().toString()
                + "\", phone = \"" + phone.getText().toString() + "\" where mentor_id = " + mentorId + ";";

        myHelper.getWritableDatabase();
        myHelper.updateRecord(query);

        Intent intent = new Intent(this, MentorView.class);
        startActivity(intent);
    }

    public void onClickDeleteBtn(View view)
    {
        int mentorId = dp.getAllMentors().get(Mentor.getSelectedItemIndex()).getId();
        String query = "delete from mentor where mentor_id = " + mentorId + ";";
        myHelper.deleteRecord(query);

        //Mentor.deleteMentor();
        Intent intent = new Intent(this, MentorView.class);
        startActivity(intent);
    }

    public void onClickCancelBtn(View view)
    {
        Intent intent = new Intent(this, MentorView.class);
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
        myHelper = new DBConnector(MentorModify.this);
        myHelper.getWritableDatabase();
    }
}
