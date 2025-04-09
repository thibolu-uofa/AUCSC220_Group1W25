package com.rollo;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Continue {
    private final Context context;

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

    public int getScoreToBeatFromJson() {
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.userdata);
            InputStreamReader reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            UserData userData = gson.fromJson(reader, UserData.class);
            reader.close();

            return userData.gameState.getScoreToBeat();
        } catch (Exception e) {
            Log.e("Continue", "Error reading JSON", e);
            return 0;
        }
    }
    public int getCurrentScoreFromJson() {
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.userdata);
            InputStreamReader reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            UserData userData = gson.fromJson(reader, UserData.class);
            reader.close();

            return userData.gameState.getCurrentScore();
        } catch (Exception e) {
            Log.e("Continue", "Error reading JSON", e);
            return 0;
        }
    }

    public int getHandsFromJson() {
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.userdata);
            InputStreamReader reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            UserData userData = gson.fromJson(reader, UserData.class);
            reader.close();

            return userData.gameState.getPlays();
        } catch (Exception e) {
            Log.e("Continue", "Error reading JSON", e);
            return 0;
        }
    }

    public int getPlaysFromJson() {
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.userdata);
            InputStreamReader reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            UserData userData = gson.fromJson(reader, UserData.class);
            reader.close();

            return userData.gameState.getPlays();
        } catch (Exception e) {
            Log.e("Continue", "Error reading JSON", e);
            return 0;
        }
    }

    public void setCurrentScore(Context context, int amount) {
        try {
            File file = new File(context.getFilesDir(), "userdata.json");
            Gson gson = new Gson();

            // Read the current userdata
            UserData data;
            try (FileReader reader = new FileReader(file)) {
                data = gson.fromJson(reader, UserData.class);
            }

            // Update the rerolls
            if (data != null && data.getGameState() != null) {
                int currentscore = data.getGameState().getCurrentScore();
                data.getGameState().setCurrentScore(currentscore + amount);

                // Write it back
                try (FileWriter writer = new FileWriter(file)) {
                    gson.toJson(data, writer);
                    Log.d("CONTINUE", "Rerolls upgraded to: " + data.getGameState().getCurrentScore());
                }
            }

        } catch (Exception e) {
            Log.e("CONTINUE", "Failed to update rerolls", e);
        }
    }

    public void setCurrentRound(Context context, int amount) {
        try {
            File file = new File(context.getFilesDir(), "userdata.json");
            Gson gson = new Gson();

            // Read the current userdata
            UserData data;
            try (FileReader reader = new FileReader(file)) {
                data = gson.fromJson(reader, UserData.class);
            }

            // Update the score
            if (data != null && data.getGameState() != null) {
                int currentround = data.getGameState().getCurrentScore();
                data.getGameState().setCurrentScore(currentround + amount);

                // Write it back
                try (FileWriter writer = new FileWriter(file)) {
                    gson.toJson(data, writer);
                    Log.d("CONTINUE", "Rerolls upgraded to: " + data.getGameState().getCurrentScore());
                }
            }

        } catch (Exception e) {
            Log.e("CONTINUE", "Failed to update rerolls", e);
        }
    }

    public void resetToDefaults(Context context) {
        File file = new File(context.getFilesDir(), "userdata.json");

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
