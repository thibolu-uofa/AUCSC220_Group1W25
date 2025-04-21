/**
 * The TouchColorButton class represents a custom button that changes its text color on touch
 * events to provide interactive feedback within the UI.
 *
 * @authors - Timi Aina
 * @version - 1.0
 * @date - April 08, 2025
 */

package com.rollo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

public class TouchColorButton extends AppCompatButton {

    private final int pressedTextColor = R.color.black;
    private final int normalTextColor = R.color.golden_white;

    /**
     * Constructor to initialize the TouchColorButton object.
     * @param context - the application context
     */
    public TouchColorButton(Context context) {
        super(context);
        init();
    }//TouchColorButton

    /**
     * Constructor to initialize the TouchColorButton object with XML attributes.
     * @param context - the application context
     * @param attrs - the XML attribute set
     */
    public TouchColorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }//TouchColorButton

    /**
     * Constructor to initialize the TouchColorButton object with XML attributes and a style.
     * @param context - the application context
     * @param attrs - the XML attribute set
     * @param defStyleAttr - the style attribute
     */
    public TouchColorButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }//TouchColorButton

    /**
     * Initializes the touch event to change text colours.
     */
    private void init() {
        setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    setTextColor(ContextCompat.getColor(getContext(), pressedTextColor));
                    break;

                case MotionEvent.ACTION_UP:
                    setTextColor(ContextCompat.getColor(getContext(), normalTextColor));

                    float x = event.getX();
                    float y = event.getY();
                    if (x >= 0 && x <= v.getWidth() && y >= 0 && y <= v.getHeight()) {
                        performClick();
                    }//if-statement
                    break;

                case MotionEvent.ACTION_CANCEL:
                    setTextColor(ContextCompat.getColor(getContext(), normalTextColor));
                    break;
            }//switch
            return true;
        });
    }//init

    /**
     * Overrides the performClick() function to prevent any warnings for supporting accessibility.
     */
    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }//performClick

}//TouchColorButton

