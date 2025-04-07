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

    private int amountSelected = 0;
    private int[] sixValues = new int[]{0,0,0,0,0,0};
    private boolean[] selectedTextDie = new boolean[]{false, false, false,
            false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_field);
        result = findViewById(R.id.result);
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

            // Update both tag and background
            

            // Use the correct background based on selection state
            int resId = getResources().getIdentifier("dice_" + newSide, "drawable", getPackageName());

            clicked.setBackgroundResource(resId);
        } catch (Exception e) {
            Log.e("Dice", "Reroll error", e);
            clicked.setBackgroundResource(R.drawable.dice_1);
        }
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

    public void play(View myView){
        if (amountSelected <= 5){
            HashMap<Integer, Integer> scoring = new HashMap<Integer, Integer>();
            scoring.put(1,0);
            scoring.put(2,0);
            scoring.put(3,0);
            scoring.put(4,0);
            scoring.put(5,0);
            scoring.put(6,0);
            ArrayList<Integer> sortedArray = updateDiceArray();
            for (int i = 0; i < sortedArray.size(); i++) {
                if(scoring.containsKey(sortedArray.get(i))){
                    Log.d("Gay", "Got Here");
                    scoring.replace(sortedArray.get(i), scoring.get(sortedArray.get(i)) + 1);
                }
            }
            resetSelectedDice();
        }
    }

    public void resetSelectedDice(){
        for (int i = 0; i < sixTextDie.length; i++) {
            if (selectedTextDie[i]){
                rerollDice(sixTextDie[i]);
            }
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
                }
                else {
                    // Switch to selected dice
                    clicked.setBackgroundResource(
                            getResources().getIdentifier("selected_dice_" + sixValues[whichDie], "drawable", getPackageName()));
                    selectedTextDie[whichDie] = true;
                    amountSelected += 1;
                }
            }
            catch (Exception e) {
                System.out.println("Selection error: " + e.getMessage());
                clicked.setBackgroundResource(R.drawable.dice_1);
            }
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