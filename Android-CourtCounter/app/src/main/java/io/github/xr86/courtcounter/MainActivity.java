package io.github.xr86.courtcounter;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int teamAGoals;
    private int teamBGoals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        teamAGoals = 0;
        teamBGoals = 0;

        mChronometer = (Chronometer) findViewById(R.id.chronometer1);
    }

    protected void goalForTeamA(View view){
        if (isChronometerRunning) {
            teamAGoals += 1;
            TextView goals = (TextView) findViewById(R.id.team_a_score);
            goals.setText(String.valueOf(teamAGoals));

            ScrollView tempWrapper = (ScrollView) findViewById(R.id.goal_board_team_A_wrapper);
            tempWrapper.measure(0, 0);
            if (tempWrapper.getMeasuredHeight() > 400) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 400);
                tempWrapper.setLayoutParams(lp);
            }

            LinearLayout tempLayout = (LinearLayout) findViewById(R.id.goal_board_team_A);
            TextView tempTextView = (TextView)getLayoutInflater().inflate(R.layout.template_minute, null);

            String temp = "" + (SystemClock.elapsedRealtime() - mChronometer.getBase()) / 1000 / 60 + "'";//mChronometer.getText().toString();
            tempTextView.setText(temp);

            tempLayout.addView(tempTextView);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),"Please start the chronometer",Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    protected void goalForTeamB(View view){
        if (isChronometerRunning) {
            teamBGoals += 1;
            TextView goals = (TextView) findViewById(R.id.team_b_score);
            goals.setText(String.valueOf(teamBGoals));

            ScrollView tempWrapper = (ScrollView) findViewById(R.id.goal_board_team_B_wrapper);
            tempWrapper.measure(0, 0);
            if (tempWrapper.getMeasuredHeight() > 400) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 400);
                tempWrapper.setLayoutParams(lp);
            }

            LinearLayout tempLayout = (LinearLayout) findViewById(R.id.goal_board_team_B);
            TextView tempTextView = (TextView)getLayoutInflater().inflate(R.layout.template_minute, null);

            String temp = "" + (SystemClock.elapsedRealtime() - mChronometer.getBase()) / 1000 / 60 + "'";//mChronometer.getText().toString();
            tempTextView.setText(temp);

            tempLayout.addView(tempTextView);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),"Please start the chronometer",Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /*Timer functions*/
    private Chronometer mChronometer;
    private boolean isChronometerRunning = false;
    private long timeWhenStopped = 0;
    private Button start, stop, restart, set, clear;

    protected void start(View v) {
        isChronometerRunning = true;
        mChronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        mChronometer.start();
    }

    protected void stop(View v) {
        isChronometerRunning = false;
        timeWhenStopped = mChronometer.getBase() - SystemClock.elapsedRealtime();
        mChronometer.stop();
    }

    protected void restart(View v) {
        mChronometer.setBase(SystemClock.elapsedRealtime());
        timeWhenStopped = 0;
    }

    protected void reset(View v){
        stop(v);
        restart(v);

        //reset board for A
        ScrollView tempWrapper = (ScrollView) findViewById(R.id.goal_board_team_A_wrapper);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tempWrapper.setLayoutParams(lp);

        LinearLayout tempLayout = (LinearLayout) findViewById(R.id.goal_board_team_A);
        tempLayout.removeAllViews();

        teamAGoals = 0;
        TextView goals = (TextView) findViewById(R.id.team_a_score);
        goals.setText(String.valueOf(teamAGoals));

        //reset board for B
        tempWrapper = (ScrollView) findViewById(R.id.goal_board_team_B_wrapper);
        tempWrapper.setLayoutParams(lp);

        tempLayout = (LinearLayout) findViewById(R.id.goal_board_team_B);
        tempLayout.removeAllViews();

        teamBGoals = 0;
        goals = (TextView) findViewById(R.id.team_b_score);
        goals.setText(String.valueOf(teamBGoals));
    }
}
