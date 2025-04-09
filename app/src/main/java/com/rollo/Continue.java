package com.rollo;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Continue {
    private final Context context;
    private static final String FILENAME = "userdata.json";

    public Continue(Context context) {
        this.context = context;
        InputStream inputStream = context.getResources().openRawResource(R.raw.userdata);
    }


    public List<HandType> getHandTypesFromJson() {
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.userdata);
            InputStreamReader reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            UserData userData = gson.fromJson(reader, UserData.class);
            reader.close();

            return userData.getHandTypes();
        } catch (Exception e) {
            Log.e("Continue", "Error reading JSON", e);
            return null;
        }
    }

    public Dice[] getDiceFromJson() {
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.userdata);
            InputStreamReader reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            UserData userData = gson.fromJson(reader, UserData.class);
            reader.close();

            return userData.getDice();
        } catch (Exception e) {
            Log.e("Continue", "Error reading JSON", e);
            return null;
        }
    }

    public int getRerollFromJson() {
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.userdata);
            InputStreamReader reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            UserData userData = gson.fromJson(reader, UserData.class);
            reader.close();

            return userData.gameState.getRerolls();
        } catch (Exception e) {
            Log.e("Continue", "Error reading JSON", e);
            return 0;
        }
    }

    public void upgradeRerolls(Context context) {
        try {
            File file = new File(context.getFilesDir(), FILENAME);
            Gson gson = new Gson();

            // Read the current userdata
            UserData data;
            try (FileReader reader = new FileReader(file)) {
                data = gson.fromJson(reader, UserData.class);
            }

            // Update the rerolls
            if (data != null && data.getGameState() != null) {
                int currentRerolls = data.getGameState().rerolls;
                data.getGameState().rerolls = currentRerolls + 1;

                // Write it back
                try (FileWriter writer = new FileWriter(file)) {
                    gson.toJson(data, writer);
                    Log.d("CONTINUE", "Rerolls upgraded to: " + data.getGameState().rerolls);
                }
            }

        } catch (Exception e) {
            Log.e("CONTINUE", "Failed to update rerolls", e);
        }
    }

    public void upgradePlays(Context context) {
        try {
            File file = new File(context.getFilesDir(), FILENAME);
            Gson gson = new Gson();

            // Read the current userdata
            UserData data;
            try (FileReader reader = new FileReader(file)) {
                data = gson.fromJson(reader, UserData.class);
            }

            // Update the rerolls
            if (data != null && data.getGameState() != null) {
                int currentRerolls = data.getGameState().plays;
                data.getGameState().plays = currentRerolls + 1;

                // Write it back
                try (FileWriter writer = new FileWriter(file)) {
                    gson.toJson(data, writer);
                    Log.d("CONTINUE", "Rerolls upgraded to: " + data.getGameState().rerolls);
                }
            }

        } catch (Exception e) {
            Log.e("CONTINUE", "Failed to update rerolls", e);
        }
    }

    public void resetToDefaults(Context context) {
        File file = new File(context.getFilesDir(), FILENAME);

        if (file.exists()) {
            boolean deleted = file.delete();
            if (!deleted) {
                Log.e("RESET", "Failed to delete existing userdata file.");
                return;
            }
        }

        try (InputStream inputStream = context.getResources().openRawResource(R.raw.userdata);
             FileOutputStream outputStream = new FileOutputStream(file)) {

            byte[] buffer = new byte[1024];
            int length;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            Log.d("RESET", "User data reset to default.");

        } catch (Exception e) {
            Log.e("RESET", "Failed to reset user data.", e);
        }
    }

}
