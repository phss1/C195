package com.example.c196.Classes;

import java.util.ArrayList;
import java.util.List;

public class Assessment
{
    private int id;
    private String title;
    private String type;
    private String startDate;
    private String endDate;
    private ArrayList<Goal> goals;

    private List<Assessment> assessmentToModify = new ArrayList<>();
    private static int selectedItemIndex;

    public Assessment(int id, String title, String type, String startDate, String endDate, ArrayList<Goal> goals)
    {
        this.id = id;
        this.title = title;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.goals = goals;
    }



    public List<Assessment> getAssessmentToModify()
    {
        return assessmentToModify;
    }

    public void setAssessmentToModify(List<Assessment> assessment)
    {
        this.assessmentToModify = assessment;
    }

    public void addAssessmentToModify(Assessment assessment)
    {
        this.assessmentToModify.add(assessment);
    }

    public static int getSelectedItemIndex()
    {
        return selectedItemIndex;
    }

    public static void setSelectedItemIndex(int selectedItemIndex)
    {
        Assessment.selectedItemIndex = selectedItemIndex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public ArrayList<Goal> getGoals()
    {
        return goals;
    }

    public void setGoals(ArrayList<Goal> goals)
    {
        this.goals = goals;
    }

    public void addGoal(Goal goal)
    {
        this.goals.add(goal);
    }
}
