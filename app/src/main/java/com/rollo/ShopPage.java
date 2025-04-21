package com.rollo;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.app.Dialog;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ShopPage extends AppCompatActivity {

    private Continue continueReader;
    private String pendingVoucherType = "";
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

        ImageView voucherOption1 = dialog.findViewById(R.id.imageView11);
        ImageView voucherOption2 = dialog.findViewById(R.id.imageView13);
        Button confirmButton = dialog.findViewById(R.id.button);

        final String[] selectedVoucherType = new String[]{""};

        voucherOption1.setImageResource(R.drawable.dice_frame);
        voucherOption2.setImageResource(R.drawable.dice_frame);

        voucherOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedVoucherType[0] = "plays";
                voucherOption1.setAlpha(1f);
                voucherOption2.setAlpha(0.5f);
            }
        });
        voucherOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedVoucherType[0] = "rerolls";
                voucherOption1.setAlpha(0.5f);
                voucherOption2.setAlpha(1f);
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedVoucherType[0].isEmpty()) {
                    Voucher voucher = new Voucher();

                    if ("plays".equals(selectedVoucherType[0])) {
                        continueReader.setVoucherType(ShopPage.this, "plays");
                    } else if ("rerolls".equals(selectedVoucherType[0])) {
                        continueReader.setVoucherType(ShopPage.this, "rerolls");
                    }


                    Toast.makeText(ShopPage.this, "Voucher selected! Press 'Next Round' to continue.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(ShopPage.this, "Please select a voucher first.", Toast.LENGTH_SHORT).show();
                }
            }
        });



        dialog.show();
    }

    private void openShopPage(String layoutName){
        Intent intent = new Intent(ShopPage.this, Shop.class);
        intent.putExtra("layoutName", layoutName);
        startActivity(intent);
    }

    public void onNextRoundClick(View view) {

        String voucher = continueReader.getVoucherType(this);

        int newPlays = 5;
        int newRerolls = 4;
        /*
        if (voucher.equals("plays")) {
            newPlays += 1;
        } else if (voucher.equals("rerolls")) {
            newRerolls += 1;
        }
        */
        continueReader.setPlays(this, newPlays);
        continueReader.setRerolls(this, newRerolls);

        nextRound(this);  // this now clears the voucher inside


    }



    public void nextRound(Context context) {
        String voucher = continueReader.getVoucherType(context);
        continueReader.setCurrentScore(context, 0);

        Intent intent = new Intent(context, GameField.class);
        if (!voucher.isEmpty()) {
            intent.putExtra("voucherType", voucher);
        }
        context.startActivity(intent);

        continueReader.clearVoucherType(context);
        finish();
    }
}
