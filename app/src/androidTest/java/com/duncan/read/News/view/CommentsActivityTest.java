package com.duncan.read.News.view;

import android.test.ActivityInstrumentationTestCase2;

import com.duncan.read.R;

import org.junit.After;
import org.junit.Before;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Duncan Lim on 22/3/2018.
 */
public class CommentsActivityTest extends ActivityInstrumentationTestCase2<CommentsActivity> {

    private CommentsActivity activity;

    public CommentsActivityTest() {
        super(CommentsActivity.class);
    }



    public void testPreconditions() {
        assertNotNull("Activity is null", activity);
    }



    public void testHappyCaseViews() {
        onView(withId(R.id.listview_product)).check(matches(isDisplayed()));
        onView(withId(R.id.ll_home)).check(doesNotExist());
        onView(withText(R.string.txt_titlecomments)).check(matches(isDisplayed()));
}

    @Before
    public void setUp() throws Exception {
        activity = getActivity();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }



}