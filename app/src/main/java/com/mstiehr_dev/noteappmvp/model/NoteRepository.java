package com.mstiehr_dev.noteappmvp.model;

import com.mstiehr_dev.noteappmvp.App;
import com.mstiehr_dev.noteappmvp.MainMVP;

import java.util.ArrayList;
import java.util.List;

public class NoteRepository implements MainMVP.NoteRepositoryOps
{
    private MainMVP.PresenterModelOps mainPresenter;

    private DaoSession daoSession;

    public NoteRepository(MainMVP.PresenterModelOps mainPresenter)
    {
        this.mainPresenter = mainPresenter;
        daoSession = App.getDaoSession();
    }

    @Override
    public void addNote(Note note)
    {
        // do take care of note
//        notes.add(note);

        daoSession.getNoteDao().insert(note);

        mainPresenter.onNoteAdded();
    }

    @Override
    public void removeNote(Note note)
    {
        // do take care of note
//        notes.remove(note);

        daoSession.getNoteDao().delete(note);

        mainPresenter.onNoteRemoved();
    }

    @Override
    public List<Note> getNotes() {
        List<Note> allNotes = daoSession.getNoteDao().loadAll();

        return allNotes;
    }
}
