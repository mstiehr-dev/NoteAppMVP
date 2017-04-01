package com.mstiehr_dev.noteappmvp.presenter;

import com.mstiehr_dev.noteappmvp.MainMVP;
import com.mstiehr_dev.noteappmvp.model.Note;
import com.mstiehr_dev.noteappmvp.model.NoteRepository;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainPresenter implements MainMVP.PresenterViewOps, MainMVP.PresenterModelOps
{
    private WeakReference<MainMVP.ViewOps> mView;
    private MainMVP.NoteRepositoryOps mNoteRepository;

    public MainPresenter(MainMVP.ViewOps mView)
    {
        this.mView = new WeakReference(mView);
        this.mNoteRepository = new NoteRepository(this);
    }

    @Override
    public void onNoteAdded()
    {
        mView.get().showAlert("note successfully added");
        mView.get().refreshData();
    }

    @Override
    public void onNoteRemoved()
    {
        mView.get().showAlert("note successfully removed");
    }

    @Override
    public void addNote(Note note)
    {
        mNoteRepository.addNote(note);
    }

    @Override
    public void removeNote(Note note)
    {
        mNoteRepository.removeNote(note);
    }

    @Override
    public List<Note> getNotes() {
        return mNoteRepository.getNotes();
    }
}
