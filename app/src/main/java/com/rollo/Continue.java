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

    public void copyJsonToInternalStorageIfNeeded(Context context) {
        File file = new File(context.getFilesDir(), "userdata.json");
        if (!file.exists()) {
            try (InputStream inputStream = context.getResources().openRawResource(R.raw.userdata);
                 FileOutputStream outputStream = new FileOutputStream(file)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            } catch (Exception e) {
                Log.e("FILE_COPY", "Failed to copy JSON", e);
            }
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
            File file = new File(context.getFilesDir(), FILENAME);
            Gson gson = new Gson();

            // Read the current userdata
            UserData data;
            try (FileReader reader = new FileReader(file)) {
                data = gson.fromJson(reader, UserData.class);
            }

            // Update the currentScore
            if (data != null && data.getGameState() != null) {
                data.getGameState().setCurrentScore(amount);

                // Write it back
                try (FileWriter writer = new FileWriter(file)) {
                    gson.toJson(data, writer);
                    Log.d("CONTINUE", "Current score is: " + data.getGameState().getCurrentScore());
                }
            }

        } catch (Exception e) {
            Log.e("CONTINUE", "Failed to setCurrentScore", e);
        }
    }


    public void setRounds(Context context, int amount) {
        try {
            File file = new File(context.getFilesDir(), FILENAME);
            Gson gson = new Gson();

            // Read the current userdata
            UserData data;
            try (FileReader reader = new FileReader(file)) {
                data = gson.fromJson(reader, UserData.class);
            }

            // Update the round
            if (data != null && data.getGameState() != null) {
                data.getGameState().setRound(amount);

                // Write it back
                try (FileWriter writer = new FileWriter(file)) {
                    gson.toJson(data, writer);
                    Log.d("CONTINUE", "Current score is: " + data.getGameState().getCurrentScore());
                }
            }

        } catch (Exception e) {
            Log.e("CONTINUE", "Failed to setCurrentScore", e);
        }
    }

    public void setHighScore(Context context) {
        try {
            File file = new File(context.getFilesDir(), FILENAME);
            Gson gson = new Gson();

            // Read the current userdata
            UserData data;
            try (FileReader reader = new FileReader(file)) {
                data = gson.fromJson(reader, UserData.class);
            }

            // Update the currentScore
            if (data != null && data.getGameState() != null) {
                if (data.getGameState().getHighScore() < data.getGameState().getCurrentScore()){
                    data.getGameState().setHighScore(data.getGameState().getCurrentScore());
                }

                // Write it back
                try (FileWriter writer = new FileWriter(file)) {
                    gson.toJson(data, writer);
                    Log.d("CONTINUE", "High score is: " + data.getGameState().getCurrentScore());
                }
            }

        } catch (Exception e) {
            Log.e("CONTINUE", "Failed to setCurrentScore", e);
        }
    }


}
