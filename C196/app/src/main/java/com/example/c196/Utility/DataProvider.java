package com.example.c196.Utility;

import com.example.c196.Classes.Mentor;

import java.util.ArrayList;

public class DataProvider
{
    private static ArrayList<Mentor> allMentors = new ArrayList<>();

    public static ArrayList<Mentor> getAllMentors()
    {
        return allMentors;
    }

    public void setAllMentors(ArrayList<Mentor> allMentors)
    {
        this.allMentors = allMentors;
    }

    public static void addMentor(Mentor newMentor)
    {
        DataProvider.allMentors.add(newMentor);
    }
}
