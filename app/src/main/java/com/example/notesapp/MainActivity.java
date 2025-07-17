package com.example.notesapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText noteInput;
    Button addNoteBtn;
    RecyclerView recyclerView;

    ArrayList<Note> notesList;
    NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteInput = findViewById(R.id.note_input);
        addNoteBtn = findViewById(R.id.add_note_btn);
        recyclerView = findViewById(R.id.recycler_view);

        notesList = new ArrayList<>();
        noteAdapter = new NoteAdapter(notesList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(noteAdapter);

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = noteInput.getText().toString();
                if (!text.isEmpty()) {
                    notesList.add(new Note(text));
                    noteAdapter.notifyItemInserted(notesList.size() - 1);
                    noteInput.setText("");
                }
            }
        });
    }
}
