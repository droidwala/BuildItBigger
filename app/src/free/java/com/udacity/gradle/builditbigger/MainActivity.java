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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    @BindView(R.id.textview_main_askquestion) TextView question;
    @BindView(R.id.button_main_yes) Button yes_for_joke;
    @BindView(R.id.button_main_no) Button no_for_joke;
    @BindView(R.id.adView) AdView adView;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    InterstitialAd interstitialAd;
    String joke;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Setting up click listeners
        yes_for_joke.setOnClickListener(this);
        no_for_joke.setOnClickListener(this);

        //Load BannerAd at bottom of the screen
        loadBannerAd();

        //Setting up Interstitial Ad to be shown while opening joke
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));

        //Listening to various Ad Events and reacting accordingly.
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                startDisplayJokeActivity();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                startDisplayJokeActivity();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                interstitialAd.show();
            }
        });
    }

    /**
     * Loads Banner Ad at bottom of screen using Admob API
     */
    private void loadBannerAd(){
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);
    }

    /**
     * Handling Click Events of Buttons
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
     * Used in PreExecute phase of EndpointsAsyncTask
     */
    private void hideElements(){
        question.setVisibility(View.GONE);
        yes_for_joke.setVisibility(View.GONE);
        no_for_joke.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Used in PostExecute phase of EndpointsAsyncTask
     */
    private void showElements(){
        question.setVisibility(View.VISIBLE);
        yes_for_joke.setVisibility(View.VISIBLE);
        no_for_joke.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    /**
     * Opens up DisplayJoke activity from displayjokes android lib , passing it joke as stringExtra
     */
    private void startDisplayJokeActivity(){
        showElements();
        Intent i = new Intent(MainActivity.this, DisplayJokeActivity.class);
        i.putExtra("JOKE",joke);
        startActivity(i);
    }


    /**
     * Fetches joke from Endpoint supplied by JavaJokes library's random joke generator
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

        /**
         * Once the joke is received we start DisplayJokesActivity interrupting it by loading Interstitial Ad in Free version
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            joke = result;
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            interstitialAd.loadAd(adRequest);
        }
    }

}
