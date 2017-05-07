package com.mstiehr_dev.noteappmvp.model;

import com.mstiehr_dev.noteappmvp.App;
import com.mstiehr_dev.noteappmvp.MainMVP;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class NoteRepositoryTest
{
    private MainMVP.NoteRepositoryOps classUnderTest;

    @Before
    public void setUp() throws Exception
    {
        classUnderTest = new IndependentNodeRepository();
    }

    @Test
    public void addNote() throws Exception
    {
        Note noteIn = new Note(System.currentTimeMillis(), "Test Note", new Date(System.currentTimeMillis()));
        classUnderTest.addNote(noteIn);

        List<Note> notes = classUnderTest.getNotes();

//        assertEquals(1, notes.size());  // todo: mock db
//        assertEquals(noteIn, notes.get(0));
    }

    @Test
    public void removeNote() throws Exception {

    }

    @Test
    public void getNotes() throws Exception {

    }

}