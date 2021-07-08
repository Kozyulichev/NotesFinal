package com.example.notesfinal.ui;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesfinal.R;
import com.example.notesfinal.note.Note;
import com.example.notesfinal.note.NotesSource;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteApater> {

    private  NotesSource notesSource;
    private final Fragment fragment;
    private int menuPosition;

    private OnItemClickListener itemClickListener;

    public void SetOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // Интерфейс для обработки нажатий, как в ListView
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    public NoteAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setNotesSource(NotesSource notesSource) {
        this.notesSource = notesSource;
        notifyDataSetChanged();
    }

    @NonNull
    @Override //Этот метод нужен что бы надуть макет
    public NoteApater onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Создаем пользовательский интерфейс
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteApater(v);
    }

    @Override //
    public void onBindViewHolder(@NonNull NoteApater holder, int position) {
        holder.onBind(notesSource.getNoteData(position));
    }

    @Override
    public int getItemCount() {
        return notesSource.size();
    }

    public class NoteApater extends RecyclerView.ViewHolder {

        private final TextView tv_name;
        private final TextView tv_text;
        private final TextView tv_date;

        public NoteApater(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.name_note);
            tv_text = itemView.findViewById(R.id.text_note1);
            tv_date = itemView.findViewById(R.id.date_note);

            registerForContextMenu(itemView);

            itemView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public boolean onLongClick(View v) {
                    menuPosition = getLayoutPosition();
                    itemView.showContextMenu(10,10);
                    return true;
                }
            });

        }

        private void registerForContextMenu(@NonNull View itemView) {
            if (fragment!=null){
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        menuPosition = getLayoutPosition();
                        return false;
                    }
                });
                fragment.registerForContextMenu(itemView);
            }
        }

        private void onBind(Note note) {

            tv_name.setText(note.getName());
            tv_text.setText(note.getText());
            tv_date.setText(note.getDate());

        }
    }

    public int getMenuPosition() {
        return menuPosition;
    }
}
