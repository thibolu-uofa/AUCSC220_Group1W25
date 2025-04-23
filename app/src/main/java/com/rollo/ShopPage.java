package com.rollo;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    private Dialog comboDialog;
    private boolean isComboDialogShowing = false;

    private String comboOption1;
    private String comboOption2;

    // Add these as class member variables
    private String selectedPainting1;
    private String selectedPainting2;
    private boolean isPaintingDialogShowing = false;
    private Dialog paintingDialog;

    private Dialog voucherDialog;
    private boolean isVoucherDialogShowing = false;

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
    public void onCombosClick(View view){
        showComboPopup();
    }
    public void onPaintingClick(View view){
        showPaintingPopup();
    }

    public void onVouchersClick(View view){
        showVoucherPopup();
    }

    private void showVoucherPopup() {
        if (isVoucherDialogShowing) {
            return;
        }

        voucherDialog = new Dialog(this);
        voucherDialog.setContentView(R.layout.popup_upgrade_selection);

        // Allow dismissing when clicking outside
        voucherDialog.setCanceledOnTouchOutside(true);

        // Handle dialog dismissal
        voucherDialog.setOnDismissListener(dialog -> {
            isVoucherDialogShowing = false;
        });

        TextView voucherOption1 = voucherDialog.findViewById(R.id.ChoiceOne);
        TextView voucherOption2 = voucherDialog.findViewById(R.id.ChoiceTwo);
        ImageView checkMark1 = voucherDialog.findViewById(R.id.checkMark1); // Add these ImageViews to your popup layout
        ImageView checkMark2 = voucherDialog.findViewById(R.id.checkMark2);

        voucherOption1.setText("+1▶️");
        voucherOption2.setText("+1🎲");

        // Initially hide check marks
        checkMark1.setVisibility(View.INVISIBLE);
        checkMark2.setVisibility(View.INVISIBLE);
        voucherOption1.setAlpha(1f);
        voucherOption1.setClickable(true);
        voucherOption2.setAlpha(1f);
        voucherOption2.setClickable(true);

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
                        voucherDialog.dismiss();
                    }
                });
            }
        };

        voucherOption1.setOnClickListener(voucherClickListener);
        voucherOption2.setOnClickListener(voucherClickListener);

        voucherDialog.show();
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
        // Return if dialog is already showing
        if (isComboDialogShowing) {
            return;
        }

        comboDialog = new Dialog(this);
        comboDialog.setContentView(R.layout.popup_upgrade_selection);

        // Allow dismissing when clicking outside
        comboDialog.setCanceledOnTouchOutside(true);

        // Handle dialog dismissal
        comboDialog.setOnDismissListener(dialog -> {
            isComboDialogShowing = false;
        });

        TextView ComboOption1 = comboDialog.findViewById(R.id.ChoiceOne);
        TextView ComboOption2 = comboDialog.findViewById(R.id.ChoiceTwo);
        ImageView checkMark1 = comboDialog.findViewById(R.id.checkMark1);
        ImageView checkMark2 = comboDialog.findViewById(R.id.checkMark2);

        Combo combo = new Combo(new HandTypeManager(continueReader.getHandTypesFromJson()));

        if(comboOption1 == null && comboOption2 == null){
            comboOption1 = combo.selectRandomCombo();
            comboOption2 = combo.selectRandomCombo();
        }


        ComboOption1.setText("+ " + comboOption1);
        ComboOption2.setText("+ " + comboOption2);
        ComboOption1.setTextSize(15);
        ComboOption2.setTextSize(15);

        // Initially hide check marks and reset states
        checkMark1.setVisibility(View.INVISIBLE);
        checkMark2.setVisibility(View.INVISIBLE);
        ComboOption1.setAlpha(1f);
        ComboOption1.setClickable(true);
        ComboOption2.setAlpha(1f);
        ComboOption2.setClickable(true);

        View.OnClickListener voucherClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView selectedOption = (TextView) v;
                ImageView correspondingCheckMark;
                TextView otherOption;
                ImageView otherCheckMark;

                if (v.getId() == R.id.ChoiceOne) {
                    correspondingCheckMark = checkMark1;
                    otherOption = ComboOption2;
                    otherCheckMark = checkMark2;
                } else {
                    correspondingCheckMark = checkMark2;
                    otherOption = ComboOption1;
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
                        if (selectedOption == ComboOption1) {
                            addComboUpgrade(comboOption1);
                        } else {
                            addComboUpgrade(comboOption2);
                        }
                        comboDialog.dismiss();
                    }
                });
            }
        };

        ComboOption1.setOnClickListener(voucherClickListener);
        ComboOption2.setOnClickListener(voucherClickListener);

        isComboDialogShowing = true;
        comboDialog.show();
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
        if (isPaintingDialogShowing) {
            return;
        }

        paintingDialog = new Dialog(this);
        paintingDialog.setContentView(R.layout.popup_upgrade_selection);
        paintingDialog.setCanceledOnTouchOutside(true);
        paintingDialog.setOnDismissListener(dialog -> {
            isPaintingDialogShowing = false;
            selectedPainting1 = null;  // Reset selections when dismissed
            selectedPainting2 = null;
        });

        TextView paintingOption1View = paintingDialog.findViewById(R.id.ChoiceOne);
        TextView paintingOption2View = paintingDialog.findViewById(R.id.ChoiceTwo);
        ImageView checkMark1 = paintingDialog.findViewById(R.id.checkMark1);
        ImageView checkMark2 = paintingDialog.findViewById(R.id.checkMark2);
        TextView optionDialogue = paintingDialog.findViewById(R.id.PaintingDialogue);

        PaintingAndScoring paintingAndScoring = new PaintingAndScoring(continueReader.getPaintingsFromJson());

        // Always get new random paintings when popup opens
        selectedPainting1 = paintingAndScoring.selectRandomPainting();
        selectedPainting2 = paintingAndScoring.selectRandomPainting();

        // Ensure we don't show the same painting twice
        while (selectedPainting2.equals(selectedPainting1)) {
            selectedPainting2 = paintingAndScoring.selectRandomPainting();
        }

        paintingOption1View.setText("+ " + selectedPainting1);
        paintingOption2View.setText("+ " + selectedPainting2);


        // Reset UI state
        checkMark1.setVisibility(View.INVISIBLE);
        checkMark2.setVisibility(View.INVISIBLE);
        paintingOption1View.setAlpha(1f);
        paintingOption1View.setClickable(true);
        paintingOption2View.setAlpha(1f);
        paintingOption2View.setClickable(true);
        optionDialogue.setText("Select a painting upgrade");

        View.OnClickListener paintingClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView selectedOption = (TextView) v;
                ImageView correspondingCheckMark;
                TextView otherOption;
                ImageView otherCheckMark;

                if (v.getId() == R.id.ChoiceOne) {
                    correspondingCheckMark = checkMark1;
                    otherOption = paintingOption2View;
                    otherCheckMark = checkMark2;
                    optionDialogue.setText(paintingAndScoring.returnDialogue(selectedPainting1));
                } else {
                    correspondingCheckMark = checkMark2;
                    otherOption = paintingOption1View;
                    otherCheckMark = checkMark1;
                    optionDialogue.setText(paintingAndScoring.returnDialogue(selectedPainting2));
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
                        if (selectedOption == paintingOption1View) {
                            addPaintingUpgrade(selectedPainting1);
                        } else {
                            addPaintingUpgrade(selectedPainting2);
                        }
                        comboDialog.dismiss();
                    }
                });
            }
        };
        paintingOption1View.setOnClickListener(paintingClickListener);
        paintingOption2View.setOnClickListener(paintingClickListener);

        isPaintingDialogShowing = true;
        paintingDialog.show();
    }

    public void addPaintingUpgrade(String newPainting){
        HandTypeManager handTypeManager = new
                HandTypeManager(continueReader.getHandTypesFromJson());
        PaintingAndScoring paintingAndScoring = new PaintingAndScoring(handTypeManager,
                continueReader.getPaintingsFromJson());

        continueReader.setPaintings(this,
                paintingAndScoring.updatePaintingHand(newPainting));

        nextRound(this);
    }


    public void nextRound(Context context) {
        Intent intent = new Intent(context, GameField.class);
        context.startActivity(intent);
        finish();
    }
}
