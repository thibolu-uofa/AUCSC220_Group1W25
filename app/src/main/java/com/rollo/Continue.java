package com.rollo;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Continue {
    private final Context context;
    private final BufferedReader reader;

    public Continue(Context context) {
        this.context = context;
        InputStream inputStream = context.getResources().openRawResource(R.raw.userdata);
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public String readingUserData() {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.userdata);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
                Log.d("Continue", "Line: " + line);
            }

            reader.close();
        } catch (Exception e) {
            Log.e("Continue", "Error reading userdata.txt", e);
        }

        return stringBuilder.toString();
    }

    public List<HandType> getHandTypes() {
        List<HandType> handTypes = new ArrayList<>();

        try (
                InputStream inputStream = context.getResources().openRawResource(R.raw.userdata);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("Round")) break;
                if (line.isEmpty()) continue;

                String[] parts = line.split(":");
                if (parts.length != 2) continue;

                String name = parts[0].trim();
                String[] values = parts[1].trim().split(",");

                if (values.length != 3) continue;

                int pips = Integer.parseInt(values[0].trim());
                int mult = Integer.parseInt(values[1].trim());
                int level = Integer.parseInt(values[2].trim());

                handTypes.add(new HandType(name, pips, mult, level));
            }
        } catch (Exception e) {
            Log.e("Continue", "Failed to read hand types", e);
        }

        return handTypes;
    }

    public boolean setHandTypes(List<HandType> updatedHandTypes) {
        File file = new File(context.getFilesDir(), "userdata.txt");
        List<String> gameStateLines = new ArrayList<>();

        try {
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                boolean gameStateSection = false;

                while ((line = reader.readLine()) != null) {
                    if (line.trim().startsWith("Round")) {
                        gameStateSection = true;
                    }
                    if (gameStateSection) {
                        gameStateLines.add(line);
                    }
                }

                reader.close();
            }

            // Step 2: Write the new hand types and the original game state back to the file
            FileWriter writer = new FileWriter(file);

            for (HandType hand : updatedHandTypes) {
                String formatted = String.format("%-12s: %d, %d, %d\n",
                        hand.getName(), hand.getPips(), hand.getMult(), hand.getLevel());
                writer.write(formatted);
            }

            writer.write("\n");

            for (String stateLine : gameStateLines) {
                writer.write(stateLine + "\n");
            }

            writer.close();
            Log.d("Continue", "Hand types successfully overwritten.");
            return true;

        } catch (IOException e) {
            Log.e("Continue", "Failed to overwrite hand types", e);
            return false;
        }
    }


}
