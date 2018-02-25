package me.danielaguilar.recepies.network;

import java.util.List;

import me.danielaguilar.recepies.config.Constants;
import me.danielaguilar.recepies.models.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by danielaguilar on 26-01-18.
 */

public interface RecipeService {
    @GET(Constants.API_RECIPES_URL)
    Call<List<Recipe>> listRecipes();
}
