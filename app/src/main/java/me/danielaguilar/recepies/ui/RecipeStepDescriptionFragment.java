package me.danielaguilar.recepies.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.danielaguilar.recepies.R;
import me.danielaguilar.recepies.models.Step;

import static android.content.Context.NOTIFICATION_SERVICE;

public class RecipeStepDescriptionFragment extends Fragment implements MediaPlayerHelper.OnPlayerStopListener{

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
        mediaPlayerHelper = MediaPlayerHelper.initialize(getActivity(), mPlayerView, this);
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

    @Override
    public void OnPlayerStop(long positionMs) {

    }
}
