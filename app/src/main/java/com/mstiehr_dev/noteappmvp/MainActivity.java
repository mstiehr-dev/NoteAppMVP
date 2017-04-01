package com.mstiehr_dev.noteappmvp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mstiehr_dev.noteappmvp.model.Note;
import com.mstiehr_dev.noteappmvp.presenter.MainPresenter;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainMVP.ViewOps
{
    private MainMVP.PresenterViewOps mainPresenter;

    private View rootView;
    private ListView listview;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootView = findViewById(R.id.root_view);
        listview = (ListView) findViewById(R.id.listview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note note = new Note();
                Date date = new Date();

                note.setText(String.format("dummy note #%d", date.getTime()));
                note.setDate(date);

                mainPresenter.addNote(note);
            }
        });

        mainPresenter = new MainPresenter(this);

        noteAdapter = new NoteAdapter(mainPresenter.getNotes());
        listview.setAdapter(noteAdapter);
        listview.setEmptyView(findViewById(R.id.empty));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showAlert(String msg)
    {
        Snackbar.make(rootView, msg, Snackbar.LENGTH_LONG)
//          .setAction("Action", null)
            .show();
    }

    @Override
    public void refreshData()
    {
        noteAdapter.notifyDataSetChanged();
    }

    class NoteAdapter extends BaseAdapter
    {
        List<Note> notes;

        public NoteAdapter(List<Note> notes)
        {
            this.notes = notes;
        }

        @Override
        public int getCount() {
            return notes.size();
        }

        @Override
        public Object getItem(int position)
        {
            return notes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if(null==convertView)
                convertView = getLayoutInflater().inflate(R.layout.note_view, null);

            final Note note = notes.get(position);

            TextView tvNoteText = (TextView) convertView.findViewById(R.id.note_text);
            tvNoteText.setText(note.getText());

            return convertView;
        }
    }
}
