package com.example.c196.Utility;

import com.example.c196.Classes.Assessment;
import com.example.c196.Classes.Course;
import com.example.c196.Classes.Goal;
import com.example.c196.Classes.Mentor;
import com.example.c196.Classes.Note;
import com.example.c196.Classes.Term;

import java.util.ArrayList;

public class DataProvider
{
    private static ArrayList<Mentor> allMentors = new ArrayList<>();
    private static ArrayList<Assessment> allAssessments = new ArrayList<>();
    private static ArrayList<Course> allCourses = new ArrayList<>();
    private static ArrayList<Term> allTerms = new ArrayList<>();
    private static ArrayList<Goal> allGoals = new ArrayList<>();
    private static ArrayList<Note> allNotes = new ArrayList<>();

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

    public static ArrayList<Assessment> getAllAssessments()
    {
        return allAssessments;
    }

    public void setAllAssessments(ArrayList<Assessment> allAssessments)
    {
        this.allAssessments = allAssessments;
    }

    public static void addAssessment(Assessment newAssessment)
    {
        DataProvider.allAssessments.add(newAssessment);
    }

    public static ArrayList<Course> getAllCourses()
    {
        return allCourses;
    }

    public void setAllCourses(ArrayList<Course> allCourses)
    {
        this.allCourses = allCourses;
    }

    public void addCourse(Course course)
    {
        DataProvider.allCourses.add(course);
    }

    public static void addTerm(Term newTerm)
    {
        DataProvider.allTerms.add(newTerm);
    }

    public static ArrayList<Term> getAllTerms()
    {
        return allTerms;
    }

    public void setAllTerms(ArrayList<Term> allTerms)
    {
        this.allTerms = allTerms;
    }

    public static ArrayList<Goal> getAllGoals() {
        return allGoals;
    }

    public static void setAllGoals(ArrayList<Goal> allGoals) {
        DataProvider.allGoals = allGoals;
    }

    public static ArrayList<Note> getAllNotes() {
        return allNotes;
    }

    public static void setAllNotes(ArrayList<Note> allNotes) {
        DataProvider.allNotes = allNotes;
    }

    public static void addNote(Note newNote)
    {
        DataProvider.allNotes.add(newNote);
    }

    public static void addNote(Goal newGoal)
    {
        DataProvider.allGoals.add(newGoal);
    }
}
