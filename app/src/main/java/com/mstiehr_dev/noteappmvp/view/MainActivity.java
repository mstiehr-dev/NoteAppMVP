package com.mstiehr_dev.noteappmvp.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mstiehr_dev.noteappmvp.MainMVP;
import com.mstiehr_dev.noteappmvp.R;
import com.mstiehr_dev.noteappmvp.model.Note;
import com.mstiehr_dev.noteappmvp.platform.BackgroundService;
import com.mstiehr_dev.noteappmvp.presenter.MainPresenter;
import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirection;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainMVP.ViewOps
{
    private MainMVP.PresenterViewOps mainPresenter;

    private View rootView;
    private ListView listview;
    private NoteAdapter noteAdapter;
    private SwipeActionAdapter mSwipeActionAdapter;

    private Handler messageHandler = new Handler();
    private BackgroundService.BackgroundBinder backgroundBinder;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            backgroundBinder = (BackgroundService.BackgroundBinder) service;
            backgroundBinder.setActivityCallbackHandler(messageHandler);
            backgroundBinder.setCallbackRunnable(new BackgroundCallback());

            backgroundBinder.doShit();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

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
                composeNote();

                backgroundBinder.doShit();
            }
        });

        mainPresenter = new MainPresenter(this);
        noteAdapter = new NoteAdapter(mainPresenter.getNotes());

        mSwipeActionAdapter = new SwipeActionAdapter(noteAdapter);
        mSwipeActionAdapter.setListView(listview);

        mSwipeActionAdapter.setSwipeActionListener(new SwipeActionAdapter.SwipeActionListener() {
            @Override
            public boolean hasActions(int position, SwipeDirection direction) {
                if(direction.isLeft())
                    return true;
                if(direction.isRight())
                    return true;

                return false;
            }

            @Override
            public boolean shouldDismiss(int position, SwipeDirection direction) {
                return false;
            }

            @Override
            public void onSwipe(int[] positions, SwipeDirection[] directions) {
                for(int i=0; i<positions.length; i++)
                {
                    int position = positions[i];
                    SwipeDirection direction = directions[i];

                    String dir = "";
                    Note note = (Note) noteAdapter.getItem(position);

                    switch(direction)
                    {
                        case DIRECTION_NORMAL_LEFT:
                        case DIRECTION_FAR_LEFT:
                            dir = "Far Left - Deleting";
                            mainPresenter.removeNote(note);
                            refreshData();
                            break;
                        case DIRECTION_NEUTRAL:
                            dir = "Neutral";
                            break;
                        case DIRECTION_NORMAL_RIGHT:
                            dir = "Normal Right";
                            break;
                        case DIRECTION_FAR_RIGHT:
                            dir = "Far Right";
                            break;
                    }
                    String message = String.format("Position: %d / Direction: %s", position, dir);
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        listview.setAdapter(mSwipeActionAdapter);
        listview.setEmptyView(findViewById(R.id.empty));

        final Intent serviceIntent = new Intent(this, BackgroundService.class);
        startService(serviceIntent);
        bindService(serviceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
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
        noteAdapter.setNotes(mainPresenter.getNotes());
        noteAdapter.notifyDataSetChanged();
        mSwipeActionAdapter.notifyDataSetChanged();
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
                convertView = getLayoutInflater().inflate(R.layout.note_list_item, null);

            final Note note = notes.get(position);

            TextView tvNoteText = (TextView) convertView.findViewById(R.id.tv_title);
            tvNoteText.setText(note.getText());

            return convertView;
        }

        public void setNotes(List<Note> notes) {
            this.notes = notes;
        }
    }

    private void composeNote()
    {
        View dialogView = getLayoutInflater().inflate(R.layout.note_dialog_input, null);

        final EditText input = (EditText) dialogView.findViewById(R.id.dialog_note_input);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setTitle("Enter new note");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Note note = new Note();
                        Date date = new Date();

                        note.setText(input.getText().toString());
                        note.setDate(date);

                        mainPresenter.addNote(note);
                    }
                });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    public class BackgroundCallback implements Runnable
    {

        @Override
        public void run()
        {
            Toast.makeText(MainActivity.this, "this is a background call", Toast.LENGTH_LONG).show();
        }
    }


}
