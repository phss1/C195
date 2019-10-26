package com.example.c196.Classes;

import com.example.c196.Utility.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class Mentor
{
    DataProvider dp = new DataProvider();

    private int id;
    private String name;
    private String email;
    private String phone;
    private List<Mentor> mentorToModify = new ArrayList<>();
    private static int selectedItemIndex;

    public Mentor(int id, String name, String email, String phone)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public static void deleteMentor()
    {
        DataProvider.getAllMentors().remove(Mentor.getSelectedItemIndex());
    }

    public static void modifyMentor(Mentor modifiedMentor)
    {
        DataProvider.getAllMentors().set(Mentor.getSelectedItemIndex(), modifiedMentor);
    }

    public List<Mentor> getMentorToModify()
    {
        return mentorToModify;
    }

    public void setMentorToModify(List<Mentor> mentorToModify)
    {
        this.mentorToModify = mentorToModify;
    }

    public void addMentorToModify(Mentor mentor)
    {
        this.mentorToModify.add(mentor);
    }

    public static int getSelectedItemIndex()
    {
        return selectedItemIndex;
    }

    public static void setSelectedItemIndex(int selectedItemIndex)
    {
        Mentor.selectedItemIndex = selectedItemIndex;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }
}