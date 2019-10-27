package com.example.c196.Classes;

import java.util.ArrayList;
import java.util.Date;

public class Course
{
    private int id;
    private String title;
    private String description;
    private int status;
    private ArrayList<Assessment> assessments;
    private ArrayList<Note> notes;
    private Date startDate;
    private Date endDate;

    public Course(int id, String title, String description, int status, ArrayList<Assessment> assessments,
                  ArrayList<Note> notes, Date startDate, Date endDate)
    {
        this.id = id;
        this.title = title;
        this.description = description;
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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int isStatus()
    {
        return status;
    }

    public void setStatus(int status)
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

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }
}
