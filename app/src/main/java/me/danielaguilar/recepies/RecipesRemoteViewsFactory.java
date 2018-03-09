package me.danielaguilar.recepies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import me.danielaguilar.recepies.dummies.RecipesDummyFactory;
import me.danielaguilar.recepies.models.Ingredient;
import me.danielaguilar.recepies.models.Recipe;
import me.danielaguilar.recepies.network.ApiCaller;
import me.danielaguilar.recepies.network.RecipeCaller;
import retrofit2.Call;
import retrofit2.Response;

public class RecipesRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    List<Ingredient> list;
    Recipe recipe;


    public RecipesRemoteViewsFactory(){}

    public RecipesRemoteViewsFactory(Context context, Intent intent){
        mContext        =   context;
        recipe          =   intent.getBundleExtra(RecipeWidget.BUNDLE_KEY).getParcelable(Recipe.CLASS_NAME);
        list            =   recipe.getIngredients();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
        remoteViews.setTextViewText(R.id.widget_list_text, list.get(i).getName());
        Intent intent = new Intent();
        intent.putExtra(RecipeWidget.KEY_ITEM, list.get(i));

        intent.putExtra(Recipe.CLASS_NAME, recipe);
        remoteViews.setOnClickFillInIntent(R.id.widget_list_item, intent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
