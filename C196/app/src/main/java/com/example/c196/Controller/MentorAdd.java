package com.example.c196.Controller;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.example.c196.R;
import com.example.c196.Utility.DBDataProvider;
import com.example.c196.Utility.UtilityMethods;

public class MentorAdd extends AppCompatActivity
{
    UtilityMethods utilities = new UtilityMethods();
    DBDataProvider myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_add);
    }

    public void onClickSaveBtn(View view)
    {
        String name = ((EditText) findViewById(R.id.nameTxtFld)).getText().toString();
        String email = ((EditText) findViewById(R.id.emailTxtFld)).getText().toString();
        String phone = ((EditText) findViewById(R.id.phoneTxtFld)).getText().toString();
        Boolean valuesNotNull = !name.isEmpty() && !email.isEmpty() && !phone.isEmpty();

        //utilities.displayGuiMessage(MentorAdd.this, "valuesNotNull = " + valuesNotNull);

        String slqQuery = "insert into mentor(mentor_id, name, email, phone) values(1, " + "\'" + name + "\'"
                + ", \'" + email + "\', \'" + phone + "\');";

        myHelper.insertRecord(slqQuery);

        utilities.displayGuiMessage(MentorAdd.this, slqQuery);

        try
        {
            if(valuesNotNull.equals(true))
            {
                String slqQuery2 = "insert into mentor(name, email, phone) values(" + "\"" + name + "\""
                        + ", \"" + email + "\", \"" + phone + "\");";

                myHelper.insertRecord(slqQuery2);

                utilities.displayGuiMessage(MentorAdd.this, slqQuery);
            }
            else
            {

            }
        }
        catch(Exception e)
        {

        }


    }

    public void onClickCancelBtn(View view)
    {
        Intent intent = new Intent(this, MentorsView.class);
        startActivity(intent);
    }
}