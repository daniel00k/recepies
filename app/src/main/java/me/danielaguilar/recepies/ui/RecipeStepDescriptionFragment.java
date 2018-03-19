package me.danielaguilar.recepies.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.danielaguilar.recepies.R;
import me.danielaguilar.recepies.models.Step;

public class RecipeStepDescriptionFragment extends Fragment implements PlayerDialogFragment.OnPlayerStateChanged{

    private Step step;

    private long playerPosition = 0l;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.player_view)
    SimpleExoPlayerView mPlayerView;

    private MediaPlayerHelper mediaPlayerHelper;

    public static final String SECONDS = "seconds";


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

        if(savedInstanceState != null){
            playerPosition = savedInstanceState.getLong(SECONDS);
        }

        mediaPlayerHelper = MediaPlayerHelper.initialize(getActivity(), mPlayerView);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        if(step!= null){
            description.setText(step.getDescription());
            if(TextUtils.isEmpty(step.getVideoURL())){
                mPlayerView.setVisibility(View.INVISIBLE);
            }else{
                // Initialize the Media Session.
                mediaPlayerHelper.initializeMediaSession();
                // Initialize the player.
                mediaPlayerHelper.initializePlayer(Uri.parse(step.getVideoURL()), playerPosition);
            }

        }
    }

    @Override
    public void onPause() {
        if(mediaPlayerHelper.getExoPlayer()!=null){
            playerPosition  =   mediaPlayerHelper.getPlayerPosition();
        }
        mediaPlayerHelper.releasePlayer();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if(mediaPlayerHelper.getExoPlayer() != null){
            mediaPlayerHelper.releasePlayer();
        }
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong(SECONDS, playerPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPlayerStateChangedListener(long seconds) {
        playerPosition = seconds;
    }
}
