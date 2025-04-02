package com.rollo;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

public class GameField extends AppCompatActivity {
    private ImageView imageView1, imageView2;
    private int imageWidth;

    private Dice[] sixDie;
    private TextView[] sixTextDie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_field);
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

    /**
     * This is for a dice click
     * @param myView
     */
    public void rerollDice(View myView){
        int newSide;
        int whichDie;
        int resourceId;
        String id;
        TextView clicked = (TextView) myView;
        id = getResources().getResourceEntryName(myView.getId());
        whichDie = Integer.parseInt(id.replaceAll("\\D+", ""));
        newSide = sixDie[whichDie - 1].getRandomSide();
        resourceId = getResources().getIdentifier("dice_" + newSide, "drawable", getPackageName());
        clicked.setBackgroundResource(resourceId);
    }

    private TextView[] getAllTheDice(){
        TextView[] allTheDice = new TextView[6];
        int id;
        for (int i = 0; i < 6; i++) {
            id = getResources().getIdentifier("Dice" + i , "id", getPackageName());
            allTheDice[i] = findViewById(id);
        }
        return allTheDice;
    }
}
