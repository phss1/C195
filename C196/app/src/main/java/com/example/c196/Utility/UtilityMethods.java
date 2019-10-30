package com.example.c196.Utility;

import android.content.Context;
import android.widget.Toast;

import java.util.regex.Pattern;

public class UtilityMethods
{
    public static void displayGuiMessage(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isValidDate(String date)
    {
        return Pattern.compile("^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d$").matcher(date).matches();
    }
}