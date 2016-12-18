package com.xr86.chucknorrisquotes;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    TextView TextView_quote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set variable after activity context is ready (otherwise aatempt to invoke virtual method -> NullPointerException)
        TextView_quote = (TextView) findViewById(R.id.TextView_quote);

        //avoid android.os.StrictMode$AndroidBlockGuardPolicy
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    public void load_new_quote(View view){
        //TextView_quote.setText("That didn't work!");

        try {

            URL url = new URL("https://api.chucknorris.io/jokes/random");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                result.append(line);
            }
            //System.out.println(result.toString());
            JSONObject jsonObj = new JSONObject(result.toString());

            String quote = jsonObj.getString("value");
            //System.out.println(quote);

            TextView_quote.setText(quote);

            urlConnection.disconnect();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
