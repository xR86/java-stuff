package io.github.xr86.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StaticActivity extends AppCompatActivity {
    List<Integer> correctAnswers = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static);

        correctAnswers.add(2);
        correctAnswers.add(3);
        correctAnswers.add(3);
    }

    public void calculateScore(View v){
        LinearLayout viewContainer = (LinearLayout) findViewById(R.id.question_container);

        int questionCount =  viewContainer.getChildCount() / 2;

        int correctCount = 0;
        int qCount = 0;
        for(int i = 1; i < viewContainer.getChildCount(); i += 2){
            Log.v("DynamicActivity.java", "Iteration: " + i);
            //Log.v("DynamicActivity.java", viewContainer.getChildAt(i).getClass().getName());

            RadioGroup radioButtonGroup = (RadioGroup) viewContainer.getChildAt(i);
            int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
            View radioButton = radioButtonGroup.findViewById(radioButtonID);
            int idx = radioButtonGroup.indexOfChild(radioButton); //in the json count starts from 1

            //Log.v("DynamicActivity.java", "\tqCount: " + qCount);
            //Log.v("DynamicActivity.java", "\tcorrectAnswers(qCount): " + correctAnswers.get(qCount));
            //Log.v("DynamicActivity.java", "\tidx: " + idx);
            if(correctAnswers.get(qCount) - 1 == idx){ //json response is offseted by +1 (user count)
                correctCount++;
            }
            
            qCount += 1;
        }

        if(correctCount > questionCount / 2){
            Toast.makeText(this, "Congratuations ! You answered correctly " + correctCount + " out of " + questionCount + " questions", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Too bad ! You answered correctly " + correctCount + " out of " + questionCount + " questions", Toast.LENGTH_SHORT).show();
        }
    }
}
