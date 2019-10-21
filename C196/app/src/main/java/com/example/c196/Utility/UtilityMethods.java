package com.example.c196.Utility;

import android.content.Context;
import android.widget.Toast;

public class UtilityMethods
{
    public static void displayGuiMessage(Context context, String message)
    {
        Toast.makeText(context, message,
                Toast.LENGTH_SHORT).show();
    }
}