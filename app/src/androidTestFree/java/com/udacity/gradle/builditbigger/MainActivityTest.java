package com.udacity.gradle.builditbigger;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {


    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkTextView(){
        //Check all widgets are displayed correctly
        onView(withId(R.id.textview_main_askquestion)).check(matches(isDisplayed()));
        onView(withId(R.id.button_main_yes)).check(matches(isDisplayed()));
        onView(withId(R.id.button_main_no)).check(matches(isDisplayed()));
        onView(withId(R.id.adView)).check(matches(isDisplayed()));

        onView(withId(R.id.button_main_yes)).perform(click());


        onView(withId(R.id.textview_disp_joke)).check(matches(CustomMatchers.isTextViewEmpty()));

    }
}
