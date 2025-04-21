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
    private int playsLeft;
    private int roundScore;
    private TextView result;
    private TextView pipCount;
    private TextView multCount;
    private TextView threshold;
    private TextView scoreDisplay;
    private Dice[] sixDie;
    private TextView[] sixTextDie;



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
        playsLeft = continueReader.getPlaysFromJson();
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
        result.setText("");
        pipCount.setText("0");
        multCount.setText("0");
    }

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

    public void play(View myView) {
        if (amountSelected >= 1 && amountSelected <= 5 && playsLeft > 0) {
            PaintingAndScoring scored = new PaintingAndScoring(hands);
            HandType hand = scored.scoring(updateDiceArray());
            resetSelectedDice();
            result.setText("");
            playScore = hand.getPips() * hand.getMult();
            roundScore += playScore;
            continueReader.setCurrentScore(this, roundScore);
            scoreDisplay.setText(String.valueOf(roundScore));

            playsLeft--;
            continueReader.setPlays(this, playsLeft);
            TextView handLimitText = findViewById(R.id.handLimitText);
            handLimitText.setText(String.valueOf(playsLeft));

            if (continueReader.getScoreToBeatFromJson() < roundScore){
                continueReader.setHighScore(this);
                openShop(this);
            }

        }

    }

    public static void openMenu(GameField gameField) {
        View blackoutView = gameField.findViewById(R.id.blackoutView);
        AnimationManager animManager = new AnimationManager(gameField);

        // Start the radial reveal animation
        animManager.startBlackoutAnimation(blackoutView, null, "return", gameField);
    }

    public static void openMenuAgain(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);

        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }

    private void openShop(Context context) {
        Intent intent = new Intent(context, ShopPage.class);
        context.startActivity(intent);
        continueReader.setScoreToBeat(this, continueReader.getScoreToBeatFromJson() + 100);
        finish();
    }
    public void resetSelectedDice(){
        for (int i = 0; i < sixTextDie.length; i++) {
            if (selectedTextDie[i]){
                rerollDice(sixTextDie[i]);
            }
            selectedTextDie[i] = false;
        }
        amountSelected = 0;
    }

    public void selectDice(View view) {
        if (clickedStart) {
            TextView clicked = (TextView) view;
            if (clicked == null) return;

            try {
                int whichDie = getDiceIndex(clicked);

                if (selectedTextDie[whichDie]) {
                    // Switch to regular dice
                    clicked.setBackgroundResource(
                            getResources().getIdentifier("dice_" + sixValues[whichDie], "drawable", getPackageName()));
                    selectedTextDie[whichDie] = false;
                    amountSelected -= 1;  // Decrease selected count
                }
                else {
                    // Switch to selected dice
                    clicked.setBackgroundResource(
                            getResources().getIdentifier("selected_dice_" + sixValues[whichDie], "drawable", getPackageName()));
                    selectedTextDie[whichDie] = true;
                    amountSelected += 1;  // Increase selected count
                }

            }
            catch (Exception e) {
                Log.d("Failure","Failure in selecting dice");
                clicked.setBackgroundResource(R.drawable.dice_1);
            }

            //Should be its own method
            PaintingAndScoring paintingAndScoring = new PaintingAndScoring(hands);
            //should be its own method
            HandType hand = paintingAndScoring.determineHandType(updateDiceArray(),
                    paintingAndScoring.getScoring(updateDiceArray()));
            result.setText(hand.getName());
            pipCount.setText(String.valueOf(hand.getPips()));
            multCount.setText(String.valueOf(hand.getMult()));
        }
    }

    private int getDiceIndex(TextView diceView) {
        try {
            String id = getResources().getResourceEntryName(diceView.getId());
            return Integer.parseInt(id.replaceAll("\\D+", "")) - 1;
        } catch (Exception e) {
            return 0; // Default to first die
        }
    }

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

            if (anyRerolled){
                amountSelected = 0;
                rerollsLeft--;
                continueReader.setRerolls(this, rerollsLeft);
                TextView rerollCounter = findViewById(R.id.rerollCounter);
                rerollCounter.setText(String.valueOf(rerollsLeft));
            }
        }
    }

    private TextView[] getAllTheDice() {
        TextView[] allTheDice = new TextView[6];
        for (int i = 0; i < 6; i++) {
            int id = getResources().getIdentifier("dice" + (i + 1), "id", getPackageName());
            allTheDice[i] = findViewById(id);
        }
        return allTheDice;
    }

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

    // Class level variables to track selections


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

    private int getPaintingIndex(TextView paintingView) {
        int viewId = paintingView.getId(); // Returns the resource ID (e.g., R.id.dice1)
        String idName = getResources().getResourceEntryName(viewId); // "dice1"
        for (int i = 1; i < paintings.length + 1; i++) {
            if (idName.equals("painting" + i)) {
                return i;
            }
        }
        return -1; // Not found
    }
}