package com.rollo;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class ShopPage extends AppCompatActivity {

    private Round round;
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

    public void onUpgradeClick(View view){
        openShopPage("shop_upgrade");
    }

    public void onCombosClick(View view){
        openShopPage("shop_combos");
    }

    public void onPaintingClick(View view){
        openShopPage("shop_painting");
    }

    public void onVouchersClick(View view){
        openShopPage("shop_vouchers");
    }

    private void openShopPage(String layoutName){
        Intent intent = new Intent(ShopPage.this, Shop.class);
        intent.putExtra("layoutName", layoutName);
        startActivity(intent);
    }

    public void onNextRoundClick(View view) {
        round.setNextRound();
        nextRound(this);
    }

    public void nextRound(Context context) {
        Intent intent = new Intent(context, GameField.class);
        context.startActivity(intent);

        finish();
    }
}
