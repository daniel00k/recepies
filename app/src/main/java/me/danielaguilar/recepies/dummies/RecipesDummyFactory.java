package me.danielaguilar.recepies.dummies;

import me.danielaguilar.recepies.models.Recipe;

/**
 * Created by danielaguilar on 26-02-18.
 */

public class RecipesDummyFactory {

    public static Recipe getRecipe(){
        Recipe recipe = new Recipe(1, "Cheesecake");
        recipe.setSteps(StepsDummyFactory.getSteps(10));
        recipe.setIngredients(IngredientsDummyFactory.getIngredients(10));
        return recipe;
    }
}
