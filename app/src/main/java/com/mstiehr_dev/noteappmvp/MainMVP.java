package com.mstiehr_dev.noteappmvp;

import com.mstiehr_dev.noteappmvp.model.Note;

import java.util.List;

public interface MainMVP
{
    interface NoteRepositoryOps
    {
        void addNote(Note note);
        void removeNote(Note note);
        List<Note> getNotes();
    }

    interface ViewOps
    {
        void showAlert(String msg);
        void refreshData();
    }

    interface PresenterModelOps
    {
        void onNoteAdded();
        void onNoteRemoved();
    }

    interface PresenterViewOps
    {
        void addNote(Note note);
        void removeNote(Note note);
        List<Note> getNotes();
    }
}
