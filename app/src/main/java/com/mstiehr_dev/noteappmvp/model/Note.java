package com.mstiehr_dev.noteappmvp.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity(indexes = {
        @Index(value = "text, date DESC", unique = true)
})
public class Note
{
    @Id private Long id;
    @NotNull private String text;
    private Date date;

    public Note()
    {
    }

    @Generated(hash = 1501873839)
    public Note(Long id, @NotNull String text, Date date) {
        this.id = id;
        this.text = text;
        this.date = date;
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
