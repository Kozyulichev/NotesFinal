package com.example.notesfinal.note;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    private String id;
    private String name;
    private String text;
    private String date;

    public Note(String name, String text, String date) {
        this.name = name;
        this.text = text;
        this.date = date;
    }

    protected Note(Parcel in) {
        name = in.readString();
        text = in.readString();
        date = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(text);
        dest.writeString(date);
    }
}
