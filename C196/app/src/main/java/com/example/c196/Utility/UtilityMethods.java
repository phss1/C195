package com.example.c196.Utility;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.c196.Controller.MentorsView;

public class UtilityMethods
{
    public static void displayGuiMessage(Context context, String message)
    {
        Toast.makeText(context, message,
                Toast.LENGTH_SHORT).show();
    }
}