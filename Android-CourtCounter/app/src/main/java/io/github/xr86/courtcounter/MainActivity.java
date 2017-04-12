package io.github.xr86.courtcounter;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int teamAGoals;
    private int teamBGoals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        teamAGoals = 0;
        teamBGoals = 0;

        //set = (Button) findViewById(R.id.button4);

        focus = (Chronometer) findViewById(R.id.chronometer1);
    }

    protected void goalForTeamA(View view){
        teamAGoals += 1;
        TextView goals = (TextView) findViewById(R.id.team_a_score);
        goals.setText(String.valueOf(teamAGoals));


        LinearLayout tempLayout = (LinearLayout) findViewById(R.id.goal_board_team_A);

        TextView tempTextView = new TextView(MainActivity.this);

        //TODO: problems here
        ViewGroup.LayoutParams params =  new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
//        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        tempTextView.setLayoutParams(params);
//
//        String temp = focus.getText().toString();
//        tempTextView.setText(temp);
//
//        tempLayout.addView(tempTextView);
    }

    protected void goalForTeamB(View view){
        teamBGoals += 1;
        TextView goals = (TextView) findViewById(R.id.team_b_score);
        goals.setText(String.valueOf(teamBGoals));
    }

    /*Timer functions*/
    Chronometer focus;
    Button start, stop, restart, set, clear;

    protected void clear(View v){
        focus.setFormat(null);
    }

    protected void start(View v) {
        focus.start();
    }

    protected void stop(View v) {
        focus.stop();
    }

    protected void restart(View v) {
        focus.setBase(SystemClock.elapsedRealtime());
    }

    protected void set(View v) {
        focus.setFormat("Formated Time - %s");
    }
}
