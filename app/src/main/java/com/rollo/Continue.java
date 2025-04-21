package com.rollo;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
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
            File file = new File(context.getFilesDir(), FILENAME);
            Gson gson = new Gson();
            UserData userData;

            try (FileReader reader = new FileReader(file)) {
                userData = gson.fromJson(reader, UserData.class);
            }

            return userData.getHandTypes();
        } catch (Exception e) {
            Log.e("Continue", "Error reading internal JSON", e);
            return null;
        }
    }

    public Dice[] getDiceFromJson() {
        try {
            File file = new File(context.getFilesDir(), FILENAME);
            Gson gson = new Gson();
            UserData userData;

            try (FileReader reader = new FileReader(file)) {
                userData = gson.fromJson(reader, UserData.class);
            }

            return userData.getDice();
        } catch (Exception e) {
            Log.e("Continue", "Error reading internal JSON", e);
            return null;
        }
    }

    public int getRerollFromJson() {
        try {
            File file = new File(context.getFilesDir(), FILENAME);
            Gson gson = new Gson();
            UserData userData;

            try (FileReader reader = new FileReader(file)) {
                userData = gson.fromJson(reader, UserData.class);
            }

            return userData.getGameState().getRerolls();
        } catch (Exception e) {
            Log.e("Continue", "Error reading internal JSON", e);
            return 0;
        }
    }

    public int getPlaysFromJson() {
        try {
            File file = new File(context.getFilesDir(), FILENAME);
            Gson gson = new Gson();
            UserData userData;

            try (FileReader reader = new FileReader(file)) {
                userData = gson.fromJson(reader, UserData.class);
            }

            return userData.getGameState().getPlays();
        } catch (Exception e) {
            Log.e("Continue", "Error reading internal JSON", e);
            return 0;
        }
    }

    public int getScoreToBeatFromJson() {
        try {
            File file = new File(context.getFilesDir(), FILENAME);
            Gson gson = new Gson();
            UserData userData;

            try (FileReader reader = new FileReader(file)) {
                userData = gson.fromJson(reader, UserData.class);
            }

            return userData.getGameState().getScoreToBeat();
        } catch (Exception e) {
            Log.e("Continue", "Error reading internal JSON", e);
            return 0;
        }
    }
    public int getCurrentScoreFromJson() {
        try {
            File file = new File(context.getFilesDir(), FILENAME);
            Gson gson = new Gson();
            UserData userData;

            try (FileReader reader = new FileReader(file)) {
                userData = gson.fromJson(reader, UserData.class);
            }

            return userData.getGameState().getCurrentScore();
        } catch (Exception e) {
            Log.e("Continue", "Error reading internal JSON", e);
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

    public void setScoreToBeat(Context context, int amount) {
        try {
            File file = new File(context.getFilesDir(), FILENAME);
            Gson gson = new Gson();

            // Read the current userdata
            UserData data;
            try (FileReader reader = new FileReader(file)) {
                data = gson.fromJson(reader, UserData.class);
            }

            // Update the Score to Beat
            if (data != null && data.getGameState() != null) {
                data.getGameState().setScoreToBeat(amount);

                // Write it back
                try (FileWriter writer = new FileWriter(file)) {
                    gson.toJson(data, writer);
                    Log.d("CONTINUE", "Score to beat is: " + data.getGameState().getCurrentScore());
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
                    Log.d("CONTINUE", "Current round is: " + data.getGameState().getCurrentScore());
                }
            }

        } catch (Exception e) {
            Log.e("CONTINUE", "Failed to setRound", e);
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
            Log.e("CONTINUE", "Failed to setHighScore", e);
        }
    }

    public void setPlays(Context context, int amount) {
        try {
            File file = new File(context.getFilesDir(), FILENAME);
            Gson gson = new Gson();

            // Read the current userdata
            UserData data;
            try (FileReader reader = new FileReader(file)) {
                data = gson.fromJson(reader, UserData.class);
            }

            // Update the plays
            if (data != null && data.getGameState() != null) {
                data.getGameState().setPlays(amount);


                // Write it back
                try (FileWriter writer = new FileWriter(file)) {
                    gson.toJson(data, writer);
                    Log.d("CONTINUE", "Number of plays left is: "
                            + data.getGameState().getCurrentScore());
                }
            }

        } catch (Exception e) {
            Log.e("CONTINUE", "Failed to set number of plays", e);
        }
    }

    public void setRerolls(Context context, int amount) {
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
                data.getGameState().setRerolls(amount);


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

    public void setHandtypes(Context context, List<HandType> handTypes) {
        try {
            File file = new File(context.getFilesDir(), FILENAME);
            Gson gson = new Gson();

            // Read the current userdata
            UserData data;
            try (FileReader reader = new FileReader(file)) {
                data = gson.fromJson(reader, UserData.class);
            }

            // Update the plays
            if (data != null && data.getGameState() != null) {
                data.setHandTypes(handTypes);


                // Write it back
                try (FileWriter writer = new FileWriter(file)) {
                    gson.toJson(data, writer);
                    Log.d("CONTINUE", "Handtypes are: " + data.getHandTypes());
                }
            }

        } catch (Exception e) {
            Log.e("CONTINUE", "Failed to handtypes", e);
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
