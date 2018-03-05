package me.danielaguilar.recepies.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.danielaguilar.recepies.R;
import me.danielaguilar.recepies.models.Recipe;

public class RecipeActivity extends BaseActivity {

    private static final int NUM_PAGES = 2;

    @BindView(R.id.pager)
    ViewPager pager;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @Nullable @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    private PagerAdapter mPagerAdapter;

    private Recipe recipe;

    public static final String  FOR_TABLET = "ForTablet";

    private boolean forTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            recipe = bundle.getParcelable(Recipe.CLASS_NAME);
            getSupportActionBar().setTitle(recipe.getName());
        }
        mPagerAdapter = new RecipeActivity.ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(pager);

        if(savedInstanceState != null){
            forTablet = savedInstanceState.getBoolean(FOR_TABLET);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT && forTablet){
            RecipeStepDescriptionFragment fragment = (RecipeStepDescriptionFragment) getSupportFragmentManager().findFragmentByTag(RecipeStepDescriptionFragment.class.getName());
            if(fragment != null){
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }




    /**
     * A simple pager adapter that represents 2 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Recipe.CLASS_NAME, recipe);
            forTablet   =   fragmentContainer != null;
            bundle.putBoolean(FOR_TABLET, forTablet);
            if(position == 1){
                RecipeIngredientListFragment fragment = new RecipeIngredientListFragment();
                fragment.setArguments(bundle);
                return fragment;
            }else{
                RecipeStepListFragment fragment = new RecipeStepListFragment();
                fragment.setArguments(bundle);
                return fragment;
            }

        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 1){
                return "Ingredients";
            }else{
                return "Steps";
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(FOR_TABLET, forTablet);
        super.onSaveInstanceState(outState);
    }
}