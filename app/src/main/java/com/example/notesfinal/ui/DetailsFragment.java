
package com.example.notesfinal.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notesfinal.MainActivity;
import com.example.notesfinal.R;
import com.example.notesfinal.note.Note;
import com.example.notesfinal.observer.Publisher;

import java.util.Date;


public class DetailsFragment extends Fragment {

    private Publisher publisher;

    private EditText et_name;
    private EditText et_text;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Note note;

    public DetailsFragment() {
    }

    public static DetailsFragment newInstance(Note note) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, note);
        fragment.setArguments(args);
        return fragment;
    }

    public static DetailsFragment newInstance() {
        DetailsFragment fragment = new DetailsFragment();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity mainActivity = (MainActivity) context;
        publisher = mainActivity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @Override
    public void onStop() {
        super.onStop();
        note = collectNoteData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(note);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        et_name = view.findViewById(R.id.name_note);
        et_text = view.findViewById(R.id.text_note);
        if (note == null) {
            et_name.setText("Имя заметки");
            et_text.setText("Текст заметки");
        }
        if (getArguments() != null && getArguments().getParcelable(ARG_PARAM1) != null) {
            note = getArguments().getParcelable(ARG_PARAM1);
            if (note != null) {
                String name = note.getName();
                String text = note.getText();

                et_name.setText(name);
                et_text.setText(text);
            }
        }
    }

    private Note collectNoteData() {

        String name1 = this.et_name.getText().toString();
        String text2 = this.et_text.getText().toString();
        String date3 = "08.07.2021";
        if (note!=null){
        Note answer = new Note(name1,text2,date3);
        answer.setId(note.getId());
        return answer;}

        return new Note(name1,text2,date3);
    }
}