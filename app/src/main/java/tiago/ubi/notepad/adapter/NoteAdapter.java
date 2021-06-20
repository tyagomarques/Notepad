package tiago.ubi.notepad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tiago.ubi.notepad.R;
import tiago.ubi.notepad.callback.NoteEventListener;
import tiago.ubi.notepad.model.Note;
import tiago.ubi.notepad.utils.NoteUtils;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private ArrayList<Note> notes;
    private Context context;
    private NoteEventListener listener;

    public NoteAdapter(ArrayList<Note> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_layout, parent, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {
        Note note = getNote(position);
        if (note != null) {
            holder.note_text.setText(note.getNoteText());
            holder.note_date.setText(NoteUtils.dateFromLong(note.getNoteDate()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNoteClick(note);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onNoteLongClick(note);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    private Note getNote(int position) {
        return notes.get(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        TextView note_text, note_date;

        public NoteHolder(View itemView) {
            super(itemView);
            note_text = itemView.findViewById(R.id.note_text);
            note_date = itemView.findViewById(R.id.note_date);

        }
    }

    public void setListener(NoteEventListener listener) {
        this.listener = listener;
    }
}
