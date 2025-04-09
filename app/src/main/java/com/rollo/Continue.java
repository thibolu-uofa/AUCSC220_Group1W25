package com.rollo;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
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

}
