package com.rollo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import java.util.HashMap;

import java.util.ArrayList;
import java.util.Collections;


public class GameField extends AppCompatActivity {
    private ImageView imageView1, imageView2;
    private boolean clickedStart = false;
    private int imageWidth;
    private TextView result;

    private Dice[] sixDie;
    private TextView[] sixTextDie;

    private HandTypeManager hands;

    private int amountSelected = 0;
    private int[] sixValues = new int[]{0,0,0,0,0,0};
    private boolean[] selectedTextDie = new boolean[]{false, false, false,
            false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_field);
        result = findViewById(R.id.result);
        hands = new HandTypeManager();
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView1.post(() -> {
            imageWidth = imageView1.getWidth();
            imageView1.setX(0);
            imageView2.setX(imageWidth);
            startScrolling();
        });

        sixDie = new Dice[]{new Dice(), new Dice(), new Dice(),
                new Dice(), new Dice(), new Dice()};

        sixTextDie = getAllTheDice();

        for (int i = 0; i < sixTextDie.length; i++) {
            sixTextDie[i].setBackgroundResource(R.drawable.dice_1);
        }
    }

    private void startScrolling() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, -imageWidth);
        animator.setDuration(15000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            imageView1.setX(value);
            imageView2.setX(value + imageWidth);
            if (value <= -imageWidth) {
                imageView1.setX(imageView2.getX() + imageWidth);
                ImageView temp = imageView1;
                imageView1 = imageView2;
                imageView2 = temp;
            }
        });
        animator.start();
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
    }

    public void play(View myView) {
        resetSelectedDice();
        result.setText("");
    }

    private HandType determineHandType(ArrayList<Integer> selectedValues, HashMap<Integer, Integer> scoring) {
        ArrayList<Integer> frequencies = new ArrayList<>(scoring.values());
        Collections.sort(frequencies, Collections.reverseOrder());

        System.out.println("Selected values: " + selectedValues);
        System.out.println("Frequencies: " + frequencies);

        if (frequencies.get(0) == 5) {
            return hands.getHandByName("Yahtzee");
        }

        else if (frequencies.get(0) == 4) {
            return hands.getHandByName("Four of a Kind");
        }

        else if (frequencies.get(0) == 3 && frequencies.get(1) == 2) {
            return hands.getHandByName("Full House");
        }

        else if (frequencies.get(0) == 3) {
            return hands.getHandByName("Three of a Kind");
        }

        else if (frequencies.get(0) == 2 && frequencies.get(1) == 2) {
            return hands.getHandByName("Two Pair");
        }

        else if (frequencies.get(0) == 2) {
            return hands.getHandByName("Pair");
        }

        else {
            return hands.getHandByName("High Die");
        }
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
                System.out.println("Selection error: " + e.getMessage());
                clicked.setBackgroundResource(R.drawable.dice_1);
            }

            HashMap<Integer, Integer> scoring = new HashMap<>();
            ArrayList<Integer> selectedValues = updateDiceArray();
            for (int i = 1; i <= 6; i++) scoring.put(i, 0);
            for (int i = 0; i < selectedValues.size(); i++) {
                if (scoring.containsKey(selectedValues.get(i))) {
                    scoring.replace(selectedValues.get(i), scoring.get(selectedValues.get(i)) + 1);
                }
            }
            HandType hand = determineHandType(selectedValues, scoring);
            result.setText(hand.getName());
            Log.d("Gay","Selected Dice Values: " + selectedValues);
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
        if(clickedStart){
            for (int i = 0; i < sixTextDie.length; i++) {
                if (selectedTextDie[i]){
                    rerollDice(sixTextDie[i]);
                    sixTextDie[i].setBackgroundResource(
                            getResources().getIdentifier("dice_" + sixValues[i], "drawable", getPackageName()));
                    selectedTextDie[i] = false;
                    amountSelected = 0;
                }
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
}