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
import me.danielaguilar.recepies.models.Step;

/**
 * Created by danielaguilar on 22-01-18.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder>{
    public interface OnStepSelected{
        void onSelect(Step step, int oldPosition, int newPosition);
    }
    private OnStepSelected listener;
    private List<Step> steps;
    private Context mContext;
    private int selectedIndex=-1;

    public StepAdapter(final List<Step> steps, final OnStepSelected listener, final Context context){
        this.listener   = listener;
        this.steps      = steps;
        mContext        = context;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_step_item, parent, false);
        return new StepViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        holder.bind(steps.get(position));
    }


    @Override
    public int getItemCount() {
        return steps.size();
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView shortDescription;
        private ImageView thumbnail;



        public StepViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            shortDescription    =   itemView.findViewById(R.id.short_description);
            thumbnail           =   itemView.findViewById(R.id.thumbnail);
        }

        public void bind(final Step step){
            shortDescription.setText(step.getShortDescription());
            if(step.getThumbnailURL() != null && !step.getThumbnailURL().equals("")){
                Picasso.with(mContext).load(step.getThumbnailURL()).into(thumbnail);
            }else{
                thumbnail.setVisibility(View.GONE);
            }

        }

        @Override
        public void onClick(View view) {
            final int oldPosition =  selectedIndex;
            selectedIndex = getAdapterPosition();
            listener.onSelect(steps.get(getAdapterPosition()), oldPosition, selectedIndex);
        }
    }
}
