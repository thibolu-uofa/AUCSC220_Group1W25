package com.rollo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

public class TouchColorButton extends AppCompatButton {

    private final int pressedTextColor = R.color.black;
    private final int normalTextColor = R.color.golden_white;

    public TouchColorButton(Context context) {
        super(context);
        init();
    }//TouchColorButton

    public TouchColorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }//TouchColorButton

    public TouchColorButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }//TouchColorButton

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
                    }
                    break;

                case MotionEvent.ACTION_CANCEL:
                    setTextColor(ContextCompat.getColor(getContext(), normalTextColor));
                    break;
            }
            return true;
        });
    }//init

    @Override
    // Prevents any warnings for supporting accessibility
    public boolean performClick() {
        super.performClick();
        return true;
    }//performClick

}//TouchColorButton

