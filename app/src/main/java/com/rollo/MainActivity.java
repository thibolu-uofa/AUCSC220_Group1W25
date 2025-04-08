package com.rollo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.media.MediaPlayer;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mp = MediaPlayer.create(this, R.raw.hopeful);
        mp.start();
        mp.setLooping(true);

        //Gets the background From image view
        ImageView gameBackground = findViewById(R.id.background);

        Animation waveAnimation = AnimationUtils.loadAnimation(this, R.anim.wave);

        gameBackground.startAnimation(waveAnimation);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        new EventListener(this);

        Continue obj = new Continue(this);

    }


    protected static void moveToGame(Context context) {
        Intent intent = new Intent(context, GameField.class);
        context.startActivity(intent);

        if (context instanceof Activity) {
            ((Activity) context).finish(); // Close current activity
        }//if statement
    }//moveToGame
}