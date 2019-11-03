package com.example.c196.Classes;

public class Note
{
    private int noteId;
    private String title;
    private String description;

    public Note(int noteId, String title, String description)
    {
        this.noteId = noteId;
        this.title = title;
        this.description = description;
    }

    public int getNoteId()
    {
        return noteId;
    }

    public void setNoteId(int noteId)
    {
        this.noteId = noteId;
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
}
