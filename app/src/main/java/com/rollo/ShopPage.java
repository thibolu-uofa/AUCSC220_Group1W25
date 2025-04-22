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

    private TextView firstSelectedPainting = null;
    private int paintingsSelected = 0;
    private String[] paintings = new String[4]; // Your painting names array

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

    public void onCombosClick(View view){
        showComboPopup();
    }

    /**
     * selectedPainting
     *
     * will select, deselect, and swap paintings. This will give the user
     * control to how their score is computed.
     *
     * @param view
     */
    public void selectedPainting(View view) {
        TextView clicked = (TextView) view;

        // If already selected, deselect it
        if (clicked.getBackground().getConstantState() ==
                getResources().getDrawable(R.drawable.selected_dash_line).getConstantState()) {

            clicked.setBackgroundResource(R.drawable.dashed_line); // Or set to default background
            paintingsSelected--;
            if (firstSelectedPainting == clicked) {
                firstSelectedPainting = null;
            }
            return;
        }

        // First selection
        if (paintingsSelected == 0) {
            clicked.setBackgroundResource(R.drawable.selected_dash_line);
            firstSelectedPainting = clicked;
            paintingsSelected = 1;
        }
        // Second selection - perform swap
        else if (paintingsSelected == 1) {
            // Get indices of selected paintings
            int firstIndex = getPaintingIndex(firstSelectedPainting);
            int secondIndex = getPaintingIndex(clicked);

            // Swap the painting names/text


            // Update the TextViews


            // Reset selection states
            firstSelectedPainting.setBackgroundResource(R.drawable.dashed_line);
            clicked.setBackgroundResource(R.drawable.dashed_line);
            firstSelectedPainting = null;
            paintingsSelected = 0;
        }
    }

    /**
     * getPaintingIndex
     *
     * same as the dice index function but for one of the four paintings
     *
     * @param paintingView
     * @return the index of a painting in the paintings array
     */
    private int getPaintingIndex(TextView paintingView) {
        int viewId = paintingView.getId(); // Returns the resource ID (e.g., R.id.dice1)
        String idName = getResources().getResourceEntryName(viewId); // "dice1"
        for (int i = 1; i < paintings.length + 1; i++) {
            if (idName.equals("painting" + i)) {
                return i - 1;
            }
        }
        return -1; // Not found
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

    private void showComboPopup() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_upgrade_selection);

        TextView comboOption1 = dialog.findViewById(R.id.ChoiceOne);
        TextView comboOption2 = dialog.findViewById(R.id.ChoiceTwo);
        ImageView checkMark1 = dialog.findViewById(R.id.checkMark1); // Add these ImageViews to your popup layout
        ImageView checkMark2 = dialog.findViewById(R.id.checkMark2);

        Combo combo = new Combo(new HandTypeManager(continueReader.getHandTypesFromJson()));

        String option1 = combo.selectRandomCombo();
        String option2 = combo.selectRandomCombo();

        comboOption1.setText("+ " + option1);
        comboOption2.setText("+ " + option2);

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
                    otherOption = comboOption2;
                    otherCheckMark = checkMark2;
                } else {
                    correspondingCheckMark = checkMark2;
                    otherOption = comboOption1;
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
                        if (selectedOption == comboOption1) {
                            addComboUpgrade(option1);
                        } else {
                            addComboUpgrade(option2);
                        }
                        dialog.dismiss();
                    }
                });
            }
        };

        comboOption1.setOnClickListener(voucherClickListener);
        comboOption2.setOnClickListener(voucherClickListener);

        dialog.show();
    }

    public void addComboUpgrade(String combo){
        HandTypeManager handTypeManager = new
                HandTypeManager(continueReader.getHandTypesFromJson());
        Combo combo1 = new Combo(handTypeManager);
        handTypeManager.upgradeHand(combo, combo1.upgradeHandType(combo));
        continueReader.setHandtypes(this, handTypeManager.getAllHands());
        nextRound(this);
    }

    private void showPaintingPopup() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_upgrade_selection);

        TextView voucherOption1 = dialog.findViewById(R.id.ChoiceOne);
        TextView voucherOption2 = dialog.findViewById(R.id.ChoiceTwo);
        ImageView checkMark1 = dialog.findViewById(R.id.checkMark1); // Add these ImageViews to your popup layout
        ImageView checkMark2 = dialog.findViewById(R.id.checkMark2);

        PaintingAndScoring paintingAndScoring = new
                PaintingAndScoring(continueReader.getPaintingsFromJson());

        String ChoiceOne = paintingAndScoring.selectRandomPainting();
        String ChoiceTwo = paintingAndScoring.selectRandomPainting();

        voucherOption1.setText("+ " + ChoiceOne);
        voucherOption2.setText("+" + ChoiceTwo);

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

                //Show option text
                TextView optionDialogue = dialog.findViewById(R.id.PaintingDialogue);


                if (v.getId() == R.id.ChoiceOne) {
                    correspondingCheckMark = checkMark1;
                    otherOption = voucherOption2;
                    otherCheckMark = checkMark2;
                    optionDialogue.setText(paintingAndScoring.returnDialogue(ChoiceOne));
                } else {
                    correspondingCheckMark = checkMark2;
                    otherOption = voucherOption1;
                    otherCheckMark = checkMark1;
                    optionDialogue.setText(paintingAndScoring.returnDialogue(ChoiceTwo));
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
