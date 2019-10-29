package com.example.c196.Classes;

import java.util.Date;

public class Goal
{
    private String description;
    private String date;

    public Goal(String description, String date)
    {
        this.description = description;
        this.date = date;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }
}
