package com.rollo;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class AnimationPlayer {

    protected final Animation fadeInAnim;
    protected final Animation fadeOutAnim;
    protected final Animation slideUpAnim;
    protected final Animation slideDownAnim;
    protected final Animation radialBlackoutAnim;
    protected final Animation diceRotationAnim;

    public AnimationPlayer(Context context) {

        // Preload animations
        fadeInAnim = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        fadeOutAnim = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        slideUpAnim = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        slideDownAnim = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        radialBlackoutAnim = AnimationUtils.loadAnimation(context, R.anim.radial_blackout);
        diceRotationAnim = AnimationUtils.loadAnimation(context, R.anim.dice_rotation);
    }//AnimationPlayer

    public Animation fadeIn() {
        return fadeInAnim;
    }//fadeIn

    public Animation fadeOut() {
        return fadeOutAnim;
    }//fadeOut

    public Animation slideUp() {
        return slideUpAnim;
    }//slideUp

    public Animation slideDown() {
        return slideDownAnim;
    }//slideDown

    public Animation radialBlackout() {
        return radialBlackoutAnim;
    }//radialBlackout

    public Animation diceRotate(){
        return diceRotationAnim;
    }//diceRotate
}//AnimationPlayer
