package me.danielaguilar.recepies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.danielaguilar.recepies.R;
import me.danielaguilar.recepies.models.Recipe;

/**
 * Created by danielaguilar on 22-01-18.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{
    public interface OnRecipeSelected{
        void onSelect(Recipe recipe);
    }
    private OnRecipeSelected listener;
    private List<Recipe> recipes;

    public RecipeAdapter(final List<Recipe> recipes, final OnRecipeSelected listener){
        this.listener   = listener;
        this.recipes    = recipes;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.bind(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView recipeName;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            recipeName  =   itemView.findViewById(R.id.recipe_name);
        }

        public void bind(final Recipe recipe){
            recipeName.setText(recipe.getName());
        }

        @Override
        public void onClick(View view) {
            listener.onSelect(recipes.get(getAdapterPosition()));
        }
    }
}
