package com.rollo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Shop extends AppCompatActivity {
    private int money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String layoutName = getIntent().getStringExtra("layoutName");

        if (layoutName != null){
            int layoutId = getResources().getIdentifier(layoutName, "layout", getPackageName());
            if (layoutId != 0){
                setContentView(layoutId);
            }
        }
    }
}
