package com.example.c196;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    DBHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myHelper = new DBHelper(MainActivity.this);

        myHelper.getWritableDatabase();

        Toast.makeText(MainActivity.this, myHelper.getDatabaseName(), Toast.LENGTH_SHORT).show();

        myHelper.createTable("customer");
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        myHelper.insertRecord("insert into customer(name, salary, hire_date) values('Mike', 20000.00, '2019-10-05 23:00:00.0')");
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        myHelper.close();
        Toast.makeText(MainActivity.this, myHelper.getDatabaseName() + " closed!", Toast.LENGTH_SHORT).show();
    }
}
