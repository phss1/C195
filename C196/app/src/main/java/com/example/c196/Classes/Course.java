package com.example.c196.Classes;

import java.util.ArrayList;
import java.util.List;

public class Course
{
    private int id;
    private String title;
    private String status;
    private ArrayList<Assessment> assessments;
    private ArrayList<Note> notes;
    private String startDate;
    private String endDate;
    private List<Course> courseToModify = new ArrayList<>();
    private static int selectedItemIndex;
    private static boolean useAlternate;

    public Course(int id, String title, String status, ArrayList<Assessment> assessments,
                  ArrayList<Note> notes, String startDate, String endDate)
    {
        this.id = id;
        this.title = title;
        this.status = status;
        this.assessments = assessments;
        this.notes = notes;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String isStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public ArrayList<Assessment> getAssessments()
    {
        return assessments;
    }

    public void setAssessments(ArrayList<Assessment> assessments)
    {
        this.assessments = assessments;
    }

    public void addAssessment(Assessment assessment)
    {
        this.assessments.add(assessment);
    }

    public ArrayList<Note> getNotes()
    {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes)
    {
        this.notes = notes;
    }

    public void addNote(Note note)
    {
        this.notes.add(note);
    }

    public String getStartDate()
    {
        return startDate;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    public static int getSelectedItemIndex()
    {
        return selectedItemIndex;
    }

    public static void setSelectedItemIndex(int selectedItemIndex)
    {
        Course.selectedItemIndex = selectedItemIndex;
    }

    public List<Course> getCourseToModify()
    {
        return courseToModify;
    }

    public void setCourseToModify(List<Course> courseToModify)
    {
        this.courseToModify = courseToModify;
    }

    public static boolean isUseAlternate()
    {
        return useAlternate;
    }

    public static void setUseAlternate(boolean useAlternate)
    {
        Course.useAlternate = useAlternate;
    }
}
