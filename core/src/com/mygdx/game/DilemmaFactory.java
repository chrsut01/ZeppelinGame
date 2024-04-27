package com.mygdx.game;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class DilemmaFactory {

    public static List<Dilemma> loadDilemmasFromFile(String filePath) {
        List<Dilemma> dilemmas;

        try (FileInputStream fileIn = new FileInputStream(filePath);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            dilemmas = (List<Dilemma>) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            dilemmas = null; // Handle error appropriately
        }

        return dilemmas;
    }

}
