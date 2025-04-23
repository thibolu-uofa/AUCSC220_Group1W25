/**
 * Continue.java
 *
 * Author: Miron Nekhoroshkov
 *
 * This file gives way to a user to save their data, and developers
 * to more easily implement inter file communication. This file
 * is made with the use of Google's json reader library.
 *
 * functions:
 *      getHandTypesFromJson()
 *          gets the handtypes saved in the userdata.json file
 *      getDiceFromJson()
 *          gets the dice saved in the userdata.json file
 *      getRerollFromJson()
 *          gets the amount of rerolls saved in the userdata.json file
 *      getPlaysFromJson()
 *          gets the amount of plays saved in the userdata.json file
 *      getScoreToBeatFromJson()
 *          gets the needed score to beat saved in the userdata.json file
 *      getCurrentScoreFromJson()
 *          gets the current score saved in the userdata.json file
 *      getPaintingsFromJson()
 *          gets the paintings saved in the userdata.json file
 *      getRunHighScoreFromJson()
 *          gets the score for the specific run
 *          saved in the userdata.json file
 *      getAllTimeHighScoreFromJson()
 *          gets the all time high score
 *          saved in the userdata.json file
 *      copyJsonToInternalStorageIfNeeded(Context context)
 *          saves the json file to internal storage for save state to work
 *      setCurrentScore(Context context, int amount)
 *          saves the current score to the json file
 *      setScoreToBeat(Context context, int amount)
 *          saves the score to beat to the json file
 *      setRounds(Context context, int amount)
 *          saves the rounds the user played to the json file
 *      setRunHighScore(Context context)
 *          saves the highscore the user got to the json file
 *      setAllTimeHighScore(Context context)
 *          saves the highscore the user got to the json file
 *      setPlays(Context context, int amount)
 *          saves the amount of plays to the json file
 *      setRerolls(Context context, int amount)
 *          saves the amount of Rerolls to the json file
 *      setHandtypes(Context context, List<HandType> handTypes)
 *          saves the hands to the json file
 *      setHandtypes(Context context, String[] paintings)
 *  *          saves the paintings to the json file
 *      resetToDefaults(Context context)
 *          resets the userdata.json file
 */

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
import android.content.SharedPreferences;
public class Continue {
    private final Context context;

    private static final String FILENAME = "userdata.json";

    //Basic Constructor
    public Continue(Context context) {
        this.context = context;
    }
    // Add these in your Continue class


    /**
     * getHandTypesFromJson
     *
     * using the google Json reader, it will use the UserData.java
     * class to cleanse the file "userdata.json" and output the proper data
     *
     * @return all the handtypes saved in the json file
     */
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

    /**
     * getDiceFromJson
     *
     * using the google Json reader, it will use the UserData.java
     * class to cleanse the file "userdata.json" and output the proper data
     *
     * @return all the dice saved in the json file
     */
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

    /**
     * getRerollFromJson
     *
     * using the google Json reader, it will use the UserData.java
     * class to cleanse the file "userdata.json" and output the proper data
     *
     * @return the number of rerolls allowed saved in the json file
     */
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

    /**
     * getPlaysFromJson
     *
     * using the google Json reader, it will use the UserData.java
     * class to cleanse the file "userdata.json" and output the proper data
     *
     * @return the number of Plays allowed saved in the json file
     */
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

    /**
     * getScoreToBeatFromJson
     *
     * using the google Json reader, it will use the UserData.java
     * class to cleanse the file "userdata.json" and output the proper data
     *
     * @return the score to beat saved in the json file
     */
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

    /**
     * getRoundFromJson
     *
     * using the google Json reader, it will use the UserData.java
     * class to cleanse the file "userdata.json" and output the proper data
     *
     * @return the round reached saved in the json file
     */
    public int getRoundFromJson() {
        try {
            File file = new File(context.getFilesDir(), FILENAME);
            Gson gson = new Gson();
            UserData userData;

            try (FileReader reader = new FileReader(file)) {
                userData = gson.fromJson(reader, UserData.class);
            }

            return userData.getGameState().getRound();
        } catch (Exception e) {
            Log.e("Continue", "Error reading internal JSON", e);
            return 0;
        }
    }

    /**
     * getCurrentScoreFromJson
     *
     * using the google Json reader, it will use the UserData.java
     * class to cleanse the file "userdata.json" and output the proper data
     *
     * @return the current score saved in the json file
     */
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

    /**
     * getPaintingsFromJson
     *
     * using the google Json reader, it will use the UserData.java
     * class to cleanse the file "userdata.json" and output the proper data
     *
     * @return the paintings saved in the json file
     */
    public String[] getPaintingsFromJson() {
        try {
            File file = new File(context.getFilesDir(), FILENAME);
            Gson gson = new Gson();
            UserData userData;

            try (FileReader reader = new FileReader(file)) {
                userData = gson.fromJson(reader, UserData.class);
            }

            return userData.getPaintings();
        } catch (Exception e) {
            Log.e("Continue", "Error reading internal JSON", e);
            return new String[]{"", "", "", ""};
        }
    }

    /**
     * getRunHighScoreFromJson
     *
     * using the google Json reader, it will use the UserData.java
     * class to cleanse the file "userdata.json" and output the proper data
     *
     * @return the run High Score from the json file
     */
    public int getRunHighScoreFromJson() {
        try {
            File file = new File(context.getFilesDir(), FILENAME);
            Gson gson = new Gson();
            UserData userData;

            try (FileReader reader = new FileReader(file)) {
                userData = gson.fromJson(reader, UserData.class);
            }

            return userData.getGameState().getRunHighScore();
        } catch (Exception e) {
            Log.e("Continue", "Error reading internal JSON", e);
            return 0;
        }
    }

    /**
     * getAllTimeHighScoreFromJson
     *
     * using the google Json reader, it will use the UserData.java
     * class to cleanse the file "userdata.json" and output the proper data
     *
     * @return the all time High Score from the json file
     */
    public int getAllTimeHighScoreFromJson() {
        try {
            File file = new File(context.getFilesDir(), FILENAME);
            Gson gson = new Gson();
            UserData userData;

            try (FileReader reader = new FileReader(file)) {
                userData = gson.fromJson(reader, UserData.class);
            }

            return userData.getGameState().getAllTimeHighScore();
        } catch (Exception e) {
            Log.e("Continue", "Error reading internal JSON", e);
            return 0;
        }
    }

    /**
     * copyJsonToInternalStorageIfNeeded
     *
     * instead having the userdata.json reset everytime the user
     * starts the app, the userdata.json is written to internal storage
     * and is dugout again to be read.
     *
     * @param context
     */
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

    /**
     * setCurrentScore
     *
     * sets the current score into the internal storage
     *
     * @param context
     * @param amount
     */
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

    /**
     * setScoreToBeat
     *
     * sets the score to beat into the internal storage
     *
     * @param context
     * @param amount
     */
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

    /**
     * setRounds
     *
     * sets the round a certain user has reached into the internal storage
     *
     * @param context
     * @param amount
     */
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

    /**
     * setRunHighScore
     *
     * sets the high score a user reached into the internal storage,
     * this is done by simply calling the function with one parameter
     * as the check will happen within the function
     *
     * @param context
     */
    public void setRunHighScore(Context context) {
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
                if (data.getGameState().getRunHighScore() < data.getGameState().getCurrentScore()){
                    data.getGameState().setRunHighScore(data.getGameState().getCurrentScore());
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

    /**
     * setAllTimeHighScore
     *
     * sets the high score a user reached into the internal storage,
     * this is done by simply calling the function with one parameter
     * as the check will happen within the function
     *
     * @param context
     */
    public void setAllTimeHighScore(Context context) {
        try {
            File file = new File(context.getFilesDir(), FILENAME);
            Gson gson = new Gson();

            // Read the current allTimeHighScore
            UserData data;
            try (FileReader reader = new FileReader(file)) {
                data = gson.fromJson(reader, UserData.class);
            }

            // Update the allTimeHighScore
            if (data != null && data.getGameState() != null) {
                if (data.getGameState().getAllTimeHighScore() < data.getGameState().getCurrentScore()){
                    data.getGameState().setAllTimeHighScore(data.getGameState().getCurrentScore());
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

    /**
     * setPlays
     *
     * sets the plays allowed by one user into the internal storage
     *
     * @param context
     * @param amount
     */
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

    /**
     * setRerolls
     *
     * sets the reroll amount a user is allowed into the internal storage
     *
     * @param context
     * @param amount
     */
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
                    Log.d("CONTINUE", "Amount of rerolls is: " + data.getGameState().getRerolls());
                }
            }

        } catch (Exception e) {
            Log.e("CONTINUE", "Failed to setRerolls", e);
        }
    }

    /**
     * setHandTypes
     *
     * sets the current scoring and level of each hand from a user
     * into the internal storage
     *
     * @param context
     * @param handTypes
     */
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

    /**
     * setPaintings
     *
     * sets the Users Paintings from the user
     * into the internal storage
     *
     * @param context
     * @param paintings
     */
    public void setPaintings(Context context, String[] paintings) {
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
                data.setPaintings(paintings);


                // Write it back
                try (FileWriter writer = new FileWriter(file)) {
                    gson.toJson(data, writer);
                    Log.d("CONTINUE", "Paintings are: " + data.getPaintings().toString());
                }
            }

        } catch (Exception e) {
            Log.e("CONTINUE", "Failed to set paintings", e);
        }
    }

    /**
     * resetToDefaults
     *
     * will reset all the information gained in a run of ROLLO
     *
     * @param context
     */
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
