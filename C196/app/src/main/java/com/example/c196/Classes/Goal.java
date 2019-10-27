package com.example.c196.Classes;

import java.util.Date;

public class Goal
{
    private String description;
    private Date date;

    public Goal(String description, Date date)
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

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
}
