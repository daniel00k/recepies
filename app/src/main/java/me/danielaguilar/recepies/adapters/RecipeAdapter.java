package me.danielaguilar.recepies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
    private Context mContext;

    public RecipeAdapter(final List<Recipe> recipes, final OnRecipeSelected listener, final Context context){
        this.listener   = listener;
        this.recipes    = recipes;
        this.mContext    = context;
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
        private ImageView recipeImage;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            recipeName  =   itemView.findViewById(R.id.recipe_name);
            recipeImage =   itemView.findViewById(R.id.recipe_image);
        }

        public void bind(final Recipe recipe){
            recipeName.setText(recipe.getName());
            if(recipe.getImageURL() != null && !recipe.getImageURL().equals("")){
                Picasso.with(mContext).load(recipe.getImageURL()).into(recipeImage);
            }else{
                recipeImage.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View view) {
            listener.onSelect(recipes.get(getAdapterPosition()));
        }
    }
}
