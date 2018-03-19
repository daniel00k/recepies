package me.danielaguilar.recepies;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.danielaguilar.recepies.models.Ingredient;
import me.danielaguilar.recepies.models.Recipe;
import me.danielaguilar.recepies.network.ApiCaller;
import me.danielaguilar.recepies.network.RecipeCaller;
import me.danielaguilar.recepies.ui.MainActivity;
import me.danielaguilar.recepies.ui.RecipeActivity;
import retrofit2.Call;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static me.danielaguilar.recepies.ui.MainActivity.MY_PREFS_NAME;
import static me.danielaguilar.recepies.ui.MainActivity.SELECTED_RECIPE;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {
    public static final String KEY_ITEM     = "me.danielaguilar.recepies.KEY_ITEM";
    public static final String BUNDLE_KEY   = "bundle";

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager =AppWidgetManager.getInstance(context);
        onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeWidget.class)));
        super.onReceive(context, intent);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            getRecepies(context, appWidgetManager, appWidgetId);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void getRecepies(final Context context, final AppWidgetManager appWidgetManager, final int appWidgetId) {
        RecipeCaller recipeCaller = new RecipeCaller(new ApiCaller.OnFinishCall<List<Recipe>>() {
            @Override
            public void onSuccess(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                ArrayList<Recipe> recipes = (ArrayList<Recipe>) response.body();
                SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                int id = prefs.getInt(SELECTED_RECIPE, 0); //0 is the default value.
                int index = 0;
                for (int j=0; j<recipes.size(); j++){
                    if(recipes.get(j).getId() == id){
                        index = j;
                    }
                }
                setAdapter(recipes.get(index), context, appWidgetManager, appWidgetId);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("failed", t.toString());
                t.printStackTrace();
            }
        });

        recipeCaller.getRecipes(context);
    }

    public static void setAdapter(Recipe recipe, Context context, AppWidgetManager appWidgetManager, int appWidgetId){
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setTextViewText(R.id.appwidget_text_view, recipe.getName());

        Intent serviceIntent = new Intent(context, RecipeWidgetService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Recipe.CLASS_NAME, recipe);
        serviceIntent.putExtra(BUNDLE_KEY, bundle);
        views.setRemoteAdapter(R.id.appwidget_list, serviceIntent);


        //Launch Recipe Activity
        Intent appIntent = new Intent(context, RecipeActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.appwidget_list, appPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

