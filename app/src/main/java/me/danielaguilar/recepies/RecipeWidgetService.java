package me.danielaguilar.recepies;

import android.content.Intent;
import android.widget.RemoteViewsService;
/**
 * Created by danielaguilar on 06-03-18.
 */

public class RecipeWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipesRemoteViewsFactory(getApplicationContext(), intent);
    }
}

