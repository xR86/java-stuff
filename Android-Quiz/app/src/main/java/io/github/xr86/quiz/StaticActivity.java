package io.github.xr86.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StaticActivity extends AppCompatActivity {
    List<List<Integer>> correctAnswers = new ArrayList<List<Integer>>(); // = new ArrayList<ArrayList<Integer>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static);

        //correctAnswers.add(new Integer[]{2});
        correctAnswers.add(new ArrayList<Integer>(){{ add(2); }});
        correctAnswers.add(new ArrayList<Integer>(){{ add(3); }});
        correctAnswers.add(new ArrayList<Integer>(){{ add(3); }});
        correctAnswers.add(new ArrayList<Integer>(){{ add(1); add(2); }});
    }

    /**
     * calculateScore - based on the questions in the container and on the correct answers list,
     * calculates and displays the score for the quiz
     * @param v - View to be passed
     */
    public void calculateScore(View v){
        LinearLayout viewContainer = (LinearLayout) findViewById(R.id.question_container);

        int questionCount =  viewContainer.getChildCount() / 2;

        int correctCount = 0;
        int qCount = 0;
        for(int i = 1; i < viewContainer.getChildCount(); i += 2){
            Log.v("DynamicActivity.java", "Iteration: " + i);
            //Log.v("DynamicActivity.java", viewContainer.getChildAt(i).getClass().getName());
            View tempView = viewContainer.getChildAt(i);
            int flag = -2;

            int idx1 = -2;
            List<Integer> idx2 = new ArrayList<Integer>();

            if(tempView instanceof RadioGroup) {
                flag = 0;
                RadioGroup radioButtonGroup = (RadioGroup) tempView;
                int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
                View radioButton = radioButtonGroup.findViewById(radioButtonID);
                idx1 = radioButtonGroup.indexOfChild(radioButton) + 1;
            } else if(tempView instanceof LinearLayout){
                flag = 1;
                LinearLayout viewGroup = (LinearLayout) tempView;
                for(int j = 0; j < viewGroup.getChildCount(); j++){
                    CheckBox chkBox = (CheckBox) viewGroup.getChildAt(j);
                    if(chkBox.isChecked()){
                        idx2.add(j+1);
                    }
                }
            } else if(tempView instanceof EditText){
                if(!((EditText) tempView).getText().toString().matches("")){
                    correctCount++;
                }
                continue;
            }


            if(flag == 0 && correctAnswers.get(qCount).get(0) == idx1){
                correctCount++;
            } else if(isTwoArrayListsWithSameValues(correctAnswers.get(qCount), idx2)){
                correctCount++;
            }
            qCount += 1;
        }

        if(correctCount > questionCount / 2){
            Toast.makeText(this, "Congratulations ! You answered correctly " + correctCount + " out of " + questionCount + " questions", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Too bad ! You answered correctly " + correctCount + " out of " + questionCount + " questions", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isTwoArrayListsWithSameValues(List<Integer> list1, List<Integer> list2)
    {
        //null checking
        if(list1==null && list2==null)
            return true;
        if((list1 == null && list2 != null) || (list1 != null && list2 == null))
            return false;

        if(list1.size() != list2.size())
            return false;
        for(Object itemList1: list1){
            if(!list2.contains(itemList1))
                return false;
        }
        return true;
    }
}
