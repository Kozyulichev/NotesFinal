package com.example.notesfinal.note;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotesSourceFirestoreImpl implements NotesSource {

    private static final String CARDS_COLLECTION = "cards";
    private static final String TAG = "[CardsSourceFirebaseImpl]";

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private CollectionReference collection = firebaseFirestore.collection(CARDS_COLLECTION);

    private List<Note> notes = new ArrayList<>();

    @Override
    public NotesSource init(final NoteSourceResponse noteSourceResponse) {
        collection.orderBy(NoteDataMapping.Fields.NAME, Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            notes = new ArrayList<>();
                            for (QueryDocumentSnapshot document:task.getResult()){
                                Map<String, Object> doc = document.getData();
                                String id = document.getId();
                                Note note = NoteDataMapping.toNoteData(id,doc);
                                notes.add(note);
                            }
                            Log.d(TAG, "success " + notes.size() + " qnt");
                            noteSourceResponse.initialized(NotesSourceFirestoreImpl.this);
                        }else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "get failed with ", e);
            }
        });
        return this;
    }

    @Override
    public Note getNoteData(int position) {
        return notes.get(position);
    }

    @Override
    public int size() {
        return notes.size();
    }

    @Override
    public void deleteNoteData(int position) {
        collection.document(notes.get(position).getId()).delete();
        notes.remove(position);
    }

    @Override
    public void updateNoteData(int position, Note note) {
        String id = note.getId();
        collection.document(id).set(NoteDataMapping.toDocument(note));

    }

    @Override
    public void addNote(Note note) {
        collection.add(NoteDataMapping.toDocument(note)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                note.setId(documentReference.getId());
            }
        });
    }

    @Override
    public void clearNotes() {
        for (Note note: notes){
            collection.document(note.getId()).delete();
        }
        notes = new ArrayList<>();

    }
}
