package com.mstiehr_dev.noteappmvp.model;

import com.mstiehr_dev.noteappmvp.MainMVP;

import java.util.ArrayList;
import java.util.List;

public class NoteRepository implements MainMVP.NoteRepositoryOps
{
    private MainMVP.PresenterModelOps mainPresenter;

    private List<Note> notes;

    public NoteRepository(MainMVP.PresenterModelOps mainPresenter)
    {
        this.mainPresenter = mainPresenter;
        this.notes = new ArrayList<>();
    }

    @Override
    public void addNote(Note note)
    {
        // do take care of note
        notes.add(note);

        mainPresenter.onNoteAdded();
    }

    @Override
    public void removeNote(Note note)
    {
        // do take care of note
        notes.remove(note);

        mainPresenter.onNoteRemoved();
    }

    @Override
    public List<Note> getNotes() {
        return notes;
    }
}
