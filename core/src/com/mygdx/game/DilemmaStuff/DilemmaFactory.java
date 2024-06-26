package com.mygdx.game.DilemmaStuff;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class DilemmaFactory {

    public static List<Dilemma> loadDilemmasFromJsonFile(String filePath) {

        List<Dilemma> dilemmas = null;

        try (FileReader reader = new FileReader(filePath)) {
            Type dilemmaListType = new TypeToken<List<Dilemma>>(){}.getType();
            dilemmas = new Gson().fromJson(reader, dilemmaListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dilemmas;
    }
}
