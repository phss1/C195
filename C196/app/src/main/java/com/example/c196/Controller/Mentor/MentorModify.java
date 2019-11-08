package com.example.c196.Controller.Mentor;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c196.Classes.Assessment;
import com.example.c196.Classes.Mentor;
import com.example.c196.Controller.Term.Terms;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;
import com.example.c196.Utility.UtilityMethods;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MentorModify extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_modify);
        getSupportActionBar().setTitle("Modify Mentor");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myHelper = new DBConnector(MentorModify.this);
        myHelper.getWritableDatabase();

        Mentor mentorToModify = dp.getAllMentors().get(Mentor.getSelectedItemIndex());
        EditText name = findViewById(R.id.nameTxtFld2);
        EditText email = findViewById(R.id.emailTxtFld2);
        EditText phone = findViewById(R.id.phoneTxtFld2);

        name.setText(mentorToModify.getName());
        email.setText(mentorToModify.getEmail());
        phone.setText(mentorToModify.getPhone());

        Spinner spinner1 = findViewById(R.id.mtrModCourseSpn);
        List<String> statusArray = populateSpinnerCourses();
        ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, statusArray);
        spinner1.setAdapter(spinnerAdapter1);
    }

    private List<String> populateSpinnerCourses()
    {
        List<String> courseTitles = new ArrayList<>();
        String query = "SELECT title from course";
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        while (cursor.moveToNext())
        {
            courseTitles.add(cursor.getString(0));
        }

        return courseTitles;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent intent = new Intent(this, MentorView.class);
            startActivity(intent);
        }

        return true;
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
