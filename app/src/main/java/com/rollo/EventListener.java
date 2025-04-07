package com.rollo;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;

public class EventListener {
    private final MainActivity mainActivity;
    private List<Button> menuButtons;

    private List<Button> gameButtons;

    private View dimOverlay;

    //Transition Views
    private View blackoutView;
    //private final View diceView;
    private final AnimationPlayer animPlayer;

    private final Handler handler = new Handler(Looper.getMainLooper());

    private TouchColorButton loadGameButton, newGameButton, tutorialButton, creditsButton;
    private TouchColorButton tutorialBackButton, creditsBackButton;

    private View tutorialView, creditsView;

    public EventListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.animPlayer = new AnimationPlayer(mainActivity);

        initViews();
        setUpMenuButtons();
        setUpGameButtons();
        setUpListeners();
    }//EventListener

    private void initViews() {
        dimOverlay = mainActivity.findViewById(R.id.dimOverlay);
        blackoutView = mainActivity.findViewById(R.id.blackoutView);
        //diceView = mainActivity.findViewById(R.id.diceView);

        loadGameButton = mainActivity.findViewById(R.id.loadGameButton);
        newGameButton = mainActivity.findViewById(R.id.newGameButton);
        tutorialButton = mainActivity.findViewById(R.id.tutorialButton);
        creditsButton = mainActivity.findViewById(R.id.creditsButton);

        tutorialBackButton = mainActivity.findViewById(R.id.tutorialBackButton);
        creditsBackButton = mainActivity.findViewById(R.id.creditsBackButton);
        tutorialView = mainActivity.findViewById(R.id.tutorialView);
        creditsView = mainActivity.findViewById(R.id.creditsView);
    }//initViews

    // Store all core buttons in a list for easy disabling/enabling
    private void setUpMenuButtons(){
        menuButtons = Arrays.asList(loadGameButton, newGameButton, tutorialButton, creditsButton);
    }//setUpMenuButtons

    private void setUpGameButtons(){
        //gameButtons = Arrays.asList();
    }//setUpGameButtons

    private void setUpListeners(){
        loadGameButton.setOnClickListener(v -> playGameTransition(animPlayer.radialBlackout(),
                animPlayer.diceRotate()));
        newGameButton.setOnClickListener(v -> playGameTransition(animPlayer.radialBlackout(),
                animPlayer.diceRotate()));
        tutorialButton.setOnClickListener(v -> showOverlay(tutorialView, tutorialBackButton,
                animPlayer.slideUp()));
        creditsButton.setOnClickListener(v -> showOverlay(creditsView, creditsBackButton,
                animPlayer.slideUp()));
        tutorialBackButton.setOnClickListener(v -> hideOverlay(tutorialView, tutorialBackButton,
                animPlayer.slideDown()));
        creditsBackButton.setOnClickListener(v -> hideOverlay(creditsView, creditsBackButton,
                animPlayer.slideDown()));
    }//setUpListeners

    private void enableMenuButtons(boolean enabled) {
        for (Button button : menuButtons) {
            button.setEnabled(enabled);
        }//for-loop
    }//enableButtons

    private void showOverlay(View overlayView, Button backButton, Animation slideUp) {
        overlayView.setVisibility(View.VISIBLE);
        overlayView.startAnimation(slideUp);

        backButton.setVisibility(View.VISIBLE);
        backButton.startAnimation(slideUp);

        dimOverlay.setVisibility(View.VISIBLE);
        dimOverlay.startAnimation(animPlayer.fadeIn());

        enableMenuButtons(false);
    }//showOverlay

    private void hideOverlay(View overlayView, Button backButton, Animation slideDown) {

        overlayView.startAnimation(slideDown);
        handler.postDelayed(() -> overlayView.setVisibility(View.GONE), slideDown.getDuration());

        backButton.startAnimation(slideDown);
        handler.postDelayed(() -> backButton.setVisibility(View.GONE), slideDown.getDuration());

        dimOverlay.startAnimation(animPlayer.fadeOut());
        handler.postDelayed(() -> dimOverlay.setVisibility(View.GONE),
                animPlayer.fadeOutAnim.getDuration());

        enableMenuButtons(true);
    }//hideOverlay


    private void playGameTransition(Animation radialBlackout, Animation diceRotate) {
            blackoutView.startAnimation(radialBlackout);

            //diceView.setVisibility(View.VISIBLE);
            //diceView.startAnimation(diceRotationAnim);

            long blackoutDuration = radialBlackout.getDuration();
            long rotateDuration = diceRotate.getDuration();
            long animDuration = Math.max(blackoutDuration, rotateDuration);

            handler.postDelayed(() -> MainActivity.moveToGame(mainActivity), animDuration);
    }//playGameTransition

}//EventListener
