/**
 * The AnimationManager class contains all the animations in the application. It also
 * creates/plays complex animations that can not be handled with simple XML files.
 *
 * @authors - Timi Aina
 * @version - 1.0
 * @date - April 08, 2025
 */

package com.rollo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.util.Random;

public class AnimationManager {

    protected final Animation waveAnim;
    protected final Animation fadeInAnim;
    protected final Animation fadeOutAnim;
    protected final Animation slideUpAnim;
    protected final Animation slideDownAnim;
    protected final Animation radialBlackoutAnim;
    protected final Animation diceRotationAnim;

    /**
     * Constructor to initialize the AnimationManager object.
     * @param context - the application context
     */
    public AnimationManager(Context context) {

        // Preload animations
        waveAnim = AnimationUtils.loadAnimation(context, R.anim.wave);
        fadeInAnim = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        fadeOutAnim = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        slideUpAnim = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        slideDownAnim = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        radialBlackoutAnim = AnimationUtils.loadAnimation(context, R.anim.radial_blackout);
        diceRotationAnim = AnimationUtils.loadAnimation(context, R.anim.dice_rotation);
    }//AnimationPlayer

    /**
     * Starts a blackout animation on a pitch black view for a specified duration.
     *
     * @param blackoutView - the pitch black view the blackout animation utilizes
     * @param mainActivity - the main menu activity
     */
    protected void startBlackoutAnimation(View blackoutView, MainActivity mainActivity, String nextState, GameField gameField) {
        final int ANIM_DURATION = 500;

        blackoutView.setAlpha(0f);
        blackoutView.setVisibility(View.VISIBLE);

        blackoutView.post(() -> {
            int centerX = blackoutView.getWidth() / 2;
            int centerY = blackoutView.getHeight() / 2;

            float startRadius = 0f;
            float endRadius = (float) Math.hypot(centerX, centerY);

            Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                    blackoutView, centerX, centerY, startRadius, endRadius);
            circularReveal.setDuration(ANIM_DURATION);

            circularReveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(@NonNull Animator animation) {}

                @Override
                public void onAnimationEnd(@NonNull Animator animation) {
                    if (nextState.equals("load")) {
                        MainActivity.moveToGame(mainActivity);
                    }
                    else if (nextState.equals("return")) {
                        GameField.openMenuAgain(gameField); // Changed to use gameField context
                    }
                    else if (nextState.equals("new")) {
                        MainActivity.newGame(mainActivity);
                    }
                }

                @Override
                public void onAnimationCancel(@NonNull Animator animation) {}

                @Override
                public void onAnimationRepeat(@NonNull Animator animation) {}
            });

            circularReveal.start();
            blackoutView.animate()
                    .alpha(1f)
                    .setDuration(ANIM_DURATION)
                    .start();
        });
    }

    /**
     * Starts a circular reveal animation on a pitch black view for a specified duration.
     *
     * @param blackoutView - the pitch black view the blackout animation utilizes
     */
    protected void startRevealAnimation(View blackoutView) {
        final int ANIM_DURATION = 500;  // Duration in milliseconds

        // Using .post() ensures that blackoutView has been laid out and its dimensions are available
        blackoutView.post(() -> {
            int centerX = blackoutView.getWidth() / 2;
            int centerY = blackoutView.getHeight() / 2;

            // Start revealing from the center outwardly
            float initRadius = (float) Math.hypot(centerX, centerY);
            float finalRadius = 0f;

            // Create the circular reveal animation
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                    blackoutView, centerX, centerY, initRadius, finalRadius);
            circularReveal.setDuration(ANIM_DURATION);

            circularReveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(@NonNull Animator animation) {
                    blackoutView.setVisibility(View.VISIBLE);
                }//onAnimationStart

                @Override
                public void onAnimationEnd(@NonNull Animator animation) {
                    blackoutView.setVisibility(View.GONE);
                }//onAnimationEnd

                @Override
                public void onAnimationCancel(@NonNull Animator animation) {} //onAnimationCancel

                @Override
                public void onAnimationRepeat(@NonNull Animator animation) {} //onAnimationRepeat
            });

            circularReveal.start();
        });
    }//startRevealAnimation

    /**
     * Animates a dice roll
     */
    public void rollDice(View diceImageView, View blackoutView) {
        // Step 1: Rotate Animation
        ObjectAnimator rotateX = ObjectAnimator.ofFloat(diceImageView, "rotationX", 0f, 455f);
        ObjectAnimator rotateY = ObjectAnimator.ofFloat(diceImageView, "rotationY", 0f, 455f);

        rotateX.setDuration(10000);
        rotateY.setDuration(10000);

        rotateX.setInterpolator(new DecelerateInterpolator());
        rotateY.setInterpolator(new DecelerateInterpolator());

        AnimatorSet rotateSet = new AnimatorSet();
        rotateSet.playTogether(rotateX, rotateY);

        // After rotating, we update the dice face and slide it up
        rotateSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                // Step 2: Update dice face (this is just an example)
                int diceRoll = new Random().nextInt(6) + 1;
                updateDiceFace(diceImageView, diceRoll);

                // Step 3: Slide up animation (from res/anim/slideUpAnim.xml)
                Animation slideUp = AnimationUtils.loadAnimation(diceImageView.getContext(), R.anim.slide_up);
                slideUp.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // Step 4: Start circular reveal
                        startRevealAnimation(blackoutView);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });

                diceImageView.startAnimation(slideUp);
            }
        });

        // Start the rotation animation
        rotateSet.start();
    }

    private void updateDiceFace(View diceImageView, int diceNumber) {
        if (diceImageView instanceof ImageView) {
            int resId = diceImageView.getContext().getResources().getIdentifier(
                    "dice_" + diceNumber, "drawable", diceImageView.getContext().getPackageName());
            ((ImageView) diceImageView).setImageResource(resId);
        }
    }


}//AnimationManager
