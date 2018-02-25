package me.danielaguilar.recepies.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.danielaguilar.recepies.R;
import me.danielaguilar.recepies.adapters.IngredientAdapter;
import me.danielaguilar.recepies.models.Ingredient;
import me.danielaguilar.recepies.models.Recipe;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeIngredientListFragment extends Fragment implements IngredientAdapter.OnIngredientSelected {

    @BindView(R.id.ingredients_list)
    RecyclerView ingredientsList;

    private Recipe recipe;

    public RecipeIngredientListFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recipe = getArguments().getParcelable(Recipe.CLASS_NAME);
        View view = inflater.inflate(R.layout.fragment_recipe_ingredient_list, container, false);
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setAdapter();
    }

    private void setAdapter(){
        IngredientAdapter ingredientAdapter = new IngredientAdapter(recipe.getIngredients(), this);
        ingredientsList.setAdapter(ingredientAdapter);
        ingredientsList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onSelect(Ingredient ingredient) {

    }
}
