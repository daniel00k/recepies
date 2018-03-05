package me.danielaguilar.recepies.ui;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.danielaguilar.recepies.R;
import me.danielaguilar.recepies.models.Step;

/**
 * Created by danielaguilar on 03-02-18.
 */

public class RecipeStepDescriptionActivity extends BaseActivity implements PlayerDialogFragment.OnPlayerStateChanged{

    private Step step;

    private long playerPosition = 0l;

    @Nullable @BindView(R.id.description)
    TextView description;

    @Nullable @BindView(R.id.player_view)
    SimpleExoPlayerView mPlayerView;

    private MediaPlayerHelper mediaPlayerHelper;

    private PlayerDialogFragment newFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        step = getIntent().getParcelableExtra(Step.CLASS_NAME);
        setContentView(R.layout.activity_recipe_step_description);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        mediaPlayerHelper = MediaPlayerHelper.initialize(this, mPlayerView);

        if(savedInstanceState != null){
            playerPosition = savedInstanceState.getLong("seconds");
        }
        initializePlayer();
    }

    private void showDialog(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        newFragment = new PlayerDialogFragment();
        newFragment.setListener(this);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Step.CLASS_NAME, step);
        bundle.putLong("seconds", playerPosition);
        newFragment.setArguments(bundle);

        newFragment.show(fragmentManager, "dialog");

        /*
        // The device is smaller, so show the fragment fullscreen
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // For a little polish, specify a transition animation
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // To make it fullscreen, use the 'content' root view as the container
        // for the fragment, which is always the root view for the activity
        transaction.add(android.R.id.content, newFragment)
                .addToBackStack(null).commit();*/
    }

    private void initializePlayer(){
        if(step != null){
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {//Portrait
                description.setText(step.getDescription());

                if (step.getVideoURL() != null && !step.getVideoURL().equals("")) {
                    // Initialize the Media Session.
                    mediaPlayerHelper.initializeMediaSession();
                    // Initialize the player.
                    mediaPlayerHelper.initializePlayer(Uri.parse(step.getVideoURL()), playerPosition);
                } else {
                    mPlayerView.setVisibility(View.INVISIBLE);
                }
            }else{
                showDialog();
            }

        }
    }

    @Override
    protected void onDestroy() {
        mediaPlayerHelper.releasePlayer();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(mediaPlayerHelper.getExoPlayer()!=null){
            outState.putLong("seconds", mediaPlayerHelper.getPlayerPosition());
        }else{
            outState.putLong("seconds", playerPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPlayerStateChangedListener(long seconds) {
        playerPosition = seconds;
    }
}
