package com.rollo;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class ShopPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        ImageView backgroundImage = findViewById(R.id.imageView);

        ObjectAnimator animator = ObjectAnimator.ofFloat(backgroundImage, "translationX", 0f, -1000f);
        animator.setDuration(5000);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.start();
    }

}
