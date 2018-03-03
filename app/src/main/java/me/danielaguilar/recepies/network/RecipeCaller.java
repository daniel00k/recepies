package me.danielaguilar.recepies.network;

import android.content.Context;

import java.util.List;

import me.danielaguilar.recepies.models.Recipe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by danielaguilar on 27-01-18.
 */

public class RecipeCaller extends ApiCaller {
    private OnFinishCall listener;

    public RecipeCaller(OnFinishCall listener){
        this.listener = listener;
    }

    public  void getRecipes(Context context){
        if(getInstance(context).getRetrofit() != null){
            RecipeService recipeService = getInstance(context).getRetrofit().create(RecipeService.class);
            Call<List<Recipe>> call = recipeService.listRecipes();
            call.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    listener.onSuccess(call, response);
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    listener.onFailure(call, t);
                }
            });
        }
    }
}
