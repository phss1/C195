package com.example.c196.Utility;

import com.example.c196.Classes.Mentor;

import java.util.ArrayList;

public class DataProvider
{
    private ArrayList<Mentor> allMentors = new ArrayList<>();

    public ArrayList<Mentor> getAllMentors()
    {
        return allMentors;
    }

    public void setAllMentors(ArrayList<Mentor> allMentors)
    {
        this.allMentors = allMentors;
    }

    public void addMentor(Mentor newMentor)
    {
        this.allMentors = allMentors;
    }
}
