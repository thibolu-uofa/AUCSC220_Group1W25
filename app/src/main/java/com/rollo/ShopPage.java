package com.rollo;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.View;
import android.app.Dialog;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ShopPage extends AppCompatActivity {

    private Continue continueReader;
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

        continueReader = new Continue(this);
        continueReader.copyJsonToInternalStorageIfNeeded(this);
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

        showVoucherPopup();
    }

    private void showVoucherPopup() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_upgrade_selection);

        TextView voucherOption1 = dialog.findViewById(R.id.ChoiceOne);
        TextView voucherOption2 = dialog.findViewById(R.id.ChoiceTwo);
        ImageView checkMark1 = dialog.findViewById(R.id.checkMark1); // Add these ImageViews to your popup layout
        ImageView checkMark2 = dialog.findViewById(R.id.checkMark2);

        voucherOption1.setText("+1▶️");
        voucherOption2.setText("+1🎲");

        // Initially hide check marks
        checkMark1.setVisibility(View.INVISIBLE);
        checkMark2.setVisibility(View.INVISIBLE);

        View.OnClickListener voucherClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView selectedOption = (TextView) v;
                ImageView correspondingCheckMark;
                TextView otherOption;
                ImageView otherCheckMark;

                if (v.getId() == R.id.ChoiceOne) {
                    correspondingCheckMark = checkMark1;
                    otherOption = voucherOption2;
                    otherCheckMark = checkMark2;
                } else {
                    correspondingCheckMark = checkMark2;
                    otherOption = voucherOption1;
                    otherCheckMark = checkMark1;
                }

                // Gray out the selected option
                selectedOption.setAlpha(0.5f);
                selectedOption.setClickable(false);

                // Show check mark
                correspondingCheckMark.setVisibility(View.VISIBLE);

                // Reset the other option if it was previously selected
                otherOption.setAlpha(1f);
                otherOption.setClickable(true);
                otherCheckMark.setVisibility(View.INVISIBLE);

                // Set up check mark click listener
                correspondingCheckMark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Apply the selected upgrade
                        if (selectedOption == voucherOption1) {
                            // Handle +1▶️ upgrade
                            addPlaysUpgrade();
                        } else {
                            // Handle +1🎲 upgrade
                            addRerollUpgrade();
                        }
                        dialog.dismiss();
                    }
                });
            }
        };

        voucherOption1.setOnClickListener(voucherClickListener);
        voucherOption2.setOnClickListener(voucherClickListener);

        dialog.show();
    }

    private void addPlaysUpgrade() {
        continueReader.setPlays(this,continueReader.getPlaysFromJson() + 1);
        nextRound(this);
    }

    private void addRerollUpgrade() {
        continueReader.setRerolls(this, continueReader.getRerollFromJson() + 1);
        nextRound(this);
    }

    private void openShopPage(String layoutName){
        Intent intent = new Intent(ShopPage.this, Shop.class);
        intent.putExtra("layoutName", layoutName);
        startActivity(intent);
    }





    public void nextRound(Context context) {
        Intent intent = new Intent(context, GameField.class);
        context.startActivity(intent);
        finish();
    }
}
