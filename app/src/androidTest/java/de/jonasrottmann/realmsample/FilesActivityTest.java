package de.jonasrottmann.realmsample;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FilesActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void filesActivityTest() {
        ViewInteraction button = onView(allOf(withId(R.id.btnRemove), withText("Clear database"), isDisplayed()));
        button.perform(click());

        ViewInteraction button2 = onView(allOf(withId(R.id.btnInsert), withText("Insert 100 Users"), isDisplayed()));
        button2.perform(click());

        ViewInteraction textView = onView(allOf(withId(R.id.txtTitle), withText("Items in database: 100"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 0), isDisplayed()));
        textView.check(matches(withText("Items in database: 100")));

        ViewInteraction button3 = onView(allOf(withId(R.id.btnOpenFile), withText("Open file list"), isDisplayed()));
        button3.perform(click());

        ViewInteraction textView2 = onView(allOf(withId(android.R.id.text1), withText("db10.realm"), childAtPosition(childAtPosition(withId(R.id.realm_browser_listView), 0), 0), isDisplayed()));
        textView2.check(matches(withText("db10.realm")));
    }

    private static Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent) && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
