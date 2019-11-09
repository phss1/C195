package com.example.c196.Utility;

import android.content.Context;
import android.database.Cursor;
import android.util.SparseBooleanArray;
import android.widget.ListView;
import android.widget.Toast;

import com.example.c196.Classes.Course;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class UtilityMethods
{

    public static ArrayList<String> createCourseSpinnerValues()
    {
        ArrayList<Course> courses = DataProvider.getAllCourses();
        ArrayList<String> courseArray = new ArrayList<>();
        for(Course course : courses)
        {
            courseArray.add(course.getTitle());
        }

        return courseArray;
    }

    public static void displayGuiMessage(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isValidDate(String date)
    {
        return Pattern.compile("^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d$").matcher(date).matches();
    }

    public static int isAnItemChecked(ListView listView)
    {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        int itemIsChecked =-1;
        for (int i = 0; i < listView.getAdapter().getCount(); i++)
        {
            itemIsChecked = checked.get(i) ? 0 : 1;
        }

        return itemIsChecked;
    }
}