package com.example.c196;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    com.example.c196.Utility.DBHelper myHelper;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myHelper = new com.example.c196.Utility.DBHelper(MainActivity.this);

        myHelper.getWritableDatabase();

        //Toast.makeText(MainActivity.this, myHelper.getDatabaseName(), Toast.LENGTH_SHORT).show();

        myHelper.createTable("customer");
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        /*
        myHelper.insertRecord("insert into customer(name, salary, hire_date) " +
                "values('Mike', 20000.00, '2019-10-05 22:00:00')");
        long result = myHelper.addRecord("name", "Max", "salary",
                20000.00, "hire_date", "2019-10-09 15:00:00");

        if(result == -1)
            Toast.makeText(MainActivity.this, "Insertion Error!",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivity.this, "Insertion worked!",
                    Toast.LENGTH_SHORT).show();


        myHelper.deleteRecord("delete from customer where ID = 2");

        String [] whereArgs = {"12"};
        int rows = myHelper.removeRecord("customer", "id = ?", whereArgs);
        Toast.makeText(MainActivity.this, "Rows deleted!" + rows,
                Toast.LENGTH_SHORT).show();
         */

        //myHelper.updateRecord("update customer set name = 'Teddy', salary = 50000.00 where ID = 3");
        String[] whereArgs = {"4"};
        int rows = myHelper.changeRecord("customer", 16000.00, "id = ?", whereArgs);
        Toast.makeText(MainActivity.this, "Rows deleted!" + rows,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPause()
    {
        super.onPause();

        myHelper.close();
        Toast.makeText(MainActivity.this, myHelper.getDatabaseName()
                + " closed!", Toast.LENGTH_SHORT).show();
    }
}
