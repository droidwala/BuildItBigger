package com.example.punit.displayjokes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DisplayJokeActivity extends AppCompatActivity {

    TextView joke_txt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp_joke);
        joke_txt = (TextView) findViewById(R.id.joke_txt);
        String joke = getIntent().getStringExtra("JOKE");
        joke_txt.setText(joke);

    }
}
