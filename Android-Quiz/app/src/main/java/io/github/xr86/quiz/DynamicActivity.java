package io.github.xr86.quiz;

import android.os.Build;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DynamicActivity extends AppCompatActivity {
    List<Integer> correctAnswers = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);

        //TODO: do some sort of shorthand here to alter function parameters (without implementing a view factory, different views ...)
//        RadioButton btn = new RadioButton(this);
//        Resources resId;
//        if (Build.VERSION.SDK_INT < 23) {
//            RadioButton.setTextAppearance(this, resId);
//        } else {
//            RadioButton.setTextAppearance(resId);
//        }

        InputStream file = this.getResources().openRawResource(R.raw.quiz);
        String fullText = readFromFile(file);

        JsonNode configJson = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            configJson = mapper.readTree(fullText);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.v("DynamicActivity.java", configJson.toString());
        constructView(configJson);
    }

    private String readFromFile(InputStream file){
        BufferedReader reader = null;
        StringBuilder fullText = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(file));
            String line = null;

            while ((line = reader.readLine()) != null) {
                //Log.v("DynamicActivity.java" , line);
                fullText.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Log.v("DynamicActivity.java" , fullText.toString());
        return fullText.toString();
    }

    private void constructView(JsonNode configJson){
        Log.v("DynamicActivity.java", "RUN constructView()" );
        //Log.v("DynamicActivity.java", configJson.at("/questions").asText() );

        LinearLayout viewContainer = (LinearLayout) findViewById(R.id.question_container);
        JsonNode questions = configJson.get("questions");
        //Log.v("DynamicActivity.java", questions.toString());

        int i = 1;
        for(JsonNode question : questions){
            //Log.v("DynamicActivity.java", question.toString());

            TextView questionTextView = new TextView(this);
            TextViewCompat.setTextAppearance(questionTextView, R.style.TextViewStyle);
            questionTextView.setText(i + ". " + question.get("text").asText());

            viewContainer.addView(questionTextView);

            RadioGroup optionsRadioGroup = new RadioGroup(this);
            //optionsRadioGroup.setId(R.id.options_group);

            Log.v("DynamicActivity.java", "Layout is: " + question.get("layout").asText());
            if( "horizontal".equals(question.get("layout").asText()) ) {
                optionsRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
            } else {
                optionsRadioGroup.setOrientation(LinearLayout.VERTICAL);
            }
            optionsRadioGroup.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );


            JsonNode options = question.get("options");
            Log.v("DynamicActivity.java", question.get("options").asText());
            for(JsonNode option : options){
                Log.v("DynamicActivity.java", "option:\t" + option.get("text").asText());

                RadioButton optionRadioButton = new RadioButton(this);
                //TODO: you shouldn't do these ~inline (HACK)
                if (Build.VERSION.SDK_INT < 23) {
                    optionRadioButton.setTextAppearance(this, R.style.RadioBtnStyle);
                } else {
                    optionRadioButton.setTextAppearance(R.style.RadioBtnStyle);
                }
                optionRadioButton.setText(option.get("text").asText());

                optionsRadioGroup.addView(optionRadioButton);
            }
            viewContainer.addView(optionsRadioGroup);

            Log.v("DynamicActivity.java", "answer: " + question.get("answer").asText());
            correctAnswers.add(question.get("answer").asInt());
            i++;
        }

        Button submitButton = new Button(this);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                calculateScore(v);
            }
        });
        submitButton.setText("Submit");

        viewContainer.addView(submitButton);
        Log.v("DynamicActivity.java", "END constructView()" );
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
            Toast.makeText(this, "Congratulations ! You answered correctly " + correctCount + " out of " + questionCount + " questions", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Too bad ! You answered correctly " + correctCount + " out of " + questionCount + " questions", Toast.LENGTH_SHORT).show();
        }
    }
}
