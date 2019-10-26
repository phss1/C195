package com.example.c196.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.UtilityMethods;

public class MentorAdd extends AppCompatActivity
{
    UtilityMethods utilities = new UtilityMethods();
    DBConnector myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_add);

        myHelper = new DBConnector(MentorAdd.this);

        myHelper.getWritableDatabase();
    }

    public void onClickSaveBtn(View view)
    {
        String name = ((EditText) findViewById(R.id.nameTxtFld)).getText().toString();
        String email = ((EditText) findViewById(R.id.emailTxtFld)).getText().toString();
        String phone = ((EditText) findViewById(R.id.phoneTxtFld)).getText().toString();
        Boolean valuesNotNull = !name.isEmpty() && !email.isEmpty() && !phone.isEmpty();

        try
        {
            if (valuesNotNull.equals(true))
            {
                String slqQuery = "insert into mentor(name, email, phone) values(" + "\"" + name + "\""
                        + ", \"" + email + "\", \"" + phone + "\");";

                myHelper.insertRecord(slqQuery);

                Intent intent = new Intent(this, MentorView.class);
                startActivity(intent);
            }
            else
            {
                //
            }
        } catch (Exception e)
        {

        }


    }

    public void onClickCancelBtn(View view)
    {
        Intent intent = new Intent(this, MentorView.class);
        startActivity(intent);
    }
}