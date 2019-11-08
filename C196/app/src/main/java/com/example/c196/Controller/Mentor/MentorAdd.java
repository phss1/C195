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
import com.example.c196.Classes.Mentor;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;
import com.example.c196.Utility.UtilityMethods;

import java.util.ArrayList;
import java.util.List;

public class MentorAdd extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_add);
        getSupportActionBar().setTitle("Add Mentor");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myHelper = new DBConnector(MentorAdd.this);
        myHelper.getWritableDatabase();

        /*Spinner spinner2 = findViewById(R.id.addMtrCourseSpn);
        ArrayList<String> courseArray = populateCourseList();

        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, courseArray);
        spinner2.setAdapter(spinnerAdapter2);*/
    }

    private ArrayList<String> populateCourseList()
    {
        ArrayList<String> courseList = new ArrayList<>();
        String query = "SELECT title from course";
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        while (cursor.moveToNext())
        {
            courseList.add(cursor.getString(0));
        }

        return courseList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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
        String name = ((EditText) findViewById(R.id.nameTxtFld)).getText().toString();
        String email = ((EditText) findViewById(R.id.emailTxtFld)).getText().toString();
        String phone = ((EditText) findViewById(R.id.phoneTxtFld)).getText().toString();
        //String selectedCourseTitle = ((Spinner) findViewById(R.id.addMtrCourseSpn)).getSelectedItem().toString();
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

                List<Integer> mentorIds = populateListView();
                /*int listSize = mentorIds.size() - 1;
                int newMentorId = mentorIds.get(listSize);

                String query = "update course set mentor_id = "
                        + newMentorId
                        + " where title = \"" + selectedCourseTitle + "\"";
                UtilityMethods.displayGuiMessage(MentorAdd.this, "" + query);
                myHelper.updateRecord(query);

                */
            }
            else
            {
                //
            }
        } catch (Exception e)
        {

        }
    }

    private List<Integer> populateListView()
    {
        List<Integer> mentorList = new ArrayList<>();
        String query = "SELECT * from mentor";
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        while (cursor.moveToNext())
        {
            mentorList.add(cursor.getInt(0));
        }

        return mentorList;
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
        myHelper = new DBConnector(MentorAdd.this);
        myHelper.getWritableDatabase();
    }
}