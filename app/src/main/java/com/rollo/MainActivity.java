/**
 * The Main Activity class handles all AppCompatActivity related tasks while initializing UI,
 * animations, and transitions to the game screen.
 *
 * @authors - Timi Aina, Jesse Maeko, & Brett Siemens
 * @version - 1.0
 * @date - April 08, 2025
 */

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

    /**
     * Performs essential setup tasks when the activity is created.
     *
     * @param savedInstanceState - holds the last saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        init(); // Initialize menu components


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        new EventListener(this);
    }

    /**
     * Initializes the main components (transitions, animations, background music) for the main menu
     * activity.
     */
    private void init() {
        EventListener listener = new EventListener(this);
        AnimationManager animManager = new AnimationManager(this);
        //listener.menuTransition();

        // Start menu background music playing and looping
        mp = MediaPlayer.create(this, R.raw.hopeful);
        mp.start();
        mp.setLooping(true);

        ImageView menuBackground = findViewById(R.id.background);
        menuBackground.startAnimation(animManager.waveAnim);
    }//init

    public static void newGame(Context context){
        Continue obj = new Continue(context);
        obj.copyJsonToInternalStorageIfNeeded(context);

        obj.resetToDefaults(context);
        moveToGame(context);
    }//newGame

    /**
     * Moves the user from the main menu activity to the game activity.
     *
     * @param context - the application context
     */
    protected static void moveToGame(Context context) {
        Intent intent = new Intent(context, GameField.class);
        context.startActivity(intent);

        if (context instanceof Activity) {
            ((Activity) context).finish(); // Close current activity
        }//if statement
    }//moveToGame
}