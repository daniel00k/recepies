package me.danielaguilar.recepies.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import me.danielaguilar.recepies.R;
import me.danielaguilar.recepies.models.Step;

/**
 * Created by danielaguilar on 21-02-18.
 */

public class PlayerDialogFragment extends DialogFragment{

    private Step step;

    private SimpleExoPlayerView mPlayerView;

    private MediaPlayerHelper mediaPlayerHelper;

    private long playerPosition = 0l;

    public interface OnPlayerStateChanged{
        void onPlayerStateChangedListener(long seconds);
    }

    private OnPlayerStateChanged listener;

    /** The system calls this to get the DialogFragment's layout, regardless
     of whether it's being displayed as a dialog or an embedded fragment. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setStep();
        // Inflate the layout to use as dialog or embedded fragment
        return inflater.inflate(R.layout.media_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mPlayerView = view.findViewById(R.id.player_view);
    }

    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            dismiss();
        }
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.media_player, null);

        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if ((i ==  android.view.KeyEvent.KEYCODE_BACK))
                {
                    getActivity().finish();
                    return true; // pretend we've processed it
                }
                else
                    return false; // pass on to be processed as normal
            }
        });
        mPlayerView = view.findViewById(R.id.player_view);

        setStep();
        initPlayer();

        return dialog;
    }

    private void initPlayer(){
        mediaPlayerHelper = MediaPlayerHelper.initialize(getActivity(), mPlayerView);
        if (step.getVideoURL() != null && !step.getVideoURL().equals("")) {
            // Initialize the Media Session.
            mediaPlayerHelper.initializeMediaSession();
            // Initialize the player.
            mediaPlayerHelper.initializePlayer(Uri.parse(step.getVideoURL()), playerPosition);
        } else {
            mPlayerView.setVisibility(View.INVISIBLE);
        }
    }

    private void setStep(){
        if(step == null){
            step = getArguments().getParcelable(Step.CLASS_NAME);
            playerPosition = getArguments().getLong(RecipeStepDescriptionActivity.SECONDS);
        }
    }

    @Override
    public void onDestroy() {
        mediaPlayerHelper.releasePlayer();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        if(listener != null){
            listener.onPlayerStateChangedListener(mediaPlayerHelper.getPlayerPosition());
        }
        super.onPause();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mediaPlayerHelper.releasePlayer();
        super.onDismiss(dialog);
    }

    public void setListener(OnPlayerStateChanged listener) {
        this.listener = listener;
    }

}