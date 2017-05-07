package com.mstiehr_dev.noteappmvp.model;

import com.mstiehr_dev.noteappmvp.App;
import com.mstiehr_dev.noteappmvp.MainMVP;

import java.util.List;


public class IndependentNodeRepository implements MainMVP.NoteRepositoryOps
{
    private DaoSession daoSession;

    public IndependentNodeRepository()
    {
        this.daoSession = App.getDaoSession();
    }

    @Override
    public void addNote(Note note) {

    }

    @Override
    public void removeNote(Note note) {

    }

    @Override
    public List<Note> getNotes() {
        return null;
    }
}
