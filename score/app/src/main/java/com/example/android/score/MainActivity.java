package com.example.android.score;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    int scoreTeamA=0,scoreTeamB=0;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
   

    public void addThreePoints(View view){
        scoreTeamA=scoreTeamA + 3;
      displayForTeamA(scoreTeamA);
    }

    public void addTwoPoints(View view){
        scoreTeamA=scoreTeamA + 2;
        displayForTeamA(scoreTeamA);
    }

    public void addOnePoint(View view){
        scoreTeamA=scoreTeamA + 1;
        displayForTeamA(scoreTeamA);
    }

    public void addThreeScore(View view){
        scoreTeamB=scoreTeamB + 3;
        displayForTeamB(scoreTeamB);
    }

    public void addTwoScore(View view){
        scoreTeamB=scoreTeamB + 2;
        displayForTeamB(scoreTeamB);
    }

    public void addOneScore(View view){
        scoreTeamB=scoreTeamB + 1;
        displayForTeamB(scoreTeamB);
    }

    public void reset(View view){
        scoreTeamA=0;
        scoreTeamB=0;
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
    }

    /**
     * Displays the given score for Team A.
     */
    public void displayForTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(score));
    }

    public void displayForTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(score));
    }

}
