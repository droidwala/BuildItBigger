package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.JokeTeller;
import com.example.punit.displayjokes.DisplayJokeActivity;
import com.example.punit.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<Context,Void,String>{

    private static MyApi myApiService = null;
    private Context context;
    JokeTeller jokeTeller;

    @Override
    protected String doInBackground(Context... params) {
        if(myApiService == null){
            MyApi.Builder builder  = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(),null)
                    .setRootUrl("https://buildbigger-1310.appspot.com/_ah/api/");

            myApiService  = builder.build();
        }
        context = params[0];
        jokeTeller = new JokeTeller();
        try {
            return myApiService.tellJoke(jokeTeller.getJoke()).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Intent i = new Intent(context, DisplayJokeActivity.class);
        i.putExtra("JOKE",result);
        context.startActivity(i);
    }
}
