package me.danielaguilar.recepies;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.danielaguilar.recepies.dummies.RecipesDummyFactory;
import me.danielaguilar.recepies.models.Recipe;
import me.danielaguilar.recepies.ui.RecipeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by danielaguilar on 26-02-18.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeActivityInstrumentedTest {

    RecipeActivity activity;
    private Recipe recipe = RecipesDummyFactory.getRecipe();

    @Rule
    public ActivityTestRule<RecipeActivity> activityTestRule = new ActivityTestRule<RecipeActivity>(RecipeActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent result = new Intent(targetContext, RecipeActivity.class);
            result.putExtra(Recipe.CLASS_NAME, recipe);
            return result;
        }
    };

    @Before
    public void setScreenOrientation(){
        activity = activityTestRule.getActivity();
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Test
    public void onSwipeLeft_ShowsAListOfIngredients(){
        onView(withId(R.id.pager)).perform(swipeLeft());
        onView(withId(R.id.ingredients_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }

    @Test
    public void onStepsListClick_ShowsAFragmentWithTheVideo(){
        onView(withId(R.id.pager)).perform(swipeRight());
        onView(withId(R.id.steps_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.fragment_container)).check(matches(isDisplayed()));
    }

    @Test
    public void onStepsListClick_ShowsAnActivityWithTheDescription(){
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        onView(withId(R.id.pager)).perform(swipeRight());
        onView(withId(R.id.steps_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.description)).check(matches(withText(recipe.getSteps().get(0).getDescription())));
    }
}
