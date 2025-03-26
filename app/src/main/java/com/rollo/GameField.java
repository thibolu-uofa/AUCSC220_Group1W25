package com.rollo;


import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

public class GameField extends AppCompatActivity {

    private ImageView imageView1, imageView2;
    private int screenWidth;
    private int imageWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_field);

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);

        imageView1.post(() -> {
            screenWidth = getResources().getDisplayMetrics().widthPixels;
            imageWidth = imageView1.getWidth();

            // Ensure both images are positioned correctly at the start
            imageView1.setX(0);
            imageView2.setX(imageWidth);

            startScrolling();
        });
    }

    private void startScrolling() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, -imageWidth);
        animator.setDuration(15000); // Adjust speed
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());

        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();

            // Move both images
            imageView1.setX(value);
            imageView2.setX(value + imageWidth);

            // When imageView1 moves completely off-screen, reposition it after imageView2
            if (value <= -imageWidth) {
                imageView1.setX(imageView2.getX() + imageWidth);
                // Swap references so imageView2 is now moving off-screen
                ImageView temp = imageView1;
                imageView1 = imageView2;
                imageView2 = temp;
            }
        });

        animator.start();
    }
}
