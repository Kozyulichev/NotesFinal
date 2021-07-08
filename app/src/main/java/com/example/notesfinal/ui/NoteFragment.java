package com.example.notesfinal.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesfinal.MainActivity;
import com.example.notesfinal.Navigation;
import com.example.notesfinal.R;
import com.example.notesfinal.note.Note;
import com.example.notesfinal.note.NoteSourceResponse;
import com.example.notesfinal.note.NotesSource;
import com.example.notesfinal.note.NotesSourceFirestoreImpl;
import com.example.notesfinal.note.NotesSourceImpl;
import com.example.notesfinal.observer.Observer;
import com.example.notesfinal.observer.Publisher;

public class NoteFragment extends Fragment {

    private Navigation navigation;
    private Publisher publisher;
    private NotesSource data;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private boolean moveToFirstPosition;

    public static NoteFragment newInstance() {
        NoteFragment fragment = new NoteFragment();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity mainActivity = (MainActivity) context;
        navigation = mainActivity.getNavigation();
        publisher = mainActivity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setHasOptionsMenu(true);
        data = new NotesSourceFirestoreImpl().init(new NoteSourceResponse() {
            @Override
            public void initialized(NotesSource notesSource) {
                noteAdapter.notifyDataSetChanged();
            }
        });
        noteAdapter.setNotesSource(data);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onOptionsItemSelected(item);

    }
    private boolean onItemSelected(int menuItemId){
        switch (menuItemId){
            case R.id.menu_add:
                navigation.addFragment(DetailsFragment.newInstance(),true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNoteData(Note note) {
                        data.addNote(note);
                        noteAdapter.notifyItemInserted(data.size()-1);
                        moveToFirstPosition=true;
                    }
                });
                return true;
            case R.id.action_update:
                final int updatePosition = noteAdapter.getMenuPosition();
                navigation.addFragment(DetailsFragment.newInstance(data.getNoteData(updatePosition)),true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNoteData(Note note) {
                        data.updateNoteData(updatePosition,note);
                        noteAdapter.notifyItemInserted(updatePosition);
                    }
                });
                return true;
            case R.id.action_remove:
                int deletePosition = noteAdapter.getMenuPosition();
                data.deleteNoteData(deletePosition);
                noteAdapter.notifyItemRemoved(deletePosition);
                return true;
            case R.id.menu_settings:
                data.clearNotes();
                noteAdapter.notifyDataSetChanged();
                return true;
        }
        return false;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onContextItemSelected(item);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler);
        initRecycler();
    }

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        noteAdapter = new NoteAdapter(this);
        recyclerView.setAdapter(noteAdapter);

        noteAdapter.SetOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Note note = data.getNoteData(position);
                openDetailsNoteFragment(note);
            }
        });
        if (moveToFirstPosition && data.size() > 0){
            recyclerView.scrollToPosition(0);
            moveToFirstPosition = false;
        }

    }

    private void openDetailsNoteFragment(Note note) {
        DetailsFragment detailsNoteFragment = DetailsFragment.newInstance(note);
        navigation.addFragment(detailsNoteFragment, true);
    }
}