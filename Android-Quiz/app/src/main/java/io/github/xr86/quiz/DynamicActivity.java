package io.github.xr86.quiz;

import android.databinding.DataBindingUtil;
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

import io.github.xr86.quiz.databinding.ActivityDynamicBinding;

public class DynamicActivity extends AppCompatActivity {
    List<Integer> correctAnswers = new ArrayList<Integer>();

    ActivityDynamicBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dynamic);

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

    /**
     * readFromFile - utility function to read a whole file and save it in a String
     * @param file - file to be read (InputStream)
     * @return fullText StringBuilder - returned as String
     */
    private String readFromFile(InputStream file){
        BufferedReader reader = null;
        StringBuilder fullText = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
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

    /**
     * constructView - function that constructs the view with questions
     * inside the LinearLayout of the Dynamic Activity Layout (which is by default empty)
     * @param configJson - a configuration json based on some policies
     */
    private void constructView(JsonNode configJson){
        Log.v("DynamicActivity.java", "RUN constructView()" );
        //Log.v("DynamicActivity.java", configJson.at("/questions").asText() );

        JsonNode questions = configJson.get("questions");
        //Log.v("DynamicActivity.java", questions.toString());

        int i = 1;
        for(JsonNode question : questions){
            TextView questionTextView = new TextView(this);
            TextViewCompat.setTextAppearance(questionTextView, R.style.TextViewStyle);

            //set text
            questionTextView.setText(i + ". " + question.get("text").asText());
            binding.questionContainer.addView(questionTextView);

            //set orientation
            RadioGroup optionsRadioGroup = new RadioGroup(this);
            //optionsRadioGroup.setId(R.id.options_group);
            //Log.v("DynamicActivity.java", "Layout is: " + question.get("layout").asText());
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

            //set radio options
            JsonNode options = question.get("options");
            Log.v("DynamicActivity.java", question.get("options").asText());
            for(JsonNode option : options){
                //Log.v("DynamicActivity.java", "option:\t" + option.get("text").asText());
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
            binding.questionContainer.addView(optionsRadioGroup);

            //Log.v("DynamicActivity.java", "answer: " + question.get("answer").asText());
            correctAnswers.add(question.get("answer").asInt() - 1); //json answers starting from 1
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

        binding.questionContainer.addView(submitButton);
        Log.v("DynamicActivity.java", "END constructView()" );
    }

    /**
     * calculateScore - based on the questions in the container and on the correct answers list,
     * calculates and displays the score for the quiz
     * @param v - View to be passed
     */
    public void calculateScore(View v){
        int correctCount = 0;
        int qCount = 0;
        for(int i = 1; i < binding.questionContainer.getChildCount(); i += 2){
            Log.v("DynamicActivity.java", "Iteration: " + i);
            //Log.v("DynamicActivity.java", viewContainer.getChildAt(i).getClass().getName());

            RadioGroup radioButtonGroup = (RadioGroup) binding.questionContainer.getChildAt(i);
            int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
            View radioButton = radioButtonGroup.findViewById(radioButtonID);
            int idx = radioButtonGroup.indexOfChild(radioButton);

            if(correctAnswers.get(qCount) == idx){ //json response is offseted by +1 (user count)
                correctCount++;
            }
            qCount += 1;
        }

        if(correctCount > qCount / 2){
            Toast.makeText(this, "Congratulations ! You answered correctly " + correctCount + " out of " + qCount + " questions", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Too bad ! You answered correctly " + correctCount + " out of " + qCount + " questions", Toast.LENGTH_SHORT).show();
        }
    }
}
