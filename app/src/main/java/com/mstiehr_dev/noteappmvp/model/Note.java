package com.mstiehr_dev.noteappmvp.model;

import java.util.Date;

public class Note
{
    private String text;
    private Date date;

    public Note()
    {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
