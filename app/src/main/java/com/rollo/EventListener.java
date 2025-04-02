package com.rollo;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.core.content.ContextCompat;

public class EventListener {

    public EventListener(Activity activity) {
        // Initialize buttons by finding them in the activity layout
        Button loadGameButton = activity.findViewById(R.id.loadGameButton);
        Button newGameButton = activity.findViewById(R.id.newGameButton);
        Button tutorialButton = activity.findViewById(R.id.tutorialButton);
        Button creditsButton = activity.findViewById(R.id.creditsButton);

        // Initialize main menu screen views
        View tutorialBackground = activity.findViewById(R.id.tutorialBackground);
        View creditsBackground = activity.findViewById(R.id.creditsBackground);
        Button creditsBackButton = activity.findViewById(R.id.creditsBackButton);

        // Set click/tap listeners for main menu buttons
        loadGameButton.setOnClickListener(v -> {});
        newGameButton.setOnClickListener(v -> {});
        tutorialButton.setOnClickListener(v -> {
            tutorialBackground.setVisibility(View.VISIBLE);
        });

        creditsButton.setOnClickListener(v -> {
            creditsBackground.setVisibility(View.VISIBLE);
            creditsBackButton.setVisibility(View.VISIBLE);
        });

        creditsBackButton.setOnClickListener(v->{
            creditsBackground.setVisibility(View.GONE);
            creditsBackButton.setVisibility(View.GONE);
        });
    }
}

