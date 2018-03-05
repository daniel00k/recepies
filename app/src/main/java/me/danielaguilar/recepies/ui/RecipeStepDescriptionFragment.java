package me.danielaguilar.recepies.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.danielaguilar.recepies.R;
import me.danielaguilar.recepies.models.Step;

public class RecipeStepDescriptionFragment extends Fragment{

    private Step step;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.player_view)
    SimpleExoPlayerView mPlayerView;

    private MediaPlayerHelper mediaPlayerHelper;


    public RecipeStepDescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(getArguments() != null){
            step        = getArguments().getParcelable(Step.CLASS_NAME);
        }
        View view = inflater.inflate(R.layout.fragment_recipe_step_description, container, false);

        ButterKnife.bind(this, view);
        mediaPlayerHelper = MediaPlayerHelper.initialize(getActivity(), mPlayerView);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        if(step!= null){
            description.setText(step.getDescription());
            if(step.getVideoURL() != null && !step.getVideoURL().equals("")){
                // Initialize the Media Session.
                mediaPlayerHelper.initializeMediaSession();
                // Initialize the player.
                mediaPlayerHelper.initializePlayer(Uri.parse(step.getVideoURL()));
            }else{
                mPlayerView.setVisibility(View.INVISIBLE);
            }

        }
    }


    @Override
    public void onDestroy() {
        mediaPlayerHelper.releasePlayer();
        super.onDestroy();
    }

}
