package com.Catchers.catchinggame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.List;

public class Leaderboard extends AppCompatActivity {

    public  class Entry{
        public String name;
        public int score;
        public Timestamp timestamp;
        public Entry(){
            name = "";
            score = 0;
            timestamp = Timestamp.now();
        }
    }
    private TextView[] Names;
    private TextView[] Scores;
    private TextView[] Dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);


        Names = new TextView[10];
        Names[0]= findViewById(R.id.name_1);
        Names[1]= findViewById(R.id.name_2);
        Names[2]= findViewById(R.id.name_3);
        Names[3]= findViewById(R.id.name_4);
        Names[4]= findViewById(R.id.name_5);
        Names[5]= findViewById(R.id.name_6);
        Names[6]= findViewById(R.id.name_7);
        Names[7]= findViewById(R.id.name_8);
        Names[8]= findViewById(R.id.name_9);
        Names[9]= findViewById(R.id.name_10);

        Scores = new TextView[10];
        Scores[0] = findViewById(R.id.score_1);
        Scores[1] = findViewById(R.id.score_2);
        Scores[2] = findViewById(R.id.score_3);
        Scores[3] = findViewById(R.id.score_4);
        Scores[4] = findViewById(R.id.score_5);
        Scores[5] = findViewById(R.id.score_6);
        Scores[6] = findViewById(R.id.score_7);
        Scores[7] = findViewById(R.id.score_8);
        Scores[8] = findViewById(R.id.score_9);
        Scores[9] = findViewById(R.id.score_10);

        Dates = new TextView[10];
        Dates[0] = findViewById(R.id.date_1);
        Dates[1] = findViewById(R.id.date_2);
        Dates[2] = findViewById(R.id.date_3);
        Dates[3] = findViewById(R.id.date_4);
        Dates[4] = findViewById(R.id.date_5);
        Dates[5] = findViewById(R.id.date_6);
        Dates[6] = findViewById(R.id.date_7);
        Dates[7] = findViewById(R.id.date_8);
        Dates[8] = findViewById(R.id.date_9);
        Dates[9] = findViewById(R.id.date_10);

        FirebaseFirestore db  = FirebaseFirestore.getInstance();
        db.collection("Leaderboard").orderBy("score", Query.Direction.DESCENDING).
                limit(10).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot scoreQuery = task.getResult();
                    assert scoreQuery != null;
                    List<DocumentSnapshot> ScoreObject = scoreQuery.getDocuments();
                 for (int i =0; i <10; i++){

                     if (ScoreObject.size() > i) {
                         DocumentSnapshot entry = ScoreObject.get(i);
                         Names[i].setText(entry.get("name", String.class));

                         Scores[i].setText(entry.get("score", String.class));
                         Dates[i].setText(entry.get("timestamp", String.class));

                     }
                     else{
                         Names[i].setText("");
                         Scores[i].setText("");
                         Dates[i].setText("");
                     }

                 }


                }
            }
        });




    }
}