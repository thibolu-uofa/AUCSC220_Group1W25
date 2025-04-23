/**
 * GameField
 *
 * Author: ROLLO TEAM
 *
 * This file is the handler for everything that occurs in the game
 * section of the Rollo Experience. Everything from selecting and playing
 * dice, to swapping paintings, and rerolling is in this file.
 *
 * Functions:
 *      rerollDice(View view)
 *          rerolls dice
 *      start(View view)
 *          starts the game
 *      play(View myView)
 *          after selecting dice, play the dice
 *      lossGame()
 *          loss screen occurs, with some stats,
 *          and the game is reset
 *      openMenu(GameField gameField)
 *          starts animation to return back to menu
 *      openMenuAgain(Context context)
 *          returns user back to main menu
 *      openShop(Context context)
 *          moves the player to the shop
 *      resetSelectedDice()
 *          rerolls the selected dice after they
 *          have been played
 *      selectDice(View view)
 *          selects and deselects dice, with instant
 *          feedback on what they will outcome to
 *      getDiceIndex(TextView diceView)
 *          gets the index of a given dice textview
 *          in all arrays
 *      rerollAllDice(View myView)
 *          rerolls all dice at the start of the
 *          round
 *      getAllTheDice()
 *          initializes a global textview array
 *          that will allow for simple dice
 *          textview manipulation
 *      updateDiceArray()
 *          finds all the values of the selected
 *          dice
 *      selectedPainting(View view)
 *          selects, deselects, and swaps paintings
 *          to give the user ability to optimize
 *          scoring
 *      getPaintingIndex(TextView paintingView)
 *          returns the index of the given paintingView
 *
 */

package com.rollo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.app.Dialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameField extends AppCompatActivity {
    //External files to help run game
    private AnimationManager animManager;
    private Continue continueReader;
    private HandTypeManager hands;

    //Round Specific
    private boolean clickedStart = false;
    private int playScore;
    private int rerollsLeft;
    private int beginningRerolls;
    private int playsLeft;
    private int beginningPlays;
    private int roundScore;
    private TextView result;
    private TextView pipCount;
    private TextView multCount;
    private TextView threshold;
    private TextView scoreDisplay;
    private Dice[] sixDie;
    private TextView[] sixTextDie;
    private boolean hasLost = false;
    private Dialog lossDialog;



    private int amountSelected = 0;
    private final int[] sixValues = new int[]{0,0,0,0,0,0};
    private final boolean[] selectedTextDie = new boolean[]{false, false, false,
            false, false, false};

    //Paintings
    private TextView firstSelectedPainting = null;
    private int paintingsSelected = 0;
    private String[] paintings = new String[4]; // Your painting names array

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_field);
        result = findViewById(R.id.result);
        pipCount = findViewById(R.id.pipsText);
        multCount = findViewById(R.id.multText);
        scoreDisplay = findViewById(R.id.roundScore);
        threshold = findViewById(R.id.threshold);
        hands = new HandTypeManager();
        continueReader = new Continue(this);
        List<HandType> handTypes = continueReader.getHandTypesFromJson();
        continueReader.copyJsonToInternalStorageIfNeeded(this);
        roundScore = continueReader.getCurrentScoreFromJson();
        scoreDisplay.setText(String.valueOf(roundScore));
        rerollsLeft = continueReader.getRerollFromJson();
        beginningRerolls = rerollsLeft;
        playsLeft = continueReader.getPlaysFromJson();
        beginningPlays = playsLeft;
        String ScoreToBeat = Integer.toString(continueReader.getScoreToBeatFromJson());
        threshold.setText("Score to beat: " + ScoreToBeat);

        animManager = new AnimationManager(this);

        ImageView gameBackground = findViewById(R.id.gameBackground);
        gameBackground.startAnimation(animManager.waveAnim);

        View blackoutView = findViewById(R.id.blackoutView);
        blackoutView.post(() -> {
            animManager.startRevealAnimation(blackoutView);
        });


        hands = new HandTypeManager(handTypes);
        sixDie = continueReader.getDiceFromJson();

        sixTextDie = getAllTheDice();

        for (int i = 0; i < sixTextDie.length; i++) {
            sixTextDie[i].setBackgroundResource(R.drawable.dice_1);
        }
        new EventListener(this);
    }

    /**
     * rerollDice
     *
     * is a function that will convert the given textview dice,
     * reroll it, change the background resource on it
     *
     * @param view
     */
    public void rerollDice(View view) {
        TextView clicked = (TextView) view;
        if (clicked == null) return;

        try {
            String id = getResources().getResourceEntryName(clicked.getId());
            int whichDie = Integer.parseInt(id.replaceAll("\\D+", "")) - 1;
            int newSide = sixDie[whichDie].getRandomSide();

            sixValues[whichDie] = newSide;

            int resId = getResources().getIdentifier("dice_" + newSide, "drawable", getPackageName());

            clicked.setBackgroundResource(resId);
        } catch (Exception e) {
            Log.e("Dice", "Reroll error", e);
            clicked.setBackgroundResource(R.drawable.dice_1);
        }

    }

    /**
     * start
     *
     * is the function that starts the user round, this will make sure
     * that the user does not just jump in to the experience but have
     * control over their experience
     *
     * @param view
     */
    public void start(View view) {
        TextView clicked = (TextView) view;

        if (!clickedStart) {
            if (sixTextDie == null) {
                sixTextDie = getAllTheDice();
            }

            for (int i = 0; i < sixTextDie.length; i++) {
                if (sixTextDie[i] != null) {
                    rerollDice(sixTextDie[i]);
                }
            }
            clickedStart = true;

            result.setText("");
            pipCount.setText("0");
            multCount.setText("0");

            clicked.setText("Play");
            clicked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View myView) {play(myView);}
            });
        }

        playsLeft = continueReader.getPlaysFromJson();
        rerollsLeft = continueReader.getRerollFromJson();

        TextView rerollCounter = findViewById(R.id.rerollCounter);
        rerollCounter.setText(String.valueOf(rerollsLeft));

        TextView handLimitText = findViewById(R.id.handLimitText);
        handLimitText.setText(String.valueOf(playsLeft));
    }

    /**
     * play
     *
     * is a function that after a user selects some dice,
     * will allow them to play those dice. After playing the
     * hand, it will check if the user is going to progress
     * to the shop or have to continue playing to beat the score
     * given
     *
     * @param myView
     */
    public void play(View myView) {
        if (amountSelected >= 1 && amountSelected <= 5 && playsLeft > 0) {
            PaintingAndScoring scored = new PaintingAndScoring(hands);
            HandType hand = scored.scoring(updateDiceArray());
            resetSelectedDice();
            result.setText("");
            playScore = hand.getPips() * hand.getMult();
            roundScore += playScore;
            continueReader.setCurrentScore(this, roundScore);
            continueReader.setRunHighScore(this);
            continueReader.setAllTimeHighScore(this);
            scoreDisplay.setText(String.valueOf(roundScore));

            playsLeft--;
            continueReader.setPlays(this, playsLeft);
            TextView handLimitText = findViewById(R.id.handLimitText);
            handLimitText.setText(String.valueOf(playsLeft));

            if (continueReader.getScoreToBeatFromJson() <= roundScore){
                openShop(this);
            }

            //This should check to see if when the player has ran out of plays
            else if(playsLeft == 0 && continueReader.getScoreToBeatFromJson() > roundScore && !hasLost){
                //Need to change one of round score to highestPlay
                hasLost = true;
                lossGame();
            }

        }

    }

    /**
     * lossGame()
     *
     * This function is responsible for displaying the endGame dialog where the players
     * stats are shown
     *
     */
    private void lossGame(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.loss_screen);
        dialog.setCancelable(false);

        TextView roundText = dialog.findViewById(R.id.round);
        TextView scoreText = dialog.findViewById(R.id.score);
        TextView highscoreText = dialog.findViewById(R.id.highscore);

        roundText.setText(String.valueOf(continueReader.getRoundFromJson()));
        scoreText.setText(String.valueOf(continueReader.getRunHighScoreFromJson()));
        highscoreText.setText(String.valueOf(continueReader.getAllTimeHighScoreFromJson()));

        dialog.show();

        View decorView = dialog.getWindow().getDecorView();

        decorView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog.isShowing()){
                    dialog.dismiss();
                    openMenu(GameField.this);
                }
            }
        }, 5000);
    }

    /**
     * openMenu
     *
     * using Timi's animation manager, this will play a beautiful animation
     * as the user starts his move back to the main menu.
     *
     * @param gameField
     */
    public static void openMenu(GameField gameField) {
        View blackoutView = gameField.findViewById(R.id.blackoutView);
        AnimationManager animManager = new AnimationManager(gameField);

        // Start the radial reveal animation
        animManager.startBlackoutAnimation(blackoutView, null, "return", gameField);
    }

    /**
     * openMenuAgain
     *
     * is the actual function that moves the user back to the main menu
     * by finish this activity.
     *
     * @param context
     */
    public static void openMenuAgain(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);

        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }

    /**
     * openShop
     *
     * will move the user to the shop page and reset Values
     *
     * @param context
     */
    private void openShop(Context context) {
        Intent intent = new Intent(context, ShopPage.class);
        context.startActivity(intent);
        continueReader.setScoreToBeat(this, continueReader.getScoreToBeatFromJson() + 100);
        continueReader.setCurrentScore(this, 0);
        continueReader.setRerolls(this, beginningRerolls);
        continueReader.setPlays(this, beginningPlays);
        finish();
    }

    /**
     * resetSelectedDice
     *
     * will move through the selected dice, and will reset
     * them after they have been played.
     */
    public void resetSelectedDice(){
        for (int i = 0; i < sixTextDie.length; i++) {
            if (selectedTextDie[i]){
                rerollDice(sixTextDie[i]);
            }
            selectedTextDie[i] = false;
        }
        result.setText("");
        pipCount.setText("0");
        multCount.setText("0");
        amountSelected = 0;
    }

    /**
     * selectDice
     *
     * allows the user to select and deselect dice. While selecting
     * and deselecting, it will update the text on the left side
     * of the screen to allow users to see what they need to play
     * to win.
     *
     * @param view
     */
    public void selectDice(View view) {
        if (!clickedStart) return;

        TextView clicked = (TextView) view;
        if (clicked == null) return;

        try {
            int whichDie = getDiceIndex(clicked);

            if (selectedTextDie[whichDie]) {
                // Unselect dice
                clicked.setBackgroundResource(
                        getResources().getIdentifier("dice_" + sixValues[whichDie], "drawable", getPackageName()));
                selectedTextDie[whichDie] = false;
                amountSelected -= 1;
            } else {
                // Select dice
                clicked.setBackgroundResource(
                        getResources().getIdentifier("selected_dice_" + sixValues[whichDie], "drawable", getPackageName()));
                selectedTextDie[whichDie] = true;
                amountSelected += 1;
            }
        } catch (Exception e) {
            Log.d("Failure", "Failure in selecting dice");
            clicked.setBackgroundResource(R.drawable.dice_1);
        }

        // Now that the selection state is updated, recalculate the sum
        int sumOfSelected = 0;
        for (int i = 0; i < selectedTextDie.length; i++) {
            if (selectedTextDie[i]) {
                sumOfSelected += sixValues[i];
            }
        }

        PaintingAndScoring paintingAndScoring = new PaintingAndScoring(hands);

        HandType hand = paintingAndScoring.determineHandType(updateDiceArray(),
                paintingAndScoring.getScoring(updateDiceArray()));
        result.setText(hand.getName());
        pipCount.setText(String.valueOf(hand.getPips() + sumOfSelected));
        multCount.setText(String.valueOf(hand.getMult()));
    }

    /**
     * getDiceIndex
     *
     * gets the dice location in the many arrays attributed to them.
     * This simplifies there handling for more efficient uses.
     *
     * @param diceView
     * @return index location of a specific dice
     */
    private int getDiceIndex(TextView diceView) {
        try {
            String id = getResources().getResourceEntryName(diceView.getId());
            return Integer.parseInt(id.replaceAll("\\D+", "")) - 1;
        } catch (Exception e) {
            return 0; // Default to first die
        }
    }

    /**
     * rerollAllDice
     *
     * will be used at the start of a round to reset all the dice to
     * a random pip amount.
     *
     * @param myView
     */
    public void rerollAllDice(View myView){
        if(clickedStart && rerollsLeft > 0){
            boolean anyRerolled = false;
            for (int i = 0; i < sixTextDie.length; i++) {
                if (selectedTextDie[i]){
                    rerollDice(sixTextDie[i]);
                    sixTextDie[i].setBackgroundResource(
                            getResources().getIdentifier("dice_" + sixValues[i], "drawable", getPackageName()));
                    selectedTextDie[i] = false;
                    anyRerolled = true;
                }
            }

            //sets text
            result.setText("");
            pipCount.setText("0");
            multCount.setText("0");

            if (anyRerolled){
                amountSelected = 0;
                rerollsLeft--;
                continueReader.setRerolls(this, rerollsLeft);
                TextView rerollCounter = findViewById(R.id.rerollCounter);
                rerollCounter.setText(String.valueOf(rerollsLeft));
            }
        }
    }

    /**
     * getAllTheDice
     *
     * gets the textviews into one array to make it easier to update
     * them or any other operations.
     *
     * Used in initialization
     *
     * @return the array of the dice textviews in a proper indexing
     */
    private TextView[] getAllTheDice() {
        TextView[] allTheDice = new TextView[6];
        for (int i = 0; i < 6; i++) {
            int id = getResources().getIdentifier("dice" + (i + 1), "id", getPackageName());
            allTheDice[i] = findViewById(id);
        }
        return allTheDice;
    }

    /**
     * updateDiceArray
     *
     * finds all the dice pip values and puts them into a list
     *
     * @return
     */
    private ArrayList<Integer> updateDiceArray() {
        ArrayList<Integer> selectedValues = new ArrayList<>();
        for (int i = 0; i < selectedTextDie.length; i++) {
            if (selectedTextDie[i]) {
                selectedValues.add(sixValues[i]);
            }
        }
        Collections.sort(selectedValues); // Sort in ascending order

        return selectedValues;
    }


    /**
     * selectedPainting
     *
     * will select, deselect, and swap paintings. This will give the user
     * control to how their score is computed.
     *
     * @param view
     */
    public void selectedPainting(View view) {
        TextView clicked = (TextView) view;

        // If already selected, deselect it
        if (clicked.getBackground().getConstantState() ==
                getResources().getDrawable(R.drawable.selected_dash_line).getConstantState()) {

            clicked.setBackgroundResource(R.drawable.dashed_line); // Or set to default background
            paintingsSelected--;
            if (firstSelectedPainting == clicked) {
                firstSelectedPainting = null;
            }
            return;
        }

        // First selection
        if (paintingsSelected == 0) {
            clicked.setBackgroundResource(R.drawable.selected_dash_line);
            firstSelectedPainting = clicked;
            paintingsSelected = 1;
        }
        // Second selection - perform swap
        else if (paintingsSelected == 1) {
            // Get indices of selected paintings
            int firstIndex = getPaintingIndex(firstSelectedPainting);
            int secondIndex = getPaintingIndex(clicked);

            // Swap the painting names/text


            // Update the TextViews


            // Reset selection states
            firstSelectedPainting.setBackgroundResource(R.drawable.dashed_line);
            clicked.setBackgroundResource(R.drawable.dashed_line);
            firstSelectedPainting = null;
            paintingsSelected = 0;
        }
    }

    /**
     * getPaintingIndex
     *
     * same as the dice index function but for one of the four paintings
     *
     * @param paintingView
     * @return the index of a painting in the paintings array
     */
    private int getPaintingIndex(TextView paintingView) {
        int viewId = paintingView.getId(); // Returns the resource ID (e.g., R.id.dice1)
        String idName = getResources().getResourceEntryName(viewId); // "dice1"
        for (int i = 1; i < paintings.length + 1; i++) {
            if (idName.equals("painting" + i)) {
                return i - 1;
            }
        }
        return -1; // Not found
    }
}