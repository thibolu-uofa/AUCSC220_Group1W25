/**
 * The EventListener class is responsible for listening to and responding to button taps and clicks
 * throughout the application. It acts as the central manager of event-driven behaviour to keep the
 * code modular and easier to maintain.
 *
 * @author - Timi Aina
 * @version - 1.0
 * @date - April 08, 2025
 */
package com.rollo;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class EventListener {
    private final MainActivity mainActivity;
    private final GameField gameField;
    private List<Button> menuButtons;
    private List<TextView> gameButtons;
    private View tutorialView, creditsView;
    private View dimOverlay;
    private View blackoutView;
    private View diceImageView;
    private final AnimationManager animManager;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private TouchColorButton loadGameButton, newGameButton, tutorialButton, creditsButton;
    private TouchColorButton tutorialBackButton, creditsBackButton;

    private TextView returnToMenu;

    /**
     * Constructor to initialize the EventListener object for main menu.
     * @param mainActivity - the main menu activity
     */
    public EventListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.gameField = null;
        this.animManager = new AnimationManager(mainActivity);

        initMenuViews();
        setUpMenuButtons();
        setUpMenuListeners();
    }//EventListener

    /**
     * Constructor to initialize the EventListener object for game activity.
     * @param gameField - the game field activity
     */
    public EventListener(GameField gameField) {
        this.mainActivity = null;
        this.gameField = gameField;
        this.animManager = new AnimationManager(gameField);

        initGameViews();
        setUpGameButtons();
        setUpGameListeners();
    }//EventListener

    /**
     * Initializes all the views in the main menu activity.
     */
    private void initMenuViews() {
        dimOverlay = mainActivity.findViewById(R.id.dimOverlay);
        blackoutView = mainActivity.findViewById(R.id.blackoutView);
        //diceImageView = mainActivity.findViewById(R.id.diceImageView);

        loadGameButton = mainActivity.findViewById(R.id.loadGameButton);
        newGameButton = mainActivity.findViewById(R.id.newGameButton);
        tutorialButton = mainActivity.findViewById(R.id.tutorialButton);
        creditsButton = mainActivity.findViewById(R.id.creditsButton);

        tutorialBackButton = mainActivity.findViewById(R.id.tutorialBackButton);
        creditsBackButton = mainActivity.findViewById(R.id.creditsBackButton);
        tutorialView = mainActivity.findViewById(R.id.tutorialView);
        creditsView = mainActivity.findViewById(R.id.creditsView);
    }//initMenuViews

    /**
     * Initializes all the views in the game activity.
     */
    private void initGameViews() {
        returnToMenu = gameField.findViewById(R.id.MainMenu);
    }//initGameViews

    /**
     * Stores all buttons in a list for easy disabling/enabling in main menu activity.
     */
    private void setUpMenuButtons(){
        menuButtons = Arrays.asList(loadGameButton, newGameButton, tutorialButton, creditsButton);
    }//setUpMenuButtons

    /**
     * Stores all buttons in a list for easy disabling/enabling in game activity.
     */
    private void setUpGameButtons(){
        gameButtons = Arrays.asList(returnToMenu);
    }//setUpGameButtons

    /**
     * Sets up all the event listeners for the buttons in the main menu activity.
     */
    private void setUpMenuListeners(){
        loadGameButton.setOnClickListener(v -> loadGameTransition());
        newGameButton.setOnClickListener(v -> newGameTransition());
        tutorialButton.setOnClickListener(v -> showOverlay(tutorialView, tutorialBackButton,
                animManager.slideUpAnim));
        creditsButton.setOnClickListener(v -> showOverlay(creditsView, creditsBackButton,
                animManager.slideUpAnim));
        tutorialBackButton.setOnClickListener(v -> hideOverlay(tutorialView, tutorialBackButton,
                animManager.slideDownAnim));
        creditsBackButton.setOnClickListener(v -> hideOverlay(creditsView, creditsBackButton,
                animManager.slideDownAnim));
    }//setUpMenuListeners

    /**
     * Sets up all the event listeners for the buttons in the game activity.
     */
    private void setUpGameListeners(){
        returnToMenu.setOnClickListener(v -> returnMenuTransition());
    }//setUpGameListeners

    /**
     * Enables/disables all the buttons in the buttons list.
     * @param buttons - the button list to be altered
     * @param enabled - true to enable buttons events, false to disable them
     */
    private void enableButtons(List<Button> buttons, boolean enabled) {
        for (Button button : buttons) {
            button.setEnabled(enabled);
        } // for-loop
    } // enableMenuButtons

    /**
     * Slides the overlay view (tutorial/credits) up to be shown.
     * @param overlayView - the view to be shown
     * @param backButton - the view's button to be shown
     * @param slideUp - slide up animation
     */
    private void showOverlay(View overlayView, Button backButton, Animation slideUp) {
        overlayView.setVisibility(View.VISIBLE);
        overlayView.startAnimation(slideUp);

        backButton.setVisibility(View.VISIBLE);
        backButton.startAnimation(slideUp);

        dimOverlay.setVisibility(View.VISIBLE);
        dimOverlay.startAnimation(animManager.fadeInAnim);

        enableButtons(menuButtons, false);
    }//showOverlay

    /**
     * Slides the overlay view (tutorial/credits) down to be hidden.
     * @param overlayView - the view to be hidden
     * @param backButton - the view's button to be hidden
     * @param slideDown - slide down animation
     */
    private void hideOverlay(View overlayView, Button backButton, Animation slideDown) {

        overlayView.startAnimation(slideDown);
        handler.postDelayed(() -> overlayView.setVisibility(View.GONE), slideDown.getDuration());

        backButton.startAnimation(slideDown);
        handler.postDelayed(() -> backButton.setVisibility(View.GONE), slideDown.getDuration());

        dimOverlay.startAnimation(animManager.fadeOutAnim);
        handler.postDelayed(() -> dimOverlay.setVisibility(View.GONE),
                animManager.fadeOutAnim.getDuration());

        enableButtons(menuButtons, true);
    }//hideOverlay

    /**
     * Transitions the user to the main menu activity.
     */
    public void menuTransition() {
        blackoutView.setVisibility(View.VISIBLE);
        animManager.rollDice(diceImageView, blackoutView);
    }//menuTransition

    /**
     * Loads a new game as the user is transitioned from the main menu activity to the game
     * activity.
     */
    public void newGameTransition() {

        animManager.startBlackoutAnimation(blackoutView, mainActivity, "new", gameField);
    }//newGameTransition

    /**
     * Loads an existing game as the user is transitioned from the main menu activity to the game
     * activity.
     */
    public void loadGameTransition() {

        animManager.startBlackoutAnimation(blackoutView, mainActivity, "load", gameField);
    }//

    public void returnMenuTransition() {
        if (gameField != null) {
            GameField.openMenu(gameField); // This will trigger the radial animation
        }
    }
}//EventListener
