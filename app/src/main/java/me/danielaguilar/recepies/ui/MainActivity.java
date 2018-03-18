package me.danielaguilar.recepies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.danielaguilar.recepies.R;
import me.danielaguilar.recepies.adapters.RecipeAdapter;
import me.danielaguilar.recepies.models.Recipe;
import me.danielaguilar.recepies.network.RecipeCaller;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements RecipeAdapter.OnRecipeSelected, RecipeCaller.OnFinishCall<List<Recipe>>{

    private static int SPAN_COUNT = 3;
    private static String RECIPE_LIST = "recipeList";

    @Nullable @BindView(R.id.recipes_list)
    RecyclerView recipesList;

    @Nullable @BindView(R.id.recipes_grid)
    RecyclerView recipesGrid;

    @BindView(R.id.animation_view)
    LottieAnimationView animationView;

    private ArrayList<Recipe> recipes;

    private CountingIdlingResource countingIdlingResource = new CountingIdlingResource(MainActivity.class.getName());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(savedInstanceState != null && savedInstanceState.getParcelableArrayList(RECIPE_LIST)!=null){
            animationView.setVisibility(View.GONE);
            recipes = savedInstanceState.getParcelableArrayList(RECIPE_LIST);
            setAdapter();
        }else{
            countingIdlingResource.increment();
            callApi();
        }
    }

    private void setAdapter(){

        RecipeAdapter adapter = new RecipeAdapter(recipes, this, this);
        if(recipesList != null){
            recipesList.setVisibility(View.VISIBLE);
            recipesList.setLayoutManager(new LinearLayoutManager(this));
            recipesList.setAdapter(adapter);
        }else if(recipesGrid != null){//In this case is a tablet layout
            recipesGrid.setVisibility(View.VISIBLE);
            recipesGrid.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
            recipesGrid.setAdapter(adapter);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPE_LIST, recipes);
    }

    private void callApi(){
        RecipeCaller recipeCaller = new RecipeCaller(this);
        recipeCaller.getRecipes(this);
    }

    @Override
    public void onSelect(Recipe recipe) {
        Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
        intent.putExtra(Recipe.CLASS_NAME, recipe);
        startActivity(intent);
    }

    @Override
    public void onSuccess(Call<List<Recipe>> call, Response<List<Recipe>> response) {
        countingIdlingResource.decrement();
        animationView.setVisibility(View.GONE);
        recipes = (ArrayList<Recipe>)response.body();
        setAdapter();

    }

    @Override
    public void onFailure(Call<List<Recipe>> call, Throwable t) {

    }

    public IdlingResource getIdlingResource() {
        return countingIdlingResource;
    }
}
