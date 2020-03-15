package com.example.babytracker;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction editText = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withClassName(is("com.amazonaws.mobile.auth.userpools.FormView")),
                                0),
                        1),
                        isDisplayed()));
        editText.perform(replaceText("kay"), closeSoftKeyboard());

        ViewInteraction editText2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withClassName(is("com.amazonaws.mobile.auth.userpools.FormEditText")),
                                1),
                        0),
                        isDisplayed()));
        editText2.perform(replaceText("passd"), closeSoftKeyboard());

        ViewInteraction editText3 = onView(
                allOf(withText("passd"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.amazonaws.mobile.auth.userpools.FormEditText")),
                                        1),
                                0),
                        isDisplayed()));
        editText3.perform(click());

        ViewInteraction editText4 = onView(
                allOf(withText("passd"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.amazonaws.mobile.auth.userpools.FormEditText")),
                                        1),
                                0),
                        isDisplayed()));
        editText4.perform(replaceText("password1"));

        ViewInteraction editText5 = onView(
                allOf(withText("password1"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.amazonaws.mobile.auth.userpools.FormEditText")),
                                        1),
                                0),
                        isDisplayed()));
        editText5.perform(closeSoftKeyboard());

        ViewInteraction button = onView(
                allOf(withText("Sign In"),
                        childAtPosition(
                                allOf(withId(R.id.user_pool_sign_in_view_id),
                                        childAtPosition(
                                                withClassName(is("com.amazonaws.mobile.auth.ui.SignInView")),
                                                1)),
                                1),
                        isDisplayed()));
        button.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.addBaby), withText("Add Baby"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.newBabyNameActual),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.newBabyNameLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("Nick"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.newBabyDOBActual),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.newBabyDOBLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("03/05/2000"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.submit), withText("Submit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Notification"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.date),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("03/12/2020"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.time),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("1800"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.setNotification), withText("Set Notification"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton3.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
