package io.github.xr86.quiz;

import android.content.res.Resources;
import android.os.Build;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        Log.v("MainActivity.java", configJson.toString());
        constructView(configJson);
    }

    private String readFromFile(InputStream file){
        BufferedReader reader = null;
        StringBuilder fullText = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(file));
            String line = null;

            while ((line = reader.readLine()) != null) {
                //Log.v("MainActivity.java" , line);
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
        //Log.v("MainActivity.java" , fullText.toString());
        return fullText.toString();
    }

    private void constructView(JsonNode configJson){
        Log.v("MainActivity.java", "constructView" );
        //Log.v("MainActivity.java", configJson.at("/questions").asText() );


        LinearLayout viewContainer = (LinearLayout) findViewById(R.id.question_container);
        JsonNode questions = configJson.get("questions");
        Log.v("MainActivity.java", questions.toString());

        int i = 1;
        for(JsonNode question : questions){
            //Log.v("MainActivity.java", question.toString());
            Log.v("MainActivity.java", question.get("text").asText());
            Log.v("MainActivity.java", question.get("layout").asText());


            TextView questionTextView = new TextView(this);
            TextViewCompat.setTextAppearance(questionTextView, R.style.TextViewStyle);
            questionTextView.setText(i + ". " + question.get("text").asText());

            viewContainer.addView(questionTextView);

            RadioGroup optionsRadioGroup = new RadioGroup(this);

            if( question.get("text").asText() == "horizontal") {
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
            Log.v("MainActivity.java", question.get("options").asText());
            for(JsonNode option : options){
                Log.v("MainActivity.java", option.get("text").asText());

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

            Log.v("MainActivity.java", question.get("answer").asText());
            i++;
        }

        Button submitButton = new Button(this);
        submitButton.setText("Submit");

        viewContainer.addView(submitButton);
    }
}
