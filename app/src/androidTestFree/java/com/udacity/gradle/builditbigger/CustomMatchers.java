package com.udacity.gradle.builditbigger;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class CustomMatchers {
    public static Matcher<View> isTextViewEmpty(){
        return new BoundedMatcher<View,TextView>(TextView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("Checking whether textview is empty or not");
            }

            @Override
            protected boolean matchesSafely(TextView item) {
                if(item.getText().toString().isEmpty()){
                    return false;
                }
                else {
                    return true;
                }
            }
        };
    }
}
