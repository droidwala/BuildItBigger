package com.example.punit.displayjokes;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class DisplayJokeActivity extends AppCompatActivity {

    TextView joke_text;
    TextView joke_header;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp_joke);
        setStatusBarColor();
        joke_text = (TextView) findViewById(R.id.textview_disp_joke);
        joke_header = (TextView) findViewById(R.id.textview_disp_joketype);
        String joke = getIntent().getStringExtra("JOKE");
        if(joke!=null) {
            joke_text.setText(joke);
        }
        else{
            joke_header.setText("DUH!!");
            joke_text.setText("Our server joked on us! LOL!");
        }

    }

    private void setStatusBarColor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.PrimaryDark));
        }
    }
}
