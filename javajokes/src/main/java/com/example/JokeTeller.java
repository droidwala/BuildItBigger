package com.example;

import java.util.Random;

public class JokeTeller {

    private String[] jokes;
    private Random random;
    public JokeTeller(){
        random = new Random();
        jokes = new String[3];
        jokes[0] = "The guy who stole my diary died...\n\nMy thoughts are with his family";
        jokes[1] = "The bartender says,\"We don't serve time travelers in here.\"\n\nA time traveler walks into bar";
        jokes[2] = "I don't trust stairs...\n\nThey look like they're up to something";
    }

    public String getJoke(){
        return jokes[random.nextInt(jokes.length)];
    }



}
