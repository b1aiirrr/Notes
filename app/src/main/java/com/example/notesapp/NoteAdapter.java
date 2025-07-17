package com.example.notesapp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private ArrayList<Note> notes;

    public NoteAdapter(ArrayList<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.noteText.setText(currentNote.getText());

        holder.editBtn.setOnClickListener(v -> {
            showEditDialog(v.getContext(), position);
        });

        holder.deleteBtn.setOnClickListener(v -> {
            notes.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, notes.size());
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    private void showEditDialog(Context context, int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_note, null);
        builder.setView(dialogView);


        AlertDialog dialog = builder.create();


        EditText editNoteInput = dialogView.findViewById(R.id.edit_note_input);
        Button saveButton = dialogView.findViewById(R.id.save_button);


        String existingNote = notes.get(position).getText();
        editNoteInput.setText(existingNote);


        saveButton.setOnClickListener(v -> {
            String updatedText = editNoteInput.getText().toString().trim();
            if (!updatedText.isEmpty()) {
                // Update the note in the data source
                notes.get(position).setText(updatedText);
                notifyItemChanged(position);
                dialog.dismiss();
            } else {
                Toast.makeText(context, "Note cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });


        dialog.show();
    }


    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView noteText;
        ImageButton editBtn, deleteBtn;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteText = itemView.findViewById(R.id.note_text);
            editBtn = itemView.findViewById(R.id.edit_btn);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
        }
    }
}
