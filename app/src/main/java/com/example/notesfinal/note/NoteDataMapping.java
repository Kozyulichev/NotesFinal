package com.example.notesfinal.note;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class NoteDataMapping {

    public static class Fields{

        public final static String NAME = "picture";
        public final static String TEXT = "date";
        public final static String DATE = "title";
    }
    public static Note toNoteData (String id, Map<String, Object> doc){
        //Timestamp timestamp = (Timestamp) doc.get(Fields.NAME);
        Note answer = new Note((String)doc.get(Fields.NAME),(String)doc.get(Fields.TEXT),
                (String)doc.get(Fields.DATE));
        answer.setId(id);
        return answer;
    }
    public static Map<String, Object> toDocument(Note cardData){
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.NAME,cardData.getName());
        answer.put(Fields.TEXT,cardData.getText());
        answer.put(Fields.DATE,cardData.getDate());
        return answer;
    }
}
