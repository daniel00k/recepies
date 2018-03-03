package me.danielaguilar.recepies;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.GradientDrawable;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import me.danielaguilar.recepies.ui.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by danielaguilar on 25-02-18.
 */

public class MainActivityInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private IdlingResource mIdlingResource;

    private MainActivity mActivity;

    private int listId;

    @Before
    public void registerIdlingResource() {
        mActivity       = activityTestRule.getActivity();
        mIdlingResource = mActivity.getIdlingResource();
        listId          = mActivity.findViewById(R.id.recipes_grid) != null ? R.id.recipes_grid : R.id.recipes_list;

        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void onClickIngredientItem_ViewPagerIsDisplayed() throws IOException {
        //onData(anything()).inAdapterView(withId(R.id.recipes_grid)).atPosition(1).perform(click());

        onView(withId(listId))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition(0, click())
                );
        onView(withId(R.id.pager)
        ).check(matches(isDisplayed()));
    }

    @Test
    public void recyclerViewIsDisplay(){
        if(listId==R.id.recipes_list){
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        onView(withId(listId))
                .check(matches(isDisplayed()));

    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
