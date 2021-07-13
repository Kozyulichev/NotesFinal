package com.example.notesfinal;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notesfinal.observer.Publisher;
import com.example.notesfinal.ui.NoteFragment;
import com.example.notesfinal.ui.StartFragment;

public class MainActivity extends AppCompatActivity {

    private Navigation navigation;
    private Publisher publisher = new Publisher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = new Navigation(getSupportFragmentManager());
        getNavigation().addFragment(NoteFragment.newInstance(), false);
        //getNavigation().addFragment(StartFragment.newInstance(), false);
    }

    public Navigation getNavigation() {
        return navigation;

    }

    public Publisher getPublisher() {
        return publisher;
    }
}