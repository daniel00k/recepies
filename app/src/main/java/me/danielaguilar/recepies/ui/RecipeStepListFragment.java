package me.danielaguilar.recepies.ui;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.danielaguilar.recepies.R;
import me.danielaguilar.recepies.StepsLayoutManager;
import me.danielaguilar.recepies.adapters.StepAdapter;
import me.danielaguilar.recepies.models.Recipe;
import me.danielaguilar.recepies.models.Step;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepListFragment extends Fragment implements StepAdapter.OnStepSelected, StepsLayoutManager.OnItemsAddedListener{

    @BindView(R.id.steps_list)
    RecyclerView stepsList;

    private Recipe recipe;

    public RecipeStepListFragment() {}

    private boolean forTablet;

    private StepsLayoutManager layoutManager;

    private int selectedStep = -1;

    public static final String SELECTED_STEP_POSITION= "step position";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recipe = getArguments().getParcelable(Recipe.CLASS_NAME);
        forTablet= getArguments().getBoolean(RecipeActivity.FOR_TABLET);
        View view = inflater.inflate(R.layout.fragment_recipe_step_list, container, false);
        ButterKnife.bind(this, view);

        if(savedInstanceState!=null){
            selectedStep = savedInstanceState.getInt(SELECTED_STEP_POSITION);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setAdapter();
    }

    private void setAdapter(){
        StepAdapter stepAdapter= new StepAdapter(recipe.getSteps(), this, getActivity());
        stepsList.setAdapter(stepAdapter);
        layoutManager = new StepsLayoutManager(getActivity());
        layoutManager.setListener(this);
        stepsList.setLayoutManager(layoutManager);

    }


    @Override
    public void onSelect(Step step, int oldPosition, int newPosition) {
        layoutManager.setAllAsUnselected();
        selectedStep = newPosition;
        setSelectedStep();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Step.CLASS_NAME, step);
        if (forTablet){
            ((BaseActivity)getActivity()).addFragmentTo(RecipeStepDescriptionFragment.class, R.id.fragment_container, bundle);
        }else{
            Intent intent = new Intent(getActivity(), RecipeStepDescriptionActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private void setSelectedStep(){
        View view = layoutManager.findViewByPosition(selectedStep);
        if(view != null){
            view.setSelected(true);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SELECTED_STEP_POSITION, selectedStep);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onItemsAttached() {
        setSelectedStep();
    }
}
