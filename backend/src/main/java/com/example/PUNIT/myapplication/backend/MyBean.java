package com.example.PUNIT.myapplication.backend;

/** The object model for the data we are sending through endpoints */
public class MyBean {

    private String myData;

    public String getJoke() {
        return this.myData;
    }

    public void setJoke(String data) {
        this.myData = data;
    }
}