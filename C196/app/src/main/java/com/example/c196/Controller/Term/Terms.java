package com.example.c196.Controller.Term;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.c196.Classes.Course;
import com.example.c196.Classes.Term;
import com.example.c196.Controller.MainActivity;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class Terms extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        getSupportActionBar().setTitle("Terms");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myHelper = new DBConnector(Terms.this);
        myHelper.getWritableDatabase();

        List<String> terms = populateListView();
        Boolean isTermsEmpty = terms.isEmpty();
        if(!isTermsEmpty)
        {
            ArrayAdapter<String> termsAdapter = new ArrayAdapter<>
                (
                this, android.R.layout.simple_list_item_1, terms
                );

            ListView listView = findViewById(R.id.termsLstVw);
            listView.setAdapter(termsAdapter);
            listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                    {
                        Term.setSelectedItemIndex(i);
                        onClickModifyTerm(view);
                    }
                }
            );
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        return true;
    }

    public void onActionAddTerm(View view)
    {
        Intent intent = new Intent(this, TermAdd.class);
        startActivity(intent);
    }

    public void onClickHomeBtn(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickModifyTerm(View view)
    {
        Intent intent = new Intent(this, TermDetailedView.class);
        startActivity(intent);
    }

    private List<String> populateListView()
    {
        String query = "SELECT * from term";
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        dp.getAllTerms().clear();
        List<String> termList = new ArrayList<>();
        ArrayList<Course> courses = new ArrayList<>();

        while (cursor.moveToNext())
        {
            Term tempTerm = new Term(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), courses);
            dp.addTerm(tempTerm);
            termList.add(cursor.getString(1));
        }

        return termList;
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
        myHelper = new DBConnector(Terms.this);
        myHelper.getWritableDatabase();
    }
}
