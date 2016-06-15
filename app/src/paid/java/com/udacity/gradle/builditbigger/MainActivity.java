package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.punit.displayjokes.DisplayJokeActivity;
import com.example.punit.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    @BindView(R.id.textview_main_askquestion) TextView question;
    @BindView(R.id.button_main_yes) Button yes_for_joke;
    @BindView(R.id.button_main_no) Button no_for_joke;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Setting up Click listeners
        yes_for_joke.setOnClickListener(this);
        no_for_joke.setOnClickListener(this);
    }


    /**
     * Handling button click events
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_main_yes:
                new EndpointsAsyncTask().execute();
                break;
            case R.id.button_main_no:
                finish();
                break;
            default:
                break;
        }

    }

    /**
     * Used during PreExecute phase of EndPointsAsyncTask
     */
    private void hideElements(){
        question.setVisibility(View.GONE);
        yes_for_joke.setVisibility(View.GONE);
        no_for_joke.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Used during PostExecute phase of EndPointsAsyncTask
     */
    private void showElements(){
        question.setVisibility(View.VISIBLE);
        yes_for_joke.setVisibility(View.VISIBLE);
        no_for_joke.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    /**
     * Fetches Joke from Endpoint supplied by javajokes library random jokes generator
     */
    public class EndpointsAsyncTask extends AsyncTask<Void,Void,String> {

        private MyApi myApiService = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            hideElements();
        }

        @Override
        protected String doInBackground(Void... params) {
            if(myApiService == null){
                MyApi.Builder builder  = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(),null)
                        .setRootUrl("https://buildbigger-1310.appspot.com/_ah/api/");

                myApiService  = builder.build();
            }
            try {
                return myApiService.tellJoke().execute().getJoke();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            showElements();
            Intent i = new Intent(MainActivity.this, DisplayJokeActivity.class);
            i.putExtra("JOKE",result);
            startActivity(i);
        }
    }
}
