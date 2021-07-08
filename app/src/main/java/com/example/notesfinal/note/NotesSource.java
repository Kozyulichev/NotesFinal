package com.example.notesfinal.note;

public interface NotesSource {
    NotesSource init (NoteSourceResponse noteSourceResponse);
    Note getNoteData(int position);
    int size();
    void deleteNoteData(int position);
    void updateNoteData(int position,Note note);
    void addNote(Note note);
    void clearNotes();
}
