package me.danielaguilar.recepies.dummies;

import java.util.ArrayList;
import java.util.Random;

import me.danielaguilar.recepies.models.Ingredient;

/**
 * Created by danielaguilar on 26-02-18.
 */

public class IngredientsDummyFactory {

    public static Ingredient getIngredient(){
        Random randomGenerator = new Random();
        int randomInt           =   randomGenerator.nextInt(100);
        Ingredient ingredient   =   new Ingredient();
        ingredient.setMeasure("Cups"+randomInt);
        ingredient.setName("Letuce"+randomInt);
        ingredient.setQuantity(3);
        return ingredient;
    }

    public static ArrayList<Ingredient> getIngredients(final int quantity){
        ArrayList<Ingredient> ingredients   =   new ArrayList<>(quantity);
        for (int i = 0; i < quantity; i++) {
            ingredients.add(getIngredient());
        }
        return ingredients;
    }
}
