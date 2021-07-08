package com.example.notesfinal.note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotesSourceImpl implements NotesSource {
    private List<Note> data;

    public NotesSourceImpl() {
        data = new ArrayList<>();
        this.data = data;
    }

    public NotesSource init(NoteSourceResponse noteSourceResponse) {

        Note note1 = new Note("Дело 1", "Сделать дело 1", "22.04.2012");
        Note note2 = new Note("Дело 2", "Сделать дело 2", "22.04.2012");
        Note note3 = new Note("Дело 3", "Сделать дело 3", "22.04.2012");
        Note note4 = new Note("Дело 4", "Сделать дело 4", "22.04.2012");
        Note note5 = new Note("Дело 5", "Сделать дело 5", "22.04.2012");
        Note[]notes = {note1,note2,note3,note4,note5};

        for (int i = 0; i <notes.length ; i++) {
            data.add(notes[i]);
        }
        if (noteSourceResponse !=null){
            noteSourceResponse.initialized(this);
        }

        return this;
    }


    @Override
    public Note getNoteData(int position) {
        return data.get(position);
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public void deleteNoteData(int position) {
        data.remove(position);
    }

    @Override
    public void updateNoteData(int position, Note note) {
        data.set(position,note);
    }

    @Override
    public void addNote(Note note) {
        data.add(note);
    }

    @Override
    public void clearNotes() {
        data.clear();
    }
}
