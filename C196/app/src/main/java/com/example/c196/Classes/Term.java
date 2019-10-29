package com.example.c196.Classes;

import java.util.ArrayList;
import java.util.List;

public class Term
{
    private int termId;
    private String title;
    private String startDate;
    private String endDate;
    private ArrayList<Course> courses;

    private List<Term> termToModify = new ArrayList<>();
    private static int selectedItemIndex;

    public Term(int termId, String title, String startDate, String endDate, ArrayList<Course> courses)
    {
        this.termId = termId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courses = courses;
    }

    public int getId()
    {
        return termId;
    }

    public void setId(int termId)
    {
        this.termId = termId;
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

    public List<Term> getTermToModify()
    {
        return termToModify;
    }

    public void setTermToModify(List<Term> term)
    {
        this.termToModify = term;
    }

    public void addAssessmentToModify(Term term)
    {
        this.termToModify.add(term);
    }

    public static int getSelectedItemIndex()
    {
        return selectedItemIndex;
    }

    public static void setSelectedItemIndex(int selectedItemIndex)
    {
        Term.selectedItemIndex = selectedItemIndex;
    }
}
