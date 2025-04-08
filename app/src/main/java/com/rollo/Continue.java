package com.rollo;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Continue {
    private Context context;

    public Continue(Context context) {
        this.context = context;
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

    public List<HandType> readHandTypes() {
        List<HandType> handTypes = new ArrayList<>();

        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.userdata);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("Round")) break; // stop before game state section
                if (line.isEmpty()) continue;

                // Example line: "Pair    : 10, 2, 1"
                String[] parts = line.split(":");
                if (parts.length != 2) continue;

                String name = parts[0].trim();
                String[] values = parts[1].trim().split(",");

                if (values.length != 3) continue;

                int pips = Integer.parseInt(values[0].trim());
                int mult = Integer.parseInt(values[1].trim());
                int level = Integer.parseInt(values[2].trim());

                HandType handType = new HandType(name, pips, mult, level);
                handTypes.add(handType);
            }

            reader.close();
        } catch (Exception e) {
            Log.e("Continue", "Failed to read hand types", e);
        }

        return handTypes;
    }
}
