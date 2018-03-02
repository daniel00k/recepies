package me.danielaguilar.recepies;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.exoplayer2.SimpleExoPlayer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.danielaguilar.recepies.dummies.StepsDummyFactory;
import me.danielaguilar.recepies.models.Step;
import me.danielaguilar.recepies.ui.RecipeStepDescriptionActivity;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by danielaguilar on 02-03-18.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeStepDescriptionActivityInstrumentedTest {

    private Step step;

    @Rule
    public ActivityTestRule<RecipeStepDescriptionActivity> activityTestRule = new ActivityTestRule<RecipeStepDescriptionActivity>(RecipeStepDescriptionActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent result = new Intent(targetContext, RecipeStepDescriptionActivity.class);
            step = StepsDummyFactory.getStep();
            result.putExtra(Step.CLASS_NAME, step);
            return result;
        }
    };
    RecipeStepDescriptionActivity activity;


    @Test
    public void onLandscape_OnlyTheVideoIsVisible(){
        activity = activityTestRule.getActivity();
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        onView(withId(R.id.description)).check(doesNotExist());
        onView(withClassName(containsString(SimpleExoPlayer.class.getSimpleName()))).check(matches(isDisplayed()));
    }


    @Test
    public void onPortrait_TheVideoAndTheDescriptionIsVisible(){
        activity = activityTestRule.getActivity();
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        onView(withId(R.id.description)).check(matches(isDisplayed()));
        onView(withClassName(containsString(SimpleExoPlayer.class.getSimpleName()))).check(matches(isDisplayed()));
    }
}
