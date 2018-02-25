package me.danielaguilar.recepies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.danielaguilar.recepies.R;
import me.danielaguilar.recepies.models.Ingredient;

/**
 * Created by danielaguilar on 22-01-18.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>{
    public interface OnIngredientSelected{
        void onSelect(Ingredient ingredient);
    }
    private OnIngredientSelected listener;
    private List<Ingredient> ingredients;

    public IngredientAdapter(final List<Ingredient> ingredients, final OnIngredientSelected listener){
        this.listener       = listener;
        this.ingredients    = ingredients;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);
        return new IngredientViewHolder(v);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.bind(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView ingredientDesciption;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ingredientDesciption  =   itemView.findViewById(R.id.ingredient_description);
        }

        public void bind(final Ingredient ingredient){
            this.ingredientDesciption.setText(ingredient.getDescription());
        }

        @Override
        public void onClick(View view) {
            listener.onSelect(ingredients.get(getAdapterPosition()));
        }
    }
}
